package invoice;

public class InvoiceProductDTO {
	private String iProductCode; //?†ú?íà ÏΩîÎìú
	private String iProductName;
	private int iProductPrice;
	private	int iProductTotal;
	private int iQuantity;
	
	private String iCustomerCode; //Í≥†Í∞ù ÏΩîÎìú 
	
	public InvoiceProductDTO(){}

	public String getiProductCode() {
		return iProductCode;
	}

	public void setiProductCode(String iProductCode) {
		this.iProductCode = iProductCode;
	}

	public String getiProductName() {
		return iProductName;
	}

	public void setiProductName(String iProductName) {
		this.iProductName = iProductName;
	}

	public int getiQuantity() {
		return iQuantity;
	}

	public void setiQuantity(int iQuantity) {
		this.iQuantity = iQuantity;
	}

	public String getiCustomerCode() {
		return iCustomerCode;
	}

	public void setiCustomerCode(String iCustomerCode) {
		this.iCustomerCode = iCustomerCode;
	}

	public int getiProductPrice() {
		return iProductPrice;
	}

	public void setiProductPrice(int iProductPrice) {
		this.iProductPrice = iProductPrice;
	}

	public int getiProductTotal() {
		return iProductTotal;
	}

	public void setiProductTotal(int iProductTotal) {
		this.iProductTotal = iProductTotal;
	}
	
	
	


}
