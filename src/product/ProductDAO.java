package product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductDAO {
	private static final Logger LOG = LoggerFactory.getLogger(ProductDAO.class);

	public static final int ID_PASSWORD_MATCH = 1;
	public static final int ID_DOES_NOT_EXIST = 2;
	public static final int PASSWORD_IS_WRONG = 3;
	public static final int DATABASE_ERROR = -1;
	private Connection conn;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "javapass";
	private static final String URL = "jdbc:mysql://localhost:3306/fulfillmentsystem?serverTimezone=UTC&useSSL=false";
	
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	
	public ProductDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//출하 및 입고 후 개수 업데이트 메소드
	
	public void updateQuantity(String pCode, int Quantity) {
		String query = "update product set pQuantity=? where pCode =?;";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, Quantity);			
			pStmt.setString(2, pCode);			
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
		LOG.trace("제품 재고 개수 변경");
	}
	
	//------------------------ 전체 리스트 가져오기 ------------------------
	
	public List<ProductDTO> selectCategory(String pCode){
		String sql = "select * from product where pCode like '"+pCode+"%';";
		List<ProductDTO> productList = selectAllCondition(sql);
		return productList;
	}
	
	
	public List<ProductDTO> selectAll(){
		String sql = "select * from product;";
		List<ProductDTO> productList = selectAllCondition(sql);
		return productList;
	}
	
	public List<ProductDTO> selectAllCondition(String sql){
		PreparedStatement pStmt = null;
		List<ProductDTO> productList = new ArrayList<ProductDTO>();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				ProductDTO product = new ProductDTO();
				product.setpCode(rs.getString("pCode"));
				product.setpName(rs.getString("pName"));
				product.setpPrice(rs.getInt("pPrice"));
				product.setpQuantity(rs.getInt("pQuantity"));
				product.setpImgSource(rs.getString("pImgSource"));
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
	
	public ProductDTO searchOne(String pCode){
		String sql = "select * from product where pCode like'"+pCode+"';";
		ProductDTO product = selectCondition(sql);
		return product;
	}
	
	public ProductDTO selectCondition(String sql){
		PreparedStatement pStmt = null;
		ProductDTO product = new ProductDTO();
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				product.setpCode(rs.getString("pCode"));
				product.setpName(rs.getString("pName"));
				product.setpPrice(rs.getInt("pPrice"));
				product.setpQuantity(rs.getInt("pQuantity"));
				product.setpImgSource(rs.getString("pImgSource"));
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
		return product;
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
