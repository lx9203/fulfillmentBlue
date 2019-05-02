package user;

public class UserDTO {
	
	//유저의 아이디의 첫 숫자는 1:운송회사 2:쇼핑몰 3:구매처 를 의미 한다.
	
	private int userType;
	private String id;
	private String name;
	private String password;
	
	public UserDTO(int userType, String id, String name, String password) {
		this.userType = userType;
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public UserDTO() {}
	
	public int getuserType() {
		return userType;
	}

	public void setuserType(int userType) {
		this.userType = userType;
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
		return "UserDTO [userType=" + userType + ", id=" + id + ", name=" + name + ", password=" + password + "]";
	}

	
	
	

}
