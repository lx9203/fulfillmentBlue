package product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import user.UserDAO;

public class ProductDAO {
	private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);

	public static final int ID_PASSWORD_MATCH = 1;
	public static final int ID_DOES_NOT_EXIST = 2;
	public static final int PASSWORD_IS_WRONG = 3;
	public static final int DATABASE_ERROR = -1;
	private Connection conn;
	private static final String USERNAME = "javauser";
	private static final String PASSWORD = "javapass";
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?verifyServerCertificate=false&useSSL=false";
	
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	
	public ProductDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//출하 및 입고 후 개수 업데이트 메소드
	
	public void updateQuantity(ProductDTO product) {
		String query = "update product set pQuantity=? where pCode =?;";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, product.getpQuantity());			
			pStmt.setString(2, product.getpCode());			
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
	
	//------------------------ 전체 리스트 가져오기 ------------------------
	
	public List<ProductDTO> selectAll(){
		String sql = "select pName, pPrice, pQuantity,pImgSource from product;";
		List<ProductDTO> productList = selectAllCondition(sql);
		return productList;
	}
	
	public List<ProductDTO> selectAllCondition(String sql){
		PreparedStatement pStmt = null;
		List<ProductDTO> productList = new ArrayList<ProductDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				ProductDTO product = new ProductDTO();
				product.setpName(rs.getString("pName"));
				product.setpPrice(rs.getInt("pPrice"));
				product.setpQuantity(rs.getInt("pQuantity"));
				product.setpImgSource(rs.getString("pImgSource"));
				productList.add(product);
			}
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
		return productList;
	}
	
	
	
	public void close() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
