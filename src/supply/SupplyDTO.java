package supply;

import java.time.*;

public class SupplyDTO {
	private String sCode;	// 기록, 읽어오기
	private String sProductCode;	// 기록
	private String sProductName;	// 읽어오기
	private int sProductPrice;	// 읽어오기
	private String sDate;	// 기록, 읽어오기
	private int sQuantity;	// 기록, 읽어오기
	private int sState;	// 기록
	private int sTotalPrice; // 기록
	
	@Override
	public String toString() {
		return "SupplyDTO [sCode=" + sCode + ", sProductCode=" + sProductCode + ", sProductName=" + sProductName
				+ ", sProductPrice=" + sProductPrice + ", sDate=" + sDate + ", sQuantity=" + sQuantity + ", sState="
				+ sState + ", sTotalPrice=" + sTotalPrice + "]";
	}

	

	public SupplyDTO(String sCode, String sProductCode, String sProductName, int sProductPrice, String sDate,
			int sQuantity, int sState, int sTotalPrice) {
		super();
		this.sCode = sCode;
		this.sProductCode = sProductCode;
		this.sProductName = sProductName;
		this.sProductPrice = sProductPrice;
		this.sDate = sDate;
		this.sQuantity = sQuantity;
		this.sState = sState;
		this.sTotalPrice = sTotalPrice;
	}

	public SupplyDTO(String[] supply) {
		this.sCode = supply[0];
		this.sProductCode = supply[0];
		this.sProductName = supply[1];
		this.sProductPrice = Integer.parseInt(supply[2]);
		this.sDate = LocalDate.now()+"";
		this.sQuantity = Integer.parseInt(supply[3]);
		this.sState = Integer.parseInt(supply[4]);
		this.sTotalPrice = Integer.parseInt(supply[4])*Integer.parseInt(supply[3]);
	}
	
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

	public SupplyDTO(int sState) {
		this.sState = sState;
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

	public int getsState() {
		return sState;
	}

	public void setsState(int sState) {
		this.sState = sState;
	}

	public int getsTotalPrice() {
		return sTotalPrice;
	}

	public void setsTotalPrice(int sTotalPrice) {
		this.sTotalPrice = sTotalPrice;
	}
	
}
