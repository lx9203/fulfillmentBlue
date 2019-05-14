package supply;

import java.sql.*;
import java.util.*;

import org.slf4j.*;

import function.*;

public class SupplyDAO {
	private static final Logger LOG = LoggerFactory.getLogger(SupplyDAO.class);
	private Connection conn;
	private static final String USERNAME = "javauser";
	private static final String PASSWORD = "javapass";
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?verifyServerCertificate=false&useSSL=false";

	PreparedStatement pStmt = null;
	ResultSet rs = null;

	CustomerFunction cf = new CustomerFunction();
	String today = cf.curDate();
	String yesterday = cf.yesterday(cf.curDate());
	String Month = cf.curMonth();
	String nextMonth = cf.nextMonth(cf.curMonth());
	String supplierCode = new String();

//-------------------------mysql연결----------------------------------------------------
	public SupplyDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//-------------------------mysql연결----------------------------------------------------
	
//--------------------------Condition---------------------------------------------------
	
	public List<SupplyDTO> selectCondition(String sql) {
		LOG.trace("sDao.selectCondition() 진입");
		PreparedStatement pStmt = null;
		List<SupplyDTO> supplyList = new ArrayList<SupplyDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				SupplyDTO supply = new SupplyDTO();
				supply.setsCode(rs.getString("sCode"));
				supply.setsProductCode(rs.getString("pCode"));
				supply.setsProductName(rs.getString("pName"));
				supply.setsProductPrice(rs.getInt("pPrice"));
				supply.setsDate(rs.getString("sDate").substring(0, 10));
				supply.setsQuantity(rs.getInt("sQuantity"));
				supply.setsState(rs.getInt("sState"));
				supply.setsTotalPrice(supply.getsQuantity(), supply.getsProductPrice());
				supplyList.add(supply);
				LOG.trace("sDao.selectCondition supplyList : " + supplyList.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pStmt != null && !pStmt.isClosed())
					pStmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
				LOG.info("selectCondition Error Code : {}", se.getErrorCode());
			}
		}
		LOG.trace("sDao.selectCondition() 종료");
		return supplyList;
	}

	public int selectOneCondition(String sql) {
		PreparedStatement pStmt = null;
		int sState = 0;
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				sState = rs.getInt("sState");
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
		return sState;
	}
	
//--------------------------Condition---------------------------------------------------
	
//----------------------------Search----------------------------------------------------
	// 미완료 전체검색 (sState < 2)	-> SupplyProc.supplyBeforeList
	public List<SupplyDTO> selectBeforeAll(String userId) {
		LOG.trace("sDao.selectBeforeAll() 진입");
		supplierCode = userId.substring(0, 1);
		LOG.trace("supplierCode: " + supplierCode);
		String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState from supply as s "
				+ "inner join product as p on p.pCode = s.sProductCode " + "where sState < 2 and  p.pCode like '"
				+ supplierCode + "%' order by sState desc";
		List<SupplyDTO> supplyList = selectCondition(sql);
		LOG.trace("sDao.selectBeforeAll() 종료");
		return supplyList;
	}

	// 처리 완료인 전체검색 (sState = 2)	-> SupplyProc.supplyAfterList
	public List<SupplyDTO> selectAfterAll(String userId) {
		LOG.trace("sDao.selectAfterAll() 진입");
		supplierCode = userId.substring(0, 1);
		LOG.trace("sDao.selectAfterAll() supplierCode : " + supplierCode);
		String curMonth = cf.curMonth();
		String nextMonth = cf.nextMonth(curMonth);
		String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState from supply as s "
				+ "inner join product as p on p.pCode = s.sProductCode " + "where sState = 2 and p.pCode like '"
				+ supplierCode + "%' and s.sDate >= '" + curMonth + "-01' and s.sDate < '" + nextMonth
				+ "-01' order by s.sCode desc;";
		List<SupplyDTO> supplyList = selectCondition(sql);
		LOG.trace("sDao.selectAfterAll() 종료");
		return supplyList;
	}

	
