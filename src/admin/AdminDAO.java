package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import invoice.InvoiceDTO;
import invoice.OrderDAO;
import invoice.OrderDTO;

public class AdminDAO {
	private static final Logger LOG = LoggerFactory.getLogger(AdminDAO.class);
	private Connection conn;
	private static final String USERNAME = "javauser";
	private static final String PASSWORD = "javapass";
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?verifyServerCertificate=false&useSSL=false";
	
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	
	String sql =new String();
	public AdminDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//------------------------매출 계산을 위한 송장 리스트-------------------------------------------------
	//------------------------이번달에 처리한 송장의 리스트---------------------------------------------
	//1. 해당 월에 해당하는 송장이 처리된 송장 번호를 받는다.
	public List<String> selectThisMonth(){ // !!! 'and iState = 1'을 조건으로 추가 해야 함 !!!
		sql = "select iCode from invoice WHERE iDate >='"+curMonth()+"-01' AND iDate <'"+nextMonth()+"-01' ;";
		List<String> invoiceCodes = selectiCodeCondition(sql);
		return invoiceCodes;
	}
	//------------------------전월달에 처리한 송장의 리스트---------------------------------------------
	public List<String> selectLastMonth(){ // !!! 'and iState = 1'을 조건으로 추가 해야 함 !!!
		sql = "select iCode from invoice WHERE iDate >='"+lastMonth()+"-01' AND iDate <'"+curMonth()+"-01' ;";
		List<String> invoiceCodes = selectiCodeCondition(sql);
		return invoiceCodes;
	}
	//------------------------올해에 처리한 송장의 리스트---------------------------------------------
	public List<String> selectThisYear(int month){ // !!! 'and iState = 1'을 조건으로 추가 해야 함 !!!
		String thisMonth = String.format("%02d", month);
		String nextMonth = String.format("%02d", month+1);
		if(month != 12) {
			sql = "select iCode from invoice WHERE iDate >='"+curYear()+"-"+thisMonth+"-01' AND iDate <'"+curYear()+"-"+nextMonth+"-01' ;";
		}else {
			sql = "select iCode from invoice WHERE iDate >='"+curYear()+"-"+thisMonth+"-01' AND iDate <'"+nextYear()+"-01-01' ;";	
		}
		List<String> invoiceCodes = selectiCodeCondition(sql);
		return invoiceCodes;
	}
	
	public List<String> selectiCodeCondition(String sql){
		PreparedStatement pStmt = null;
		List<String> invoiceCodes = new ArrayList<String>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				String invoiceCode= rs.getString("iCode");
				invoiceCodes.add(invoiceCode);
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
		return invoiceCodes;
	}
		
	//2. 찾은 송장 번호와 맞는 Order제품의 가격과 개수를 가져와 총액을 구한다.
	
	public List<AdminDTO> selectOrder(String iCode){
		sql = "select o.oQuantity, p.pPrice from `order` as o "
				+ "inner join product as p on p.pCode = o.oProductCode where o.oInvoiceCode like '"+iCode+"';";
		List<AdminDTO> orderList = selectPriceCondition(sql);
		return orderList;
	}
	
	public List<AdminDTO> selectPriceCondition(String sql){
		PreparedStatement pStmt = null;
		List<AdminDTO> orderList = new ArrayList<AdminDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				AdminDTO order = new AdminDTO();
				order.setoQuantity(rs.getInt("oQuantity"));
				order.setpPrice(rs.getInt("pPrice"));
				orderList.add(order);
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
		return orderList;
	}
	
	public void close() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//이번 년도 구하는 함수
	public String curYear() {
		LocalDateTime curYear = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy");	
    	return curYear.format(dateTimeFormatter);
	}
	//다음 년도 구하는 함수
	public String nextYear() {
		LocalDateTime nextYear = LocalDateTime.now();
		nextYear = nextYear.plusYears(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy");	
    	return nextYear.format(dateTimeFormatter);
	}
	
	//이번 달을 구하는 함수
	public String curMonth() {
		LocalDateTime curMonth = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");	
    	return curMonth.format(dateTimeFormatter);
	}
	//다음달 구하는 함수
	public String nextMonth() {
		LocalDateTime nextMonth = LocalDateTime.now();
		nextMonth = nextMonth.plusMonths(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");	
    	return nextMonth.format(dateTimeFormatter);
	}
	//지난달 구하는 함수
	public String lastMonth() {
		LocalDateTime lastMonth = LocalDateTime.now();
		lastMonth = lastMonth.minusMonths(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");	
    	return lastMonth.format(dateTimeFormatter);
	}
}
