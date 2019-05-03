package invoice;

public class InvoiceCustomerDTO {
	private String iCode;
	private String iName;
	private String iTel;
	private String iAddress;
	
	public InvoiceCustomerDTO() {}

	public String getiCode() {
		return iCode;
	}

	public void setiCode(String iCode) {
		this.iCode = iCode;
	}

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

	
	
	

}
