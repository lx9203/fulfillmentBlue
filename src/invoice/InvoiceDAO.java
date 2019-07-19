package invoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import function.*;

public class InvoiceDAO {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceDAO.class);
	private Connection conn;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "javapass";
	
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?serverTimezone=UTC&useSSL=false";
	
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	CustomerFunction cf = new CustomerFunction();
	
	public InvoiceDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertInvoice(InvoiceDTO invoice) { // 송장정보를 DB에 넣는다.
		String query = "insert into invoice (iCode, iName, iTel, iAddress, iAreaCode, iDate, iState) "
				+ "values(?,?,?,?,?,?,?);";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, invoice.getiCode());
			pStmt.setString(2, invoice.getiName());
			pStmt.setString(3, invoice.getiTel());
			pStmt.setString(4, invoice.getiAddress());
			pStmt.setString(5, invoice.getiAreaCode());
			//날짜와 배송 상태는 현재시간과 배송 준비 상태를 넣는다.
			pStmt.setString(6, cf.curTime());
			pStmt.setInt(7, 0);
			
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
	
	public void requestState(String iCode) { // 출고 신청 (State = 1)
		String query = "update invoice set iState=1 where iCode =?;";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1,iCode);
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
	
	
	//-----------------------출고 처리 하기--------------------------------------------
	public List<InvoiceDTO> selectAllWorkTime(String Area){ //오늘 날짜의 18시까지의 송장을 처리
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice WHERE iAreaCode like '"+Area+"' and iDate < '"+cf.curDate()+" 18:00:00' and iState = 0 order by iDate ;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	public List<InvoiceDTO> selectAllBeforeWork(String Area){ //오늘날짜 09시 까지 송장 처리 
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice WHERE iAreaCode like '"+Area+"' and iDate < '"+cf.curDate()+" 09:00:00' and iState = 0 order by iDate ;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	//------------------------여러개의 송장번호를 리스트로 가져오기-------------------------------------------
	
	
	//------------------- 쇼핑몰에서 사용하는 리스트--------------------------------
	
	//1. 해당 쇼핑몰의 지정한 날짜의 송장 목록 가져오기
	public List<InvoiceDTO> mallSearchAllDay(char iCode,String date){
		String tomorrow = cf.tomorrow(date);
		LOG.trace("[송장 DAO] 다음날 날짜 : " + tomorrow);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iCode like '%"+Character.toString(iCode)+"%' and iDate >='"+date+"' and iDate < '"+tomorrow+"';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	//2. 해당 쇼핑몰에서 지정한 달의 송장 목록 가져오기
	public List<InvoiceDTO> mallSearchAllMonth(char iCode,String month){
		String nextMonth = cf.nextMonth(month);
		LOG.trace("[송장 DAO] 다음달 날짜 : " + nextMonth);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iCode like '%"+Character.toString(iCode)+"%' and iDate >='"+month+"-01' and iDate < '"+nextMonth+"-01' and iState = 2;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	//----------------------운송사에서 사용하는 리스트 -------------------------
	
	//1. 해당 운송사의 지정한 날짜의 송장 목록 가져오기
	public List<InvoiceDTO> transSearchAllDay(String AreaCode,String date){
		String tomorrow = cf.tomorrow(date);
		LOG.trace("[송장 DAO] 다음날 날짜 : " + tomorrow);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iAreaCode like '"+AreaCode+"' and iDate >='"+date+"' and iDate < '"+tomorrow+"';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	//2. 해당 운송사의 지정한 달의 송장 목록 가져오기
	public List<InvoiceDTO> transSearchAllMonth(String AreaCode,String month){
		String nextMonth = cf.nextMonth(month);
		LOG.trace("[송장 DAO] 다음달 날짜 : " + nextMonth);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iAreaCode like '"+AreaCode+"' and iDate >='"+month+"-01' and iDate < '"+nextMonth+"-01' and iState = 2;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	//3. 송장 처리가 진행중인 송장의 목록 가져오기
	public List<InvoiceDTO> transRequestList(){
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice where iState = 1;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	//--------------------------------송장 매출 처리 메소드----------------------
	
	// ------------------------------1. 쇼핑몰 지불액 처리---------------------------------
	public List<InvoiceDTO> mallSalesMonth(char iCode,String month){
		String nextMonth = cf.nextMonth(month);
		LOG.trace("[송장 DAO] 다음달 날짜 : " + nextMonth);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iCode like '%"+Character.toString(iCode)+"%' and iDate >='"+month+"-01' and iDate < '"+nextMonth+"-01' and iState = 2;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> mallSalesYear(char iCode,String year){
		String nextYear = cf.nextYear(year);
		LOG.trace("[송장 DAO] 다음 년도 날짜 : " + nextYear);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iCode like '%"+Character.toString(iCode)+"%' and iDate >='"+year+"-01' and iDate < '"+nextYear+"-01' and iState = 2;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> mallSalesYearMonth(String year,char iCode,int month){
		String thisMonth = String.format("%02d", month);
		String nextMonth = String.format("%02d", month+1);
		String sql = new String();
		if(month != 12) {
			sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
					+ "where iCode like '%"+Character.toString(iCode)+"%' and iDate >='"+year+"-"+thisMonth+"-01' and iDate < '"+year+"-"+nextMonth+"-01' and iState = 2;";
		}else {
			sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
					+ "where iCode like '%"+Character.toString(iCode)+"%' and iDate >='"+year+"-"+thisMonth+"-01' and iDate < '"+cf.nextYear(year)+"-01-01' and iState = 2;";
		}
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	//---------------------------------2.운송사 매출 처리----------------------------------------
	public List<InvoiceDTO> transSalesMonth(String AreaCode,String month){
		String nextMonth = cf.nextMonth(month);
		LOG.trace("[송장 DAO] 다음달 날짜 : " + nextMonth);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iAreaCode like '"+AreaCode+"' and iDate >='"+month+"-01' and iDate < '"+nextMonth+"-01' and iState = 2;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> transSalesYear(String AreaCode,String year){
		String nextYear = cf.nextYear(year);
		LOG.trace("[송장 DAO] 다음 년도 날짜 : " + nextYear);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iAreaCode like '"+AreaCode+"' and iDate >='"+year+"-01' and iDate < '"+nextYear+"-01' and iState = 2;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> transSalesYearMonth(String year, String AreaCode,int month){
		String thisMonth = String.format("%02d", month);
		String nextMonth = String.format("%02d", month+1);
		String sql = new String();
		if(month != 12) {
			sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
					+ "where iAreaCode like '"+AreaCode+"' and iDate >='"+year+"-"+thisMonth+"-01' and iDate < '"+year+"-"+nextMonth+"-01' and iState = 2;";
		}else {
			sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
					+ "where iAreaCode like '"+AreaCode+"' and iDate >='"+year+"-"+thisMonth+"-01' and iDate < '"+cf.nextYear(year)+"-01-01' and iState = 2;";
		}
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> selectAllCondition(String sql){
		PreparedStatement pStmt = null;
		List<InvoiceDTO> invoiceList = new ArrayList<InvoiceDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				InvoiceDTO invoice = new InvoiceDTO();
				invoice.setiCode(rs.getString("iCode"));
				invoice.setiName(rs.getString("iName"));
				invoice.setiTel(rs.getString("iTel"));
				invoice.setiAddress(rs.getString("iAddress"));
				invoice.setiDate(rs.getString("iDate").substring(0, 10));
				invoice.setiState(rs.getInt("iState"));
				invoiceList.add(invoice);
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
		return invoiceList;
	}

	//----------------------------한개의 송장번호 가져오기--------------------------------------------
	public InvoiceDTO selectOneDayLast(){
		String sql = "select iCode, iName, iTel, iAddress, iDate from invoice where iDate >='"+cf.curDate()+"' and iDate < '"+cf.tomorrow(cf.curDate())+"'  order by iCode desc limit 1;";
		InvoiceDTO invoice = selectOneCondition(sql);
		return invoice;
	}
	
	public InvoiceDTO selectOneCode(String iCode){
		String sql = "select iCode, iName, iTel, iAddress, iDate from invoice "
				+ "where iCode like '"+iCode+"';";
		InvoiceDTO invoice = selectOneCondition(sql);
		return invoice;
	}
	
	public InvoiceDTO selectOneCondition(String sql){
		PreparedStatement pStmt = null;
		InvoiceDTO invoice = new InvoiceDTO();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				invoice.setiCode(rs.getString("iCode"));
				invoice.setiName(rs.getString("iName"));
				invoice.setiTel(rs.getString("iTel"));
				invoice.setiAddress(rs.getString("iAddress"));
				invoice.setiDate(rs.getString("iDate").substring(0, 10));
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
		return invoice;
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
