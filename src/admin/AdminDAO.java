package admin;

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
		LOG.trace("[AdminDAO] 월별 출고 완료 송장 리스트");
		sql = "select iCode from invoice WHERE iDate >='"+cf.curMonth()+"-01' AND iDate <'"+cf.nextMonth(cf.curMonth())+"-01' and iState = 2;";
		List<String> invoiceCodes = selectiCodeCondition(sql);
		return invoiceCodes;
	}
	//2. 처리신청을 한 송장의 건수를 가져온다.
	public List<String> selectThisMonthReady(){
		LOG.trace("[AdminDAO] 출고 신청 송장 리스트");
		sql = "select iCode from invoice WHERE iState = 1;";
		List<String> invoiceCodes = selectiCodeCondition(sql);
		return invoiceCodes;
	}
	
	//------------------------전년도에 처리한 송장의 리스트---------------------------------------------
	public List<String> selectLastYear(){
		LOG.trace("[AdminDAO] 전년도 출고 완료 송장 리스트");
		sql = "select iCode from invoice WHERE iDate >='"+cf.lastYear(cf.curYear())+"-01-01' AND iDate <'"+cf.curYear()+"-01-01' and iState = 2;";
		List<String> invoiceCodes = selectiCodeCondition(sql);
		return invoiceCodes;
	}
	//------------------------올해에 처리한 송장의 리스트---------------------------------------------
	public List<String> selectYear(String year,int month){
		LOG.trace("[AdminDAO] 이번 년도 출고 완료 송장 리스트");
		String thisMonth = String.format("%02d", month);
		String nextMonth = String.format("%02d", month+1);
		if(month != 12) {
			sql = "select iCode from invoice WHERE iDate >='"+year+"-"+thisMonth+"-01' AND iDate <'"+year+"-"+nextMonth+"-01' and iState = 2  ;";
		}else {
			sql = "select iCode from invoice WHERE iDate >='"+year+"-"+thisMonth+"-01' AND iDate <'"+cf.nextYear(year)+"-01-01' and iState = 2  ;";	
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
		
	
	
	//------------------------------------ 타입별 메소드 --------------------------------------------
	
	//[공통] 해당 월의 처리 완료된 송장 리스트 가져오기
		public List<AdminDTO> adminMonthList(String month){
			LOG.trace("[AdminDAO] 선택 월에 출고 완료 송장 리스트");
			String sql = "select iCode, iDate, iAreaCode from invoice "
					+ "where iDate >='"+month+"-01' and iDate < '"+cf.nextMonth(month)+"-01' and iState = 2 ;";
			List<AdminDTO> invoiceList = selectTransCondition(sql);
			return invoiceList;
		}
	//-----------------------------1. 재고 관련 ----------------------------------------------------------------
		public AdminDTO selectProductOne(String pCode){//제품이 출고 대기중이 수량
			LOG.trace("[AdminDAO] 물품 재고 총 수량");
			String sql = "select o.oProductCode, SUM(o.oQuantity) from `order` as o "
					+ "inner join invoice as i on i.iCode=o.oInvoiceCode "
					+ "where o.oProductCode like '"+pCode+"' and iState < 2 "
					+ "group by o.oProductCode;";
			AdminDTO order = selectProductOneCondition(sql);
			return order;
		}
		
		public AdminDTO selectProductOneCondition(String sql){
			PreparedStatement pStmt = null;
			AdminDTO order = new AdminDTO();
			try {
				pStmt = conn.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();
				
				while(rs.next()){
					order.setpCode(rs.getString("oProductCode"));
					order.setoQuantity(rs.getInt("SUM(o.oQuantity)"));
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
			return order;
		}
			
	//-----------------------------2. 월별 제품 판매 수량 관련 -------------------------------------------------
		public List<AdminDTO> selectProductMonth(String month){ //정해진 달에 제품이 팔린 수량
			LOG.trace("[AdminDAO] 선택 월에 물품 판매 수량 리스트");
			String sql = "select o.oProductCode, SUM(o.oQuantity) from `order` as o "
					+ "inner join invoice as i on i.iCode=o.oInvoiceCode "
					+ "where i.iDate >='"+month+"-01' and i.iDate < '"+cf.nextMonth(month)+"-01' and iState = 2 "
					+ "group by o.oProductCode;";
			List<AdminDTO> orderList = selectProductCondition(sql);
			return orderList;
		}
		
		public List<AdminDTO> selectProductCondition(String sql){
			PreparedStatement pStmt = null;
			List<AdminDTO> orderList = new ArrayList<AdminDTO>();
			try {
				pStmt = conn.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();
				
				while(rs.next()){
					AdminDTO order = new AdminDTO();
					order.setpCode(rs.getString("oProductCode"));
					order.setoQuantity(rs.getInt("SUM(o.oQuantity)"));
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
	
	//-------------------------------3. 운송사에서 사용하는 리스트---------------------------------------------------------
	//1.처리 준비중인 송장 리스트 가져오기
	public List<AdminDTO> selectTransList(){
		LOG.trace("[AdminDAO] 출고 신청 송장 리스트");
		String sql = "select iCode, iDate, iAreaCode from invoice "
				+ "where iState = 1 or iState = 3 order by iState desc;";
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
	
	//2. 찾은 송장 번호와 맞는 Order제품의 가격과 개수를 가져와 총액을 구한다.
	
		public List<AdminDTO> selectOrderList(String iCode){
			sql = "select p.pCode, o.oQuantity, p.pQuantity, p.pPrice from `order` as o "
					+ "inner join product as p on p.pCode = o.oProductCode where o.oInvoiceCode like '"+iCode+"';";
			List<AdminDTO> orderList = selectOrderCondition(sql);
			return orderList;
		}
		
		public List<AdminDTO> selectOrderCondition(String sql){
			PreparedStatement pStmt = null;
			List<AdminDTO> orderList = new ArrayList<AdminDTO>();
			try {
				pStmt = conn.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();
				
				while(rs.next()){
					AdminDTO order = new AdminDTO();
					order.setpCode(rs.getString("pCode"));
					order.setoQuantity(rs.getInt("oQuantity"));
					order.setpQuantity(rs.getInt("pQuantity"));
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
//----------------------------- 출고 승인 완료 (1 -> 2) 관련 메소드 -------------------------
	public void invoiceCompleteState(String iCode) { // 출고 완료 (State = 2)
		LOG.trace("[AdminDAO] 출고 승인 완료");
		String query = "update invoice set iState=2 where iCode =?;";
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
	
	public void invoiceOutofStockState(String iCode) { // 재고 부족 (State = 3)
		LOG.trace("[AdminDAO] 출고 승인 완료");
		String query = "update invoice set iState=3 where iCode =?;";
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
	
//--------------------------------------- 발주 내역에서 사용하는 메소드 ----------------------------------
	public List<AdminDTO> selectSupplyList(){ //발주신청이 들어와 있는 발주리스트
		LOG.trace("[AdminDAO] 발주 승인 신청 리스트");
		String sql = "select p.pCode, p.pName, p.pPrice, s.sQuantity, s.sCode, s.sDate from supply as s " 
					+"inner join product as p on p.pCode = s.sProductCode where s.sState = 1 "
					+"order by s.sDate;";
		List<AdminDTO> supplyList = selectSupplyCondition(sql);
		return supplyList;
	}
	
	public List<AdminDTO> selectSupplyListMonth(String month){ //월별 발주가 완료된 발주 리스트
		LOG.trace("[AdminDAO] 월별 발주 완료 리스트");
		String sql = "select p.pCode, p.pName, p.pPrice, s.sQuantity, s.sCode, s.sDate from supply as s " 
					+"inner join product as p on p.pCode = s.sProductCode "
					+"where s.sDate >='"+month+"-01' and s.sDate < '"+cf.nextMonth(month)+"-01' and s.sState = 2 "
					+"order by s.sDate;";
		List<AdminDTO> supplyList = selectSupplyCondition(sql);
		return supplyList;
	}
	
	public List<AdminDTO> selectSupplyCondition(String sql){
		PreparedStatement pStmt = null;
		List<AdminDTO> supplyList = new ArrayList<AdminDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				AdminDTO supply = new AdminDTO();
				supply.setpCode(rs.getString("pCode"));
				supply.setpName(rs.getString("pName"));
				supply.setpPrice(rs.getInt("pPrice"));
				supply.setsQuantity(rs.getInt("sQuantity"));
				supply.setsTotalPrice(supply.getpPrice()*supply.getsQuantity());
				supply.setsCode(rs.getString("sCode"));
				supply.setsDate(rs.getString("sDate"));
				supplyList.add(supply);
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
		return supplyList;
	}
//------------------------------- 발주 승인(1 ->2) 처리 메소드 ---------------------------
	public void supplyCompleteState(String sCode) { // 발주 승인 (State = 2)
		LOG.trace("[AdminDAO] 발주 승인 처리");
		String query = "update supply set sState=2 where sCode =?;";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1,sCode);
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
	
	public void close() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