//	public List<SupplyDTO> supplySalesYear(char sCode,String year){
//		String nextYear = cf.nextYear(year);
//		LOG.trace("[sDao.supplySalesYear] 다음 년도 날짜 : " + nextYear);
//		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
//				+ "where iCode like '"+Character.toString(sCode)+"%' and iDate >='"+year+"-01' and iDate < '"+nextYear+"-01' and iState = 2;";
//		List<SupplyDTO> supplyList = selectCondition(sql);
//		return supplyList;
//	}

	// 월별 리스트	-> SupplyProc.supplyAfterListSearch & SupplyProc.intoMain
	public List<SupplyDTO> searchByMonth(String month, String userId) {
		LOG.trace("sDao.searchByMonth 진입");
		supplierCode = userId.substring(0, 1);
		String nextMonth = cf.nextMonth(month)+"-01";
		LOG.trace("sDao.searchByMonth nextMonth : "+nextMonth);
		month = month+"-01";
		LOG.trace("sDao.searchByMonth month : "+month);
		String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where sState = 2 and p.pCode like '" + supplierCode
				+ "%' and s.sDate >= '" + month + "' and s.sDate < '" + nextMonth + "';";
		List<SupplyDTO> searchList = selectCondition(sql);
		LOG.trace("sDao.searchByMonth 종료");
		return searchList;
	}// 월별 리스트

	// 미처리 마지막 발주코드 -> CustomerFunction
	public String searchsCodeBySupplier(String supplier) {
		String sCode = new String();
		try {
			String sql = "select sCode from supply where sCode like '" + supplier
					+ "%' and sState = 0 order by sCode desc limit 1;";
			PreparedStatement pStmt = null;
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				sCode = rs.getString("sCode");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LOG.trace("sDao.searchsCodeBySupplier sCode: "+sCode);
		return sCode;
	}
	
	// sState 찾기	-> CustomerFunction.sCodeCreate
	public int searchState(String pCode) {
		String sql = "select sState from supply where sProductCode like '" + pCode + "%' order by sCode desc limit 1;";
		int state = selectOneCondition(sql);
		return state;
	}// sState 찾기
	
	// product에서 pCode에 해당하는 주문량 확인	-> SupplyProc.complete
	public int selectQuantity(String pCode) {
		String query = "select pQuantity from product where pCode = ?;";
		int pQuantity = 0;
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, pCode);
			
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				pQuantity = rs.getInt("pQuantity");
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
		return pQuantity;
	}
	
	// 이번달에 sState가 1인 발주의 갯수
	public int count() {
		String sql = "select count(*) from supply where sState = 1 and s.sDate >= '" + Month + "-01' and s.sDate < '"
				+ nextMonth + "-01';";
		int state = selectOneCondition(sql);
		return state;
	}
	
//----------------------------Search----------------------------------------------------

//----------------------------Control---------------------------------------------------	
	
	// 공급 완료	-> SupplyProc.complete
	public void SupplyQuantity(String pCode, int pQuantity) {
		String query = "UPDATE product p INNER JOIN supply s ON p.pCode = s.sProductCode "
				+ "SET pQuantity = ? where pCode = ? and sDate < '" + today + "' and sDate >= '" + yesterday + "';";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, pQuantity + 100);
			pStmt.setString(2, pCode);
			pStmt.executeUpdate();
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
	}
	
	// (supply) 발주 상태를 1로
	public void updateState(String sCode) {
		String query = "update supply set sState=1 where sCode =?;";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, sCode);
			pStmt.executeUpdate();
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
	}

	//DB에 추가
	public void insertSupply(String pCode) {
		// 발주신청(pCode를 받아 발주코드와 현재시간, 처리상태를 붙임)
		String query = "insert into supply (sCode, sProductCode, sDate, sQuantity, sState) values(?,?,?,?,?);";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, CustomerFunction.sCodeCreate(pCode));
			pStmt.setString(2, pCode);
			pStmt.setString(3, cf.curTime());
			pStmt.setInt(4, 50);
			pStmt.setInt(5, 0);

			pStmt.executeUpdate();
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
	}
//----------------------------Control---------------------------------------------------	

}
