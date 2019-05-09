package admin;

public class AdminDTO {
	private String iCode;
	private String pCode;
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
	
	
	

}
