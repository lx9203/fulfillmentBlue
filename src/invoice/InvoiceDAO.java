package invoice;

import java.sql.*;
import java.util.*;

import org.slf4j.*;

import product.*;

public class InvoiceDAO {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceDAO.class);
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
	
	//------------------------ ?†ÑÏ≤? Î¶¨Ïä§?ä∏ Í∞??†∏?ò§Í∏? ------------------------
	
	public List<InvoiceDTO> selectAll(){
		String sql = "select iCode, iCustomerCode, iProductCode, iQuantity, iAreaCode, iDate from invoice;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	//------------------------ ?Ü°?û• Î≤àÌò∏?óê ÎßûÎäî Î¶¨Ïä§?ä∏ Í∞??†∏?ò§Í∏? ---------------------
	
	public List<InvoiceDTO> selectAllInvoice(char AreaCode){
		String sql = "select iCode, iCustomerCode, iProductCode, iQuantity, iAreaCode, iDate from invoice;";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	//------------------------ ?ã®?ùº Î¶¨Ïä§?ä∏ Í∞??†∏?ò§Í∏? ------------------------
	
	public List<InvoiceDTO> selectOne(){
		String sql = "select iCode, iCustomerCode, iProductCode, iQuantity, iAreaCode, iDate from invoice where iCode=?";
		List<InvoiceDTO> invoiceList = selectAllCondition(sql);
		return invoiceList;
	}
	
	//------------------------ Select All Condition ------------------------
	
	public List<InvoiceDTO> selectAllCondition(String sql){
		PreparedStatement pStmt = null;
		List<InvoiceDTO> invoiceList = new ArrayList<InvoiceDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				InvoiceDTO invoice = new InvoiceDTO();
				invoice.setiCode(rs.getString("iCode"));
				invoice.setiCustomerCode(rs.getString("iCustomerCode"));
				invoice.setiProductCode(rs.getString("iProductCode"));
				invoice.setiQuantity(rs.getInt("iQuantity"));
				invoice.setiAreaCode(rs.getString("iAreaCode"));
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
	
	public void close() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
