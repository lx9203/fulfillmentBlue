package invoice;

public class OrderDTO {
	private int oNum; //읽어오기
	private String oProductCode; //기록
	private String oProductName; //읽어오기
	private String oInvoiceCode; //기록
	private int oQuantity; //기록, 읽어오기
	private int oPrice; // 읽어오기
	private int oTotalPrice; //만들어서 넣기 
	
	public OrderDTO(String oProductCode, String oInvoiceCode, int oQuantity) {
		this.oProductCode = oProductCode;
		this.oInvoiceCode = oInvoiceCode;
		this.oQuantity = oQuantity;
	}

	public OrderDTO() {}

	public int getoNum() {
		return oNum;
	}

	public void setoNum(int oNum) {
		this.oNum = oNum;
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

	public int getoTotalPrice() {
		return oTotalPrice;
	}

	public void setoTotalPrice(int oTotalPrice) {
		this.oTotalPrice = oTotalPrice;
	}

	@Override
	public String toString() {
		return "OrderDTO [oNum=" + oNum + ", oProductCode=" + oProductCode + ", oProductName=" + oProductName
				+ ", oInvoiceCode=" + oInvoiceCode + ", oQuantity=" + oQuantity + ", oPrice=" + oPrice + "]";
	}
	
	

}
