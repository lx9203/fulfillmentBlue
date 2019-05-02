package order;

public class OrderDTO {
	private String oCode; //주문 번호 
	private String oProductCode;
	private String oProductName;
	private String oInvoiceCode;
	private int oQuantity;
	private int oPrice;
	
	public OrderDTO() {}

	public String getoCode() {
		return oCode;
	}

	public void setoCode(String oCode) {
		this.oCode = oCode;
	}

	public String getoProductName() {
		return oProductName;
	}

	public String getoProductCode() {
		return oProductCode;
	}

	public void setoProductCode(String oProductCode) {
		this.oProductCode = oProductCode;
	}

	public void setoProductName(String oProductName) {
		this.oProductName = oProductName;
	}

	public String getoInvoiceCode() {
		return oInvoiceCode;
	}

	public void setoInvoiceCode(String oInvoiceCode) {
		this.oInvoiceCode = oInvoiceCode;
	}

	public int getoQuantity() {
		return oQuantity;
	}

	public void setoQuantity(int oQuantity) {
		this.oQuantity = oQuantity;
	}

	public int getoPrice() {
		return oPrice;
	}

	public void setoPrice(int oPrice) {
		this.oPrice = oPrice;
	}
	
	

}
