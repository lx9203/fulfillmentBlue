package invoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import function.*;

public class InvoiceDAO {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceDAO.class);
	private Connection conn;
	private static final String USERNAME = "javauser";
	private static final String PASSWORD = "javapass";
	
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?verifyServerCertificate=false&useSSL=false";
	
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	String today = curDate();
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
			pStmt.setString(6, curTime());
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
	
	public void completeState(String iCode) { // 출고 완료 (State = 2)
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
	public List<InvoiceDTO> selectAllWorkTime(){ //오늘 날짜의 9시부터 18시까지의 송장을 처리
		String today = curDate();
		String sql = "select iCode from invoice WHERE iDate >='"+today+" 09:00:00' AND iDate <= '"+today+" 18:00:00' ;";
		List<InvoiceDTO> invoiceList = selectiCodeCondition(sql);
		return invoiceList;
	}
	public List<InvoiceDTO> selectAllBeforeWork(){ //어제 날짜 18일 부터 오늘날짜 09시 까지 송장 처리 
		String today = curDate();
		String yesterday = yesterDate();
		String sql = "select iCode from invoice WHERE iDate >='"+yesterday+" 18:00:00' AND iDate < '"+today+" 09:00:00' ;";
		List<InvoiceDTO> invoiceList = selectiCodeCondition(sql);
		return invoiceList;
	}
	public List<InvoiceDTO> selectAllAfterWork(){
		String today = curDate(); //오늘 날짜 18시부터 24시까지 송장 처리
		String sql = "select iCode from invoice WHERE iDate >'"+today+" 18:00:00' AND iDate <= '"+today+" 24:00:00' ;";
		List<InvoiceDTO> invoiceList = selectiCodeCondition(sql);
		return invoiceList;
	}
	// iCode 가져오는 메소드
	public List<InvoiceDTO> selectiCodeCondition(String sql){
		PreparedStatement pStmt = null;
		List<InvoiceDTO> invoiceList = new ArrayList<InvoiceDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				InvoiceDTO invoice = new InvoiceDTO();
				invoice.setiCode(rs.getString("iCode"));
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
	
	//------------------------여러개의 송장번호를 리스트로 가져오기-------------------------------------------
	//------------------- 쇼핑몰에서 사용하는 리스트--------------------------------
	
	//1. 해당 쇼핑몰의 지정한 날짜의 송장 목록 가져오기
	public List<InvoiceDTO> mallSearchAllDay(char iCode,String Date){
		String tomorrow = cf.tomorrow(Date);
		LOG.trace("[송장 DAO] 다음날 날짜 : " + tomorrow);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iCode like '"+Character.toString(iCode)+"%' and iDate >'"+Date+"' and iDate < '"+tomorrow+"';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> mallSearchAllMonth(char iCode,String month){
		String nextMonth = cf.nextMonth(month);
		LOG.trace("[송장 DAO] 다음달 날짜 : " + nextMonth);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iCode like '"+Character.toString(iCode)+"%' and iDate >='"+month+"-01' and iDate < '"+nextMonth+"-01';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	//-------------------------------운송사에서 사용하는 리스트 -------------------------
	
	public List<InvoiceDTO> transSelectAllDay(String userId){
		LOG.trace(today);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iDate like '"+today+"%' and iAreaCode like '"+userId+"';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> transSelectAllMonth(String userId){
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iDate >'"+today+" ' and iDate < '"+today+" 23:59:59' and iAreaCode like '"+userId+"';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	//----------------------------날짜 검색 리스트-------------------------------
	
	public List<InvoiceDTO> transSearchAllDay(String userId,String Date){
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iDate like '"+Date+"%' and iAreaCode like '"+userId+"';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	//--------------------------------송장 매출 처리 메소드----------------------
	
	public List<InvoiceDTO> mallSalesMonth(char iCode,String month){
		String nextMonth = cf.nextMonth(month);
		LOG.trace("[송장 DAO] 다음달 날짜 : " + nextMonth);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iCode like '"+Character.toString(iCode)+"%' and iDate >='"+month+"-01' and iDate < '"+nextMonth+"-01' and iState = 2;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> mallSalesYear(char iCode,String year){
		String nextYear = cf.nextYear(year);
		LOG.trace("[송장 DAO] 다음 년도 날짜 : " + nextYear);
		String sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
				+ "where iCode like '"+Character.toString(iCode)+"%' and iDate >='"+year+"-01' and iDate < '"+nextYear+"-01' and iState = 2;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> mallSalesCurYearMonth(char iCode,int month){ // !!! 'and iState = 1'을 조건으로 추가 해야 함 !!!
		String thisMonth = String.format("%02d", month);
		String nextMonth = String.format("%02d", month+1);
		String sql = new String();
		if(month != 12) {
			sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
					+ "where iCode like '"+Character.toString(iCode)+"%' and iDate >='"+cf.curYear()+"-"+thisMonth+"-01' and iDate < '"+cf.curYear()+"-"+nextMonth+"-01' and iState = 2;";
		}else {
			sql = "select iCode, iName, iTel, iAddress, iDate, iState from invoice "
					+ "where iCode like '"+Character.toString(iCode)+"%' and iDate >='"+cf.curYear()+"-"+thisMonth+"-01' and iDate < '"+cf.nextYear(cf.curYear())+"-01-01' and iState = 2;";
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
	public InvoiceDTO selectOneDayLast(String date){
		String sql = "select iCode from invoice where iCode like '%"+date+"%' order by iCode desc limit 1;";
		InvoiceDTO invoice = selectOneIncrement(sql);
		return invoice;
	}
	
	public InvoiceDTO selectOneIncrement(String sql){
		PreparedStatement pStmt = null;
		InvoiceDTO invoice = new InvoiceDTO();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				invoice.setiCode(rs.getString("iCode"));
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
				invoice.setiDate(rs.getString("iDate"));
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
	
	//현재 시간 구하기 
	public String curTime() {
		LocalDateTime curTime = LocalDateTime.now();	
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	return curTime.format(dateTimeFormatter);
	}
	
	//현재 날짜를 구하는 함수
	public String curDate() {
		LocalDateTime curTime = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");	
    	return curTime.format(dateTimeFormatter);
	}
	//이번 달을 구하는 함수
	public String curMonth() {
		LocalDateTime curTime = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");	
    	return curTime.format(dateTimeFormatter);
	}
	//다음달 구하는 함수
	public String nextMonth() {
		LocalDateTime nextTime = LocalDateTime.now();
		nextTime = nextTime.plusMonths(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");	
    	return nextTime.format(dateTimeFormatter);
	}
	//하루전 날짜 구하는 함수
	public String yesterDate() {
		LocalDateTime yesterTime = LocalDateTime.now();
		yesterTime = yesterTime.minusDays(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");	
    	return yesterTime.format(dateTimeFormatter);
	}
}
