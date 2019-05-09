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

	// 오늘 리스트
	public List<SupplyDTO> searchByDay() {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		String sDay = sdf.format(curDate);
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate, s.sState from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where s.sDate >= '" + sDay + "' and s.sDate <= '"
				+ sDay + " 23:59:59';";
		List<SupplyDTO> searchList = searchCondition(sql);
		return searchList;
	}// 오늘 리스트
	
	// 일별 리스트
	public List<SupplyDTO> searchByDay(String sDay) {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		sDay = sdf.format(curDate);
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate, s.sState from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where s.sDate >= '" + sDay + "' and s.sDate <= '"
				+ sDay + " 23:59:59';";
		List<SupplyDTO> searchList = searchCondition(sql);
		return searchList;
	}// 일별 리스트

	// 이번달 리스트
	public List<SupplyDTO> searchByMonth() {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM");
		String sMonth = sdf.format(curDate);
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate, s.sState from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where s.sDate >= '" + sMonth + "-01' and s.sDate <= '" + sMonth + "-31 23:59:59'";
		List<SupplyDTO> searchList = searchCondition(sql);
		return searchList;
	}// 이번달 리스트

	// 월별 리스트
	public List<SupplyDTO> searchByMonth(String sMonth) {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM");
		sMonth = sdf.format(curDate);
		String sql = "select s.sCode, p.pName, p.pPrice, s.sQuantity, s.sDate, s.sState from supply as s "
				+ "inner join product as p on p.pCode=s.sProductCode where s.sDate >= '" + sMonth + "-01' and s.sDate <= '" + sMonth + "-31 23:59:59'";
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

	// 재고확인
	public void searchQuantity() {

	}// 재고확인

	// 날짜로 sState찾기
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

	public int searchStateByDay() {
		String sql = "select sState from supply order by sCode desc limit 1;";
		int state = selectOneCondition(sql);
		return state;
	}// 날짜로 sState찾기

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
			pStmt.setInt(5, sDto.getsState());

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

	// 출고 처리 ※ product 에서 pQuantity 수량을 검색하고 +100 해야함
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

	// 시간
	public String curTime() {
		LocalDateTime curTime = LocalDateTime.now();

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

		return curTime.format(dateTimeFormatter);
	}// 시간

}
