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
	// ------------전체 리스트 출력-------------------------------------
	
	public List<InvoiceProductDTO> selectAllProduct(char code){
		String sql = "select p.pName, ip.iQuantity, p.pPrice from invoiceproduct as ip\r\n" + 
				"	inner join product as p on p.pcode=ip.iProductCode where ip.iCustomerCode like'"+code+"%';"; 
		List<InvoiceProductDTO> productList = selectConditionProduct(sql);
		return productList;
		
	}
	
	public List<InvoiceProductDTO> selectConditionProduct(String query) {
		PreparedStatement pStmt = null;
		List<InvoiceProductDTO> productList = new ArrayList<InvoiceProductDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				InvoiceProductDTO product = new InvoiceProductDTO();
				product.setiProductName(rs.getString("pName"));
				product.setiQuantity(rs.getInt("iQuantity"));
				product.setiProductPrice(rs.getInt("pPrice"));
				product.setiProductTotal(product.getiProductPrice()*product.getiQuantity());
				productList.add(product);
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
		return productList;
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
