package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserDAO {
	private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);

	public static final int ID_PASSWORD_MATCH = 1;
	public static final int ID_DOES_NOT_EXIST = 2;
	public static final int PASSWORD_IS_WRONG = 3;
	public static final int DATABASE_ERROR = -1;
	private Connection conn;
	private static final String USERNAME = "Gamja_SQL";
	private static final String PASSWORD = "Bluemoon1836#%";
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?verifyServerCertificate=false&useSSL=false";
	
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	
	public UserDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//로그인 확인 메소드
		public int verifyIdPassword(String id, String password) {
			String query = "select hashed from user where id=?;";
			String hashedPassword = "";
			try {
				pStmt = conn.prepareStatement(query);
				pStmt.setString(1,id);
				rs = pStmt.executeQuery();
				while(rs.next()) {
					hashedPassword = rs.getString(1);
					if(BCrypt.checkpw(password,hashedPassword))
						return ID_PASSWORD_MATCH;
					else
						return PASSWORD_IS_WRONG;
				}
				return ID_DOES_NOT_EXIST;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(pStmt != null && !pStmt.isClosed())
						pStmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			return DATABASE_ERROR;
		}
	
	//운송회사의 경우, 지역 추가
	public void insertUser(UserDTO user) {
		String query = "insert into user (id,user_type,name,password,hashed) values(?,?,?,?,?);";
		try {
			String hp = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, user.getId());
			pStmt.setInt(2, user.getuserType());
			pStmt.setString(3, user.getName());
			pStmt.setString(4, "****");
			pStmt.setString(5, hp);
			
			pStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pStmt != null && !pStmt.isClosed())
					pStmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	
	//---------------------- selectOne 관련 메소드 ----------------------------------
	public UserDTO lastId(int userType) { //쇼핑몰의 마지막 번호
    	String sql = "select * from user where user_type="+userType+" order by id desc limit 1;";
    	UserDTO uDto = selectOne(sql);
    	return uDto;
    }
	
	
	public UserDTO searchById(String id) {
    	String sql = "select * from user where id like '" + id + "';";
    	UserDTO uDto = selectOne(sql);
    	return uDto;
    }
	
	public UserDTO selectOne(String query) {
    	PreparedStatement pStmt = null;
    	UserDTO user = new UserDTO();
    	try {
			pStmt = conn.prepareStatement(query);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				user.setId(rs.getString("id"));
				user.setuserType(rs.getInt("user_type"));
				user.setName(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pStmt != null && !pStmt.isClosed()) 
					pStmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
    	return user;
    }
	
	//종료 메소드
	public void close() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
