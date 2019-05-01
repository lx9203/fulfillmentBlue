package invoice;

public class InvoiceCustomerDTO {
	private String iName;
	private String iTel;
	private String iAddress;
	
	private String iCustomerCode; //고객 코드 
	
	public InvoiceCustomerDTO() {}

	public String getiName() {
		return iName;
	}

	public void setiName(String iName) {
		this.iName = iName;
	}

	public String getiTel() {
		return iTel;
	}

	public void setiTel(String iTel) {
		this.iTel = iTel;
	}

	public String getiAddress() {
		return iAddress;
	}

	public void setiAddress(String iAddress) {
		this.iAddress = iAddress;
	}

	public String getiCustomerCode() {
		return iCustomerCode;
	}

	public void setiCustomerCode(String iCustomerCode) {
		this.iCustomerCode = iCustomerCode;
	}

	@Override
	public String toString() {
		return "InvoiceCustomerDTO [iName=" + iName + ", iTel=" + iTel + ", iAddress=" + iAddress + ", iCustomerCode="
				+ iCustomerCode + "]";
	}
	
	

}
