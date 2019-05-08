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

public class InvoiceDAO {
	private static final Logger LOG = LoggerFactory.getLogger(OrderDAO.class);
	private Connection conn;
	private static final String USERNAME = "javauser";
	private static final String PASSWORD = "javapass";
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?verifyServerCertificate=false&useSSL=false";
	
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	String today = curDate();
	
	public InvoiceDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertInvoice(InvoiceDTO invoice) { // Invoice 관련 7개의 정보를 넣는다.
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
	
	public void updateState(String iCode) { // 출고 처리
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
	//------------------------매출 계산을 위한 송장 리스트-------------------------------------------------
	
	//------------------------여러개의 송장번호를 리스트로 가져오기-------------------------------------------
	//------------------- 쇼핑몰에서 사용하는 리스트--------------------------------
	public List<InvoiceDTO> mallSelectAllDay(char iCode){
		Date curDate = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String sql = "select iCode, iName, iTel, iAddress, iDate from invoice "
				+ "where iCode like '"+Character.toString(iCode)+"%"+sdf.format(curDate)+"%';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> mallSelectAllMonth(char iCode){
		Date curDate = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
		String sql = "select iCode, iName, iTel, iAddress, iDate from invoice "
				+ "where iCode like '"+Character.toString(iCode)+"%"+sdf.format(curDate)+"%';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	//-------------------------------운송사에서 사용하는 리스트 -------------------------
	
	public List<InvoiceDTO> transSelectAllDay(String userId){
		LOG.trace(today);
		String sql = "select iCode, iName, iTel, iAddress, iDate from invoice "
				+ "where iDate like '"+today+"%' and iAreaCode like '"+userId+"';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> transSelectAllMonth(String userId){
		String sql = "select iCode, iName, iTel, iAddress, iDate from invoice "
				+ "where iDate >'"+today+" ' and iDate < '"+today+" 23:59:59' and iAreaCode like '"+userId+"';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	//----------------------------날짜 검색 리스트-------------------------------
	
	public List<InvoiceDTO> mallSearchAllDay(char iCode,String Date){
		String sql = "select iCode, iName, iTel, iAddress, iDate from invoice "
				+ "where iCode like '"+Character.toString(iCode)+"%' and iDate >'"+Date+"' and iDate < '"+Date+" 23:59:59';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> transSearchAllDay(String userId,String Date){
		String sql = "select iCode, iName, iTel, iAddress, iDate from invoice "
				+ "where iDate like '"+Date+"%' and iAreaCode like '"+userId+"';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	//수정 안함
	public List<InvoiceDTO> mallSearchAllMonth(char iCode,String date){
		String sql = "select iCode, iName, iTel, iAddress, iDate from invoice "
				+ "where iCode like '"+Character.toString(iCode)+"%' and iDate >'"+date+"' and iDate < '"+date+" 23:59:59';";
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
	public String curMonth() {
		LocalDateTime curTime = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");	
    	return curTime.format(dateTimeFormatter);
	}
	//하루전 날짜 구하는 함수
	public String yesterDate() {
		LocalDateTime yesterTime = LocalDateTime.now();
		yesterTime = yesterTime.minusDays(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");	
    	return yesterTime.format(dateTimeFormatter);
	}
}
