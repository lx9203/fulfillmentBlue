package supply;

import java.sql.*;
import java.time.*;
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

	public SupplyDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 전체검색
	// 미처리 전체검색
	public List<SupplyDTO> selectBeforeAll(String userId) {
		supplierCode = userId.substring(0, 1);
		LOG.trace("supplierCode: " + supplierCode);
		String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState from supply as s "
				+ "inner join product as p on p.pCode = s.sProductCode " + "where sState < 2 and  p.pCode like '"
				+ supplierCode + "%'";
		List<SupplyDTO> supplyList = selectCondition(sql);
		return supplyList;
	}

	// 처리 완료인 전체검색
	public List<SupplyDTO> selectAfterAll(String userId) {
		supplierCode = userId.substring(0, 1);
		LOG.trace("supplierCode: " + supplierCode);
		String curMonth = cf.curMonth();
		String nextMonth = cf.nextMonth(curMonth);
		String sql = "select s.sCode, p.pCode, p.pName, p.pPrice, s.sDate, s.sQuantity, s.sState from supply as s "
				+ "inner join product as p on p.pCode = s.sProductCode " + "where sState = 2 and  p.pCode like '"
				+ supplierCode + "%' and s.sDate >= '" + curMonth + "-01' and s.sDate < '" + nextMonth
				+ "-01' order by s.sCode desc;";
		List<SupplyDTO> supplyList = selectCondition(sql);
		return supplyList;
	}

	public List<SupplyDTO> selectCondition(String sql) {
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
				LOG.trace(supply.getsTotalPrice() + "");
				supplyList.add(supply);
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
		return supplyList;
	}// 전체검색

	// 일별, 월별 검색용 컨디션
	public List<SupplyDTO> searchCondition(String sql) {
		PreparedStatement pStmt = null;
		List<SupplyDTO> searchList = new ArrayList<SupplyDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				SupplyDTO supply = new SupplyDTO();
				supply.setsCode(rs.getString("sCode"));
				supply.setsProductName(rs.getString("pName"));
				supply.setsProductPrice(rs.getInt("pPrice"));
				supply.setsQuantity(rs.getInt("sQuantity"));
				supply.setsDate(rs.getString("sDate").substring(0, 10));
				supply.setsState(rs.getInt("sState"));
				searchList.add(supply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pStmt != null && !pStmt.isClosed())
					pStmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
				LOG.info("selectAllCondition Error Code : {}", se.getErrorCode());
			}
		}
		return searchList;
	}// 일별, 월별 검색용 컨디션

	// 어제 리스트
	public List<SupplyDTO> searchByDay() {
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate, s.sState from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where s.sDate >= '" + yesterday
				+ "' and s.sDate < '" + today + "';";
		List<SupplyDTO> searchList = searchCondition(sql);
		return searchList;
	}// 어제 리스트

	// 월별 리스트
	public List<SupplyDTO> searchByMonth(String Month, String userId) {
		supplierCode = userId.substring(0, 1);
		LocalDate LocalAfterMonth = LocalDate.parse(Month + "-01");
		LocalAfterMonth = LocalAfterMonth.plusMonths(1);
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate, s.sState from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where sState = 2 and p.pCode like '" + supplierCode
				+ "%' and s.sDate >= '" + Month + "' and s.sDate < '" + LocalAfterMonth + "';";
		List<SupplyDTO> searchList = searchCondition(sql);
		return searchList;
	}// 월별 리스트

	// 미처리 마지막 발주코드
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
		return sCode;
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

	// 날짜로 sState 찾기
	public int selectOneCondition(String sql) {
		PreparedStatement pStmt = null;
		int state = 0;
		try {
			pStmt = conn.prepareStatement(sql);
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
		return state;
	}

	public int searchState(String pCode) {
		String sql = "select sState from supply where sProductCode like '" + pCode + "%' order by sCode desc limit 1;";
		int state = selectOneCondition(sql);
		return state;
	}// 날짜로 sState 찾기

	public int count() {
		String sql = "select count(*) from supply where sState = 1 and s.sDate >= '" + Month + "-01' and s.sDate < '"
				+ nextMonth + "-01';";
		int state = selectOneCondition(sql);
		return state;
	}// 날짜로 sState 찾기

	// sCode 찾기
	public String selectOnesCode() {
		PreparedStatement pStmt = null;
		String sCode = new String();
		String sql = "select sCode from supply where sCode like '" + " 공급사 입력 "
				+ "%' and sState = 0 order by sCode desc;";
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				sCode = rs.getString("sCode");
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
		return sCode;
	}

	// supply DB 에 넣기
	public void insertSupply(SupplyDTO sDto) {
		PreparedStatement pStmt = null;
		String query = "insert into supply (sCode, sProductCode, sDate, sQuantity, sState) " + "values(?,?,?,?,?);";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, sDto.getsCode());
			pStmt.setString(2, sDto.getsProductCode());
			pStmt.setString(3, sDto.getsDate());
			pStmt.setInt(4, sDto.getsQuantity());
			pStmt.setString(5, sDto.getsState());

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

	// product에서 pCode에 해당하는 주문량 확인
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

	// 공급 완료
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

	public void insertSupply(String pCode) {
		// 발주신청(pCode를 받아 발주코드와 현재시간, 처리상태를 붙임)
		String query = "insert into supply (sCode, sProductCode, sDate, sQuantity, sState) values(?,?,?,?,?);";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, cf.sCodeCreate(pCode));
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
}
