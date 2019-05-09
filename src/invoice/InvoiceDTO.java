package invoice;

import java.time.LocalDate;

public class InvoiceDTO {
	private String iCode;
	private String iName;
	private String iTel;
	private String iAddress;
	private String iAreaCode;
	private String iDate;
	private String iState;
	
	public InvoiceDTO(String[] customer) {
		this.iCode = customer[0];
		this.iName = customer[1];
		this.iTel = customer[2];
		this.iAddress = customer[3];
		this.iAreaCode = customer[4];
		this.iDate = LocalDate.now()+"";
	}

	public InvoiceDTO() {}

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

	public String getiAreaCode() {
		return iAreaCode;
	}

	public void setiAreaCode(String iAreaCode) {
		this.iAreaCode = iAreaCode;
	}

	public String getiDate() {
		return iDate;
	}

	public void setiDate(String iDate) {
		this.iDate = iDate;
	}
	
	public String getiState() {
		return iState;
	}

	public void setiState(int iState) {
		if(iState == 1) {
			this.iState = "출고 완료";			
		} else {
			this.iState = "출고 준비중";
		}
	}

	@Override
	public String toString() {
		return "InvoiceDTO [iCode=" + iCode + ", iName=" + iName + ", iTel=" + iTel + ", iAddress=" + iAddress
				+ ", iAreaCode=" + iAreaCode + ", iDate=" + iDate + "]";
	}

	
}
