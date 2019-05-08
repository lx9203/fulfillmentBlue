package supply;

import java.sql.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.Date;

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
	
	// 전체검색
	public List<SupplyDTO> selectAll() {
		String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState from supply as s inner join product as p on p.pCode = s.sProductCode;";
		List<SupplyDTO> supplyList = selectCondition(sql);
		return supplyList;
	}
	
	public List<SupplyDTO> selectCondition(String sql){
		PreparedStatement pStmt = null;
		List<SupplyDTO> supplyList = new ArrayList<SupplyDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				SupplyDTO supply = new SupplyDTO();
				supply.setsCode(rs.getString("sCode"));
				supply.setsProductCode(rs.getString("pCode"));
				supply.setsProductName(rs.getString("pName"));
				supply.setsProductPrice(rs.getInt("pPrice"));
				supply.setsDate(rs.getString("sDate"));
				supply.setsQuantity(rs.getInt("sQuantity"));
				supply.setsState(rs.getInt("sState"));
				supply.setsTotalPrice(supply.getsQuantity()*supply.getsProductPrice());
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
				LOG.info("selectAllCondition Error Code : {}", se.getErrorCode());
			}
		}
		return supplyList;
	}
	
	// 일별, 월별 검색
	public List<SupplyDTO> searchCondition(String sql){
		PreparedStatement pStmt = null;
		List<SupplyDTO> searchList = new ArrayList<SupplyDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				SupplyDTO supply = new SupplyDTO();
				supply.setsCode(rs.getString("sCode"));
				supply.setsProductCode(rs.getString("pName"));
				supply.setsProductPrice(rs.getInt("pPrice"));
				supply.setsQuantity(rs.getInt("sQuantity"));
				supply.setsDate(rs.getString("sDate"));
				supply.setsState(rs.getInt("sState"));
				searchList.add(supply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pStmt != null && !pStmt.isClosed())
					pStmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
				LOG.info("selectAllCondition Error Code : {}", se.getErrorCode());
			}
		}
		return searchList;
	}
	
	public List<SupplyDTO> searchByDay(String sDate) {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		sDate = sdf.format(curDate);
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate, s.sState from supply as s inner join product as p on p.pCode=s.sProductCode where s.sDate ='" +sDate+"';";
		List<SupplyDTO> searchList = searchCondition(sql);
		LOG.trace("searchList : " + searchList.toString());
		return searchList;
	}
	
	public List<SupplyDTO> searchByMonth(String sMonth) {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM");
		sMonth = sdf.format(curDate);
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where s.sDate like '%" + sMonth + "%';";
		List<SupplyDTO> searchList = searchCondition(sql);
		LOG.trace("searchList : " + searchList.toString());
		return searchList;
	}
	
	//	시간
	public String curTime() {
		LocalDateTime curTime = LocalDateTime.now();
    	
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    	
    	return curTime.format(dateTimeFormatter);
	}
	
}
