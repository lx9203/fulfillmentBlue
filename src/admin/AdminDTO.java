package admin;

public class AdminDTO {
	private String iCode; //송장 번호(쇼핑몰, 운송사)
	private String pCode; //제품 번호
	private String sCode; //발주 코드(공급사)
	private String iDate; //송장 날짜
	private String sDate; //발주 날짜
	private String uName; //유저 이름(운송사이름)
	private String pName; //제품 이름
	private int iTotalPrice; //송장내 총 금액
	private int sTotalPrice; //발주물품 총 금액
	private int pQuantity; //재고 수량
	private int oQuantity; //송장 주문 수량
	private int sQuantity; //발주 수량
	private int pPrice; //제품가격
	private String iState;
	
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
		this.iDate = iDate.substring(0, 10);
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

	public int getsQuantity() {
		return sQuantity;
	}

	public void setsQuantity(int sQuantity) {
		this.sQuantity = sQuantity;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate.substring(0, 10);
	}

	public int getsTotalPrice() {
		return sTotalPrice;
	}

	public void setsTotalPrice(int sTotalPrice) {
		this.sTotalPrice = sTotalPrice;
	}

	public String getiState() {
		return iState;
	}

	public void setiState(int iState) {
		switch(iState) {
		case 1:
			this.iState = "승인 대기";
			break;
		case 3:
			this.iState = "재고 부족";
			break;
		default:
			this.iState = "오류";
			break;
		}
	}
	
	
	

}
