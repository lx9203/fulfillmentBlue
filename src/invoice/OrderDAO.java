package invoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OrderDAO {
	private static final Logger LOG = LoggerFactory.getLogger(OrderDAO.class);
	private Connection conn;
	private static final String USERNAME = "javauser";
	private static final String PASSWORD = "javapass";
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?verifyServerCertificate=false&useSSL=false";
	
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	
	public OrderDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertOrder(OrderDTO order) {
		String query = "insert into order (oNum,oProductCode,oQuantity,oInvoiceCode) values(?,?,?,?);";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, order.getoNum());
			pStmt.setString(2, order.getoProductCode());
			pStmt.setInt(3, order.getoQuantity());
			pStmt.setString(4, order.getoInvoiceCode());
			
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
	
	public List<OrderDTO> selectAll(String iCode){
		String sql = "select o.oNum, p.pName, o.oQuantity, p.pPrice from order as o "
				+ "inner join product as p on p.pCode=o.oProductCode where o.oInvoiceCode like'"+iCode+"';";
		List<OrderDTO> orderList = selectAllCondition(sql);
		return orderList;
	}
	
	public List<OrderDTO> selectAllCondition(String sql){
		PreparedStatement pStmt = null;
		List<OrderDTO> orderList = new ArrayList<OrderDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				OrderDTO order = new OrderDTO();
				order.setoNum(rs.getInt("oNum"));
				order.setoProductName(rs.getString("pName"));
				order.setoPrice(rs.getInt("pPrice"));
				order.setoQuantity(rs.getInt("oQuantity"));
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

}
