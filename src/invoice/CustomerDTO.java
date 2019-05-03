package invoice;

public class CustomerDTO {
	private String cCode;
	private String cName;
	private String cTel;
	private String cAddress;
	
	public CustomerDTO(String cCode, String cName, String cTel, String cAddress) {
		this.cCode = cCode;
		this.cName = cName;
		this.cTel = cTel;
		this.cAddress = cAddress;
	}
	
	public CustomerDTO() {}

	public String getcCode() {
		return cCode;
	}

	public String getcName() {
		return cName;
	}

	public String getcTel() {
		return cTel;
	}

	public String getcAddress() {
		return cAddress;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public void setcTel(String cTel) {
		this.cTel = cTel;
	}

	public void setcAddress(String cAddress) {
		this.cAddress = cAddress;
	}

	@Override
	public String toString() {
		return "CustomerDTO [cCode=" + cCode + ", cName=" + cName + ", cTel=" + cTel + ", cAddress=" + cAddress + "]";
	}
	
}
