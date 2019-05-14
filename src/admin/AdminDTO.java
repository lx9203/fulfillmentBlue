package admin;

public class AdminDTO {
	private String iCode;
	private String pCode;
	private String iDate;
	private String uName;
	private String pName;
	private int iTotalPrice;
	private int pQuantity;
	private int oQuantity;
	private int pPrice;
	
	public AdminDTO() {}
	
	public AdminDTO(String iCode, String pCode, int oQuantity, int pPrice) {
		this.iCode = iCode;
		this.pCode = pCode;
		this.oQuantity = oQuantity;
		this.pPrice = pPrice;
	}

	public String getiCode() {
		return iCode;
	}

	public void setiCode(String iCode) {
		this.iCode = iCode;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public int getoQuantity() {
		return oQuantity;
	}

	public void setoQuantity(int oQuantity) {
		this.oQuantity = oQuantity;
	}

	public int getpPrice() {
		return pPrice;
	}

	public void setpPrice(int pPrice) {
		this.pPrice = pPrice;
	}

	public String getiDate() {
		return iDate;
	}

	public void setiDate(String iDate) {
		this.iDate = iDate;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public int getiTotalPrice() {
		return iTotalPrice;
	}

	public void setiTotalPrice(int iTotalPrice) {
		this.iTotalPrice = iTotalPrice;
	}

	public int getpQuantity() {
		return pQuantity;
	}

	public void setpQuantity(int pQuantity) {
		this.pQuantity = pQuantity;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}
	
	
	

}
