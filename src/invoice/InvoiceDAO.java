package invoice;

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

public class InvoiceDAO {
	private static final Logger LOG = LoggerFactory.getLogger(OrderDAO.class);
	private Connection conn;
	private static final String USERNAME = "javauser";
	private static final String PASSWORD = "javapass";
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?verifyServerCertificate=false&useSSL=false";
	
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	
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
	
	
	//------------------------여러개의 송장번호를 리스트로 가져오기-------------------------------------------
	public List<InvoiceDTO> selectAllDay(String day){
		String sql = "select iCode, iName, iTel, iAddress, iDate from invoice where iDate like '%"+day+"일%';";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	public List<InvoiceDTO> selectAllMonth(String month){
		String sql = "select iCode, iName, iTel, iAddress, iDate from invoice "
				+ "where iDate like '%"+month+"월%';";
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
				invoice.setiDate(rs.getString("iDate"));
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
    	
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시");
    	
    	return curTime.format(dateTimeFormatter);
	}
}
