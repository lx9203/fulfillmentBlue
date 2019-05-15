package supply;

import java.sql.*;
import java.util.*;

import org.slf4j.*;

import function.*;
import invoice.*;

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
	String curYear = cf.curYear();
	String nextYear = cf.nextYear(cf.curYear());
	String lastYear = cf.lastYear(cf.curYear());

	// -------------------------mysql연결----------------------------------------------------
	public SupplyDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// -------------------------mysql연결----------------------------------------------------

	// --------------------------Condition---------------------------------------------------

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
			}
			LOG.trace("sDao.selectCondition supplyList : " + supplyList.toString());
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
	

	// --------------------------Condition---------------------------------------------------
	
	// ----------------------------Admin-----------------------------------------------------

	// -> AdminProc.productList
	public SupplyDTO productQuantity(String pCode) {
		String sql = "select SUM(sQuantity) from supply where sProductCode like '" + pCode
				+ "' and sState <2 group by sProductCode;";
		SupplyDTO sDto = selectOneQuantityCondition(sql);
		return sDto;
	}

	public SupplyDTO selectOneQuantityCondition(String sql) {
		PreparedStatement pStmt = null;
		SupplyDTO aDto = new SupplyDTO();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				aDto.setsQuantity(rs.getInt("SUM(sQuantity)"));
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
		return aDto;
	}

	// ----------------------------Admin-----------------------------------------------------

	// ----------------------------Search----------------------------------------------------

	// 미완료 전체검색 (sState < 2) -> SupplyProc.supplyBeforeList
	public List<SupplyDTO> selectBeforeAll(String supplierCode) {
		LOG.trace("sDao.selectBeforeAll() 진입");
		String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState "
				+ "from supply as s inner join product as p on p.pCode = s.sProductCode "
				+ "where sState < 2 and p.pCode like '" + supplierCode + "%' and s.sDate < '"+cf.curDate()+"'order by sState desc;";
		List<SupplyDTO> supplyList = selectCondition(sql);
		LOG.trace("sDao.selectBeforeAll() 종료");
		return supplyList;
	}
	
	// 납품 
	public List<SupplyDTO> selectBeforeState(String supplierCode) {
		LOG.trace("sDao.selectBeforeAll() 진입");
		String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState "
				+ "from supply as s inner join product as p on p.pCode = s.sProductCode "
				+ "where sState = 0 and p.pCode like '" + supplierCode + "%'";
		List<SupplyDTO> supplyList = selectCondition(sql);
		LOG.trace("sDao.selectBeforeAll() 종료");
		return supplyList;
	}

	// 처리 완료인 전체검색 (sState = 2) -> SupplyProc.supplyAfterList
		public List<SupplyDTO> selectAll(String supplierCode) {
			LOG.trace("sDao.selectAll() 진입");
			String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState from supply as s "
					+ "inner join product as p on p.pCode = s.sProductCode where sState = 2 and p.pCode like '"
					+ supplierCode + "%' order by s.sCode desc;";
			List<SupplyDTO> supplyList = selectCondition(sql);
			LOG.trace("sDao.selectAll() 종료");
			return supplyList;
		}

	// 처리 완료인 이번달검색 (sState = 2) -> SupplyProc.supplyAfterList
	public List<SupplyDTO> selectAfterAll(String supplierCode) {
		LOG.trace("sDao.selectAfterAll() 진입");
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

	// 월별 리스트 -> SupplyProc.supplyAfterListSearch & SupplyProc.intoMain
	public List<SupplyDTO> searchByMonth(String month, String supplierCode) {
		LOG.trace("sDao.searchByMonth 진입");
		String nextMonth = cf.nextMonth(month) + "-01";
		LOG.trace("sDao.searchByMonth nextMonth : " + nextMonth);
		month = month + "-01";
		LOG.trace("sDao.searchByMonth month : " + month);
		String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where sState = 2 and p.pCode like '" + supplierCode + "%' "
				+ "and s.sDate >= '" + month + "' and s.sDate < '" + nextMonth + "';";
		List<SupplyDTO> searchList = selectCondition(sql);
		LOG.trace("sDao.searchByMonth searchList : " + searchList);
		LOG.trace("sDao.searchByMonth 종료");
		return searchList;
	}// 월별 리스트

	// 미처리 마지막 발주코드 -> CustomerFunction.sCodeCreate
	public String searchsCodeBySupplier(String supplierCode) {
		String sCode = new String();
		try {
			String sql = "select sCode from supply where sCode like '" + supplierCode
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
		LOG.trace("sDao.searchsCodeBySupplier sCode: " + sCode);
		return sCode;
	}
	
	// 미처리 발주코드 -> SupplyProc.complete
	public String searchsCode(String supplierCode) {
		String sCode = new String();
		try {
			String sql = "select sCode from supply where sState = 0 and sCode like '"+supplierCode+"%' order by sCode limit 1;";
			PreparedStatement pStmt = null;
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				sCode = rs.getString("sCode");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LOG.trace("sDao.searchsCodeBySupplier sCode: " + sCode);
		return sCode;
	}

	// sState 찾기 -> CustomerFunction.sCodeCreate
	public int searchState(String pCode) {
		String sql = "select sState from supply where sProductCode like '" + pCode + "%' order by sCode desc limit 1;";
		int state = selectOneCondition(sql);
		return state;
	}// sState 찾기

	// product에서 pCode에 해당하는 주문량 확인 -> SupplyProc.complete
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

	// 이번달에 sState가 1인 발주의 갯수 -> SupplyProc.intoMain
	public int count(String supplierCode) {
		String sql = "select count(*) from supply where sState = 2 and sCode like 'A%' "
				+ "and sDate >= '2019-05-01' and sDate < '2019-06-01';";
		PreparedStatement pStmt = null;
		int sCount = 0;
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				sCount = rs.getInt("count(*)");
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
		return sCount;
	}

	// 전년도 이맘때까지의 매출액 -> SupplyProc.intoMain
	public List<SupplyDTO> supplySalesLastYear(String supplierCode, String lastYear) {
		LOG.trace("supplySalesLastYear 진입");
		String lastNewDay = lastYear + "-01-01";
		String lastYearToday = lastYear + today.substring(4);
		LOG.trace("sDao.supplySalesLastYear lastNewDay : " + lastNewDay);
		LOG.trace("sDao.supplySalesLastYear lastYearToday : " + lastYearToday);
		String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState "
				+ "from supply as s "
				+ "inner join product as p on p.pCode = s.sProductCode " 
				+ "where sCode like '" + supplierCode + "%' and sState = 2 " 
				+ "and sDate >= '" + lastNewDay + "' and sDate <= '" + lastYearToday + "';";
		List<SupplyDTO> supplyList = selectCondition(sql);
		LOG.trace("supplySalesLastYear 퇴장");
		return supplyList;
	}
	
	// 올해 현재까지의 매출액	-> SupplyProc.intoMain
	public List<SupplyDTO> supplySalesCurYear(String supplierCode) {
		LOG.trace("supplySalesCurYear 진입");
		String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState "
				+ "from supply as s "
				+ "inner join product as p on p.pCode = s.sProductCode " 
				+ "where sCode like '" + supplierCode + "%' "
				+ "and sState = 2 and sDate >= '" + curYear + "-01-01' and sDate < '" + nextYear + "-01-01';";
		List<SupplyDTO> supplyList = selectCondition(sql);
		LOG.trace("supplySalesCurYear 퇴장");
		return supplyList;
	}
	
	public List<SupplyDTO> SalesYearMonth(String year,String supplierCode,int month){
		String thisMonth = String.format("%02d", month);
		String nextMonth = String.format("%02d", month+1);
		String sql = new String();
		if(month != 12) {
			sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState "
					+ "from supply as s " 
					+ "inner join product as p on p.pCode = s.sProductCode "
					+ "where s.sCode like '"+supplierCode+"%' and s.sDate >='"+year+"-"+thisMonth+"-01' and s.sDate < '"+year+"-"+nextMonth+"-01' and s.sState = 2;";
		}else {
			sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState "
					+ "from supply as s " 
					+ "inner join product as p on p.pCode = s.sProductCode "
					+ "where s.sCode like '"+supplierCode+"%' and s.sDate >='"+year+"-"+thisMonth+"-01' and s.sDate < '"+cf.nextYear(year)+"-01-01' and s.sState = 2;";
		}
		List<SupplyDTO> supplyList = selectCondition(sql);
		return supplyList;
	}

	// ----------------------------Search----------------------------------------------------

	// ----------------------------Control---------------------------------------------------

	// 발주 상태를 0(대기)로 만듬
	public void updateStateOne(String sCode) {
		String query = "update supply set sState=0 where sCode =?;";
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

	// 발주 상태를 1(발송중)로 만듬
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

	// 발주 상태를 2(완료)로 만듬
	public void updateStateTwo(String sCode) {
		String query = "update supply set sState=0 where sCode =?;";
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

	// supply DB에 추가 -> SupplyProc.complete
	public void insertSupply(String pCode, int Quantity) {
		// 발주신청(pCode를 받아 발주코드와 현재시간, 처리상태를 붙임)
		String query = "insert into supply (sCode, sProductCode, sDate, sQuantity, sState) values(?,?,?,?,?);";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, CustomerFunction.sCodeCreate(pCode));
			pStmt.setString(2, pCode);
			pStmt.setString(3, cf.curTime());
			pStmt.setInt(4, Quantity);
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

	// Product의 갯수를 증가시킴 -> SupplyProc.complete
	public void SupplyQuantity(String pCode, int pQuantity) {
		String query = "UPDATE product p INNER JOIN supply s ON p.pCode = s.sProductCode "
				+ "SET pQuantity = ? where pCode = ? and sDate < '" + today + "' and sDate >= '" + yesterday + "';";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, pQuantity + 20);
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

	// ----------------------------Control---------------------------------------------------

}
