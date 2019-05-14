package supply;

public class SupplyDTO {
	private String sCode;	// 기록, 읽어오기
	private String sProductCode;	// 기록, 읽어오기
	private String sProductName;	// 읽어오기
	private int sProductPrice;	// 읽어오기
	private String sDate;	// 기록, 읽어오기
	private int sQuantity;	// 기록, 읽어오기
	private String sState;	// 기록
	private int sTotalPrice; // 기록, 읽어오기
	
	public String getsProductName() {
		return sProductName;
	}

	public void setsProductName(String sProductName) {
		this.sProductName = sProductName;
	}

	public int getsProductPrice() {
		return sProductPrice;
	}

	public void setsProductPrice(int sProductPrice) {
		this.sProductPrice = sProductPrice;
	}
	
	public SupplyDTO() {}
	
	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getsProductCode() {
		return sProductCode;
	}

	public void setsProductCode(String sProductCode) {
		this.sProductCode = sProductCode;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public int getsQuantity() {
		return sQuantity;
	}

	public void setsQuantity(int sQuantity) {
		this.sQuantity = sQuantity;
	}

	public String getsState() {
		return sState;
	}

	public void setsState(int sState) {
		switch(sState) {
		case 0 :
			this.sState = "대기중";
			break;
		case 1 :
			this.sState = "발송중";
			break;
		}
	}

	public int getsTotalPrice() {
		return sTotalPrice;
	}

	public void setsTotalPrice(int sQuantity, int sProductPrice) {
		this.sTotalPrice = (sQuantity*sProductPrice);
	}

	@Override
	public String toString() {
		return "SupplyDTO [sCode=" + sCode + ", sProductCode=" + sProductCode + ", sProductName=" + sProductName
				+ ", sProductPrice=" + sProductPrice + ", sDate=" + sDate + ", sQuantity=" + sQuantity + ", sState="
				+ sState + ", sTotalPrice=" + sTotalPrice + "]";
	}
}
