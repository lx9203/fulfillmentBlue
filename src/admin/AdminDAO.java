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

import function.*;
import user.*;


public class AdminDAO {
	private static final Logger LOG = LoggerFactory.getLogger(AdminDAO.class);
	private Connection conn;
	private static final String USERNAME = "javauser";
	private static final String PASSWORD = "javapass";
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?verifyServerCertificate=false&useSSL=false";
	
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	CustomerFunction cf = new CustomerFunction();
	
	UserDTO uDto = new UserDTO();
	UserDAO uDao = new UserDAO();
	
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
	public List<String> selectThisMonth(){
		sql = "select iCode from invoice WHERE iDate >='"+cf.curMonth()+"-01' AND iDate <'"+cf.nextMonth(cf.curMonth())+"-01' and iState = 2;";
		List<String> invoiceCodes = selectiCodeCondition(sql);
		return invoiceCodes;
	}
	//------------------------전년도에 처리한 송장의 리스트---------------------------------------------
	public List<String> selectLastYear(){ // !!! 'and iState = 1'을 조건으로 추가 해야 함 !!!
		sql = "select iCode from invoice WHERE iDate >='"+cf.lastYear(cf.curYear())+"-01-01' AND iDate <'"+cf.curYear()+"-01-01' and iState = 2;";
		List<String> invoiceCodes = selectiCodeCondition(sql);
		return invoiceCodes;
	}
	//------------------------올해에 처리한 송장의 리스트---------------------------------------------
	public List<String> selectThisYear(int month){ // !!! 'and iState = 1'을 조건으로 추가 해야 함 !!!
		String thisMonth = String.format("%02d", month);
		String nextMonth = String.format("%02d", month+1);
		if(month != 12) {
			sql = "select iCode from invoice WHERE iDate >='"+cf.curYear()+"-"+thisMonth+"-01' AND iDate <'"+cf.curYear()+"-"+nextMonth+"-01' and iState = 2  ;";
		}else {
			sql = "select iCode from invoice WHERE iDate >='"+cf.curYear()+"-"+thisMonth+"-01' AND iDate <'"+cf.nextYear(cf.curYear())+"-01-01' and iState = 2  ;";	
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
		sql = "select p.pCode, o.oQuantity, p.pPrice from `order` as o "
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
				order.setpCode(rs.getString("pCode"));
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
	
	//------------------------------------ 타입별 메소드 --------------------------------------------
	
	//2. 운송사 관련 메소드 
	
	//-------------------------------운송사에서 사용하는 리스트---------------------------------------------------------
	public List<AdminDTO> adminTransDay(){
		String sql = "select iCode, iDate, iAreaCode from invoice "
				+ "where iDate >'"+cf.curDate()+"' and iDate < '"+cf.tomorrow(cf.curDate())+"' and iState = 2;";
		List<AdminDTO> invoiceList = selectTransCondition(sql);
		return invoiceList;
	}
	
	public List<AdminDTO> selectTransCondition(String sql){
		PreparedStatement pStmt = null;
		List<AdminDTO> invoiceList = new ArrayList<AdminDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				AdminDTO invoice = new AdminDTO();
				uDto = uDao.searchById(rs.getString("iAreaCode"));
				invoice.setiCode(rs.getString("iCode"));
				invoice.setiDate(rs.getString("iDate"));
				invoice.setuName(uDto.getName());
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
	
	public void close() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
