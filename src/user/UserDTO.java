package user;

public class UserDTO {
	
	//유저의 아이디의 첫 숫자는 1:운송회사 2:쇼핑몰 3:구매처 를 의미 한다.
	
	private String area;
	private String id;
	private String name;
	private String password;
	
	public UserDTO(String area, String id, String name, String password) {
		this.area = area;
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public UserDTO() {}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDTO [area=" + area + ", id=" + id + ", name=" + name + ", password=" + password + "]";
	}

	
	
	

}
