package product;

public class ProductDTO {
	private String pCode; //제품 코드
	private String pName;
	private int pPrice;
	private int pQuantity;
	private String pImgSource;
	
	
	
	public ProductDTO(String pCode, int pQuantity) {
		this.pCode = pCode;
		this.pQuantity = pQuantity;
	}


	public ProductDTO() {}
	
	
	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}
	
	public int getpPrice() {
		return pPrice;
	}

	public void setpPrice(int pPrice) {
		this.pPrice = pPrice;
	}

	public int getpQuantity() {
		return pQuantity;
	}

	public void setpQuantity(int pQuantity) {
		this.pQuantity = pQuantity;
	}

	public String getpImgSource() {
		return pImgSource;
	}

	public void setpImgSource(String pImgSource) {
		this.pImgSource = pImgSource;
	}

	@Override
	public String toString() {
		return "ProductDTO [pCode=" + pCode + ", pName=" + pName + ", pPrice=" + pPrice + ", pQuantity=" + pQuantity
				+ ", pImgSource=" + pImgSource + "]";
	}
	
	

}
