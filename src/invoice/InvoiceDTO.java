package invoice;

public class InvoiceDTO {
	private String iCode;
	private String iCustomerCode;
	private String iProductCode;
	private int iQuantity;
	private String iAreaCode;
	private String iDate;
	
	public InvoiceDTO(String iCode, String iCustomerCode, String iProductCode, int iQuantity, String iAreaCode,
			String iDate) {
		this.iCode = iCode;
		this.iCustomerCode = iCustomerCode;
		this.iProductCode = iProductCode;
		this.iQuantity = iQuantity;
		this.iAreaCode = iAreaCode;
		this.iDate = iDate;
	}
	
	public InvoiceDTO() {}

	public String getiCode() {
		return iCode;
	}

	public String getiCustomerCode() {
		return iCustomerCode;
	}

	public String getiProductCode() {
		return iProductCode;
	}

	public int getiQuantity() {
		return iQuantity;
	}

	public String getiAreaCode() {
		return iAreaCode;
	}

	public String getiDate() {
		return iDate;
	}

	public void setiCode(String iCode) {
		this.iCode = iCode;
	}

	public void setiCustomerCode(String iCustomerCode) {
		this.iCustomerCode = iCustomerCode;
	}

	public void setiProductCode(String iProductCode) {
		this.iProductCode = iProductCode;
	}

	public void setiQuantity(int iQuantity) {
		this.iQuantity = iQuantity;
	}

	public void setiAreaCode(String iAreaCode) {
		this.iAreaCode = iAreaCode;
	}

	public void setiDate(String iDate) {
		this.iDate = iDate;
	}

	@Override
	public String toString() {
		return "InvoiceDTO [iCode=" + iCode + ", iCustomerCode=" + iCustomerCode + ", iProductCode=" + iProductCode
				+ ", iQuantity=" + iQuantity + ", iAreaCode=" + iAreaCode + ", iDate=" + iDate + "]";
	}
	
}
