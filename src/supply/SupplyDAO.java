package supply;

import java.sql.*;
import java.util.*;

import org.slf4j.*;

public class SupplyDAO {
	private static final Logger LOG = LoggerFactory.getLogger(SupplyDAO.class);
	private Connection conn;
	private static final String USERNAME = "javauser";
	private static final String PASSWORD = "javapass";
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?verifyServerCertificate=false&useSSL=false";
	
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	
	public SupplyDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<SupplyDTO> searchByDay(String day) {
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where s.sDate like '%" + day + "일%';";
		List<SupplyDTO> sDto = selectAll(sql);
		return sDto;
	}
	
	public List<SupplyDTO> searchByMonth(String month) {
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where s.sDate like '%" + month + "월%';";
		List<SupplyDTO> sDto = selectAll(sql);
		return sDto;
	}
	
	public List<SupplyDTO> selectAll(String query){
		List<SupplyDTO> supplyList = new ArrayList<SupplyDTO>();
		PreparedStatement pStmt = null;
		try {
			pStmt = conn.prepareStatement(query);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {	
				SupplyDTO sDto = new SupplyDTO();
				sDto.setsCode(rs.getString("sCode"));
				sDto.setsProductName(rs.getString("pName"));
				sDto.setsProductPrice(rs.getInt("pPrice"));
				sDto.setsQuantity(rs.getInt("sQuantity"));
				sDto.setsDate(rs.getString("sDate"));
				sDto.setsTotalPrice(sDto.getsProductPrice()*sDto.getsQuantity());
			}
			rs.close();
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
		return supplyList;
	}
	
	public List<SupplyDTO> selectAllCondition(String sql){
		PreparedStatement pStmt = null;
		List<SupplyDTO> supplyList = new ArrayList<SupplyDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				SupplyDTO supply = new SupplyDTO();
				supply.setsCode(rs.getString("sCode"));
				supply.setsProductCode(rs.getString("sProductCode"));
				supply.setsDate(rs.getString("sDate"));
				supply.setsQuantity(rs.getInt("sQuantity"));
				supply.setsState(rs.getInt("sState"));
				supplyList.add(supply);
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
		return supplyList;
	}
	
}
