package supply;

import java.sql.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.Date;

import org.slf4j.*;

import invoice.*;
import product.*;

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
				supply.setsDate(rs.getString("sDate"));
				supply.setsQuantity(rs.getInt("sQuantity"));
				supply.setsState(rs.getInt("sState"));
				supply.setsTotalPrice(supply.getsQuantity() * supply.getsProductPrice());
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
				LOG.info("selectAllCondition Error Code : {}", se.getErrorCode());
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
				supply.setsDate(rs.getString("sDate"));
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

	// 일별 검색
	public List<SupplyDTO> searchByDay(String sDay) {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		sDay = sdf.format(curDate);
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate, s.sState from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where s.sDate >= '" + sDay + "' and s.sDate <= '"
				+ sDay + " 23:59:59';";
		List<SupplyDTO> searchList = searchCondition(sql);
		LOG.trace("searchList : " + searchList.toString());
		return searchList;
	}// 일별 검색

	// 월별 검색
	public List<SupplyDTO> searchByMonth(String sMonth) {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		sMonth = sdf.format(curDate);
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate, s.sState from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where s.sDate ='" + sMonth + "';";
		List<SupplyDTO> searchList = searchCondition(sql);
		LOG.trace("searchList : " + searchList.toString());
		return searchList;
	}// 월별 검색

	// 미처리 마지막 발주코드
	public String searchsCodeBySupplier(String supplier) {
		String sCode="";
		try {
			SupplyDTO supply = new SupplyDTO();
			String sql = "select s.sCode from supply as s inner join product as p on p.pCode=s.sProductCode where sCode like '"+ supplier +"%' and sState = 0 order by sCode desc limit 1;";
			PreparedStatement pStmt = null;
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			supply.setsCode(rs.getString("sCode"));
			sCode = supply.getsCode();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sCode;
	}

	// 재고확인
	public void searchQuantity() {

	}// 재고확인

	// 날짜로 sState찾기
	public int selectOneCondition(String sql) {
		PreparedStatement pStmt = null;
		SupplyDTO state = new SupplyDTO();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			state.setsState(rs.getInt("sState"));

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
		return state.getsState();
	}

	public int searchStateByDay(String sDay) {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		sDay = sdf.format(curDate);
		String sql = "select s.sState from supply as s inner join product as p on p.pCode=s.sProductCode where s.sDate >= '"
				+ sDay + "' and s.sDate <= '" + sDay + " 23:59:59' order by sCode limit 1;";
		int state = selectOneCondition(sql);
		return state;
	}// 날짜로 sState찾기

	// sCode 찾기
	public String selectOnesCode() {
		PreparedStatement pStmt = null;
		SupplyDTO sCode = new SupplyDTO();
		String sql = "select sCode from supply where sCode like '" + " 공급사 입력 "
				+ "%' and sState = 0 order by sCode desc;";
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			sCode.setsCode(rs.getString("sCode"));

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
		return sCode.getsCode();
	}

	// 시간
	public String curTime() {
		LocalDateTime curTime = LocalDateTime.now();

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

		return curTime.format(dateTimeFormatter);
	}// 시간

}
