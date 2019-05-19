package function;

public class CookieFunction {
	public String userType(int userType) {
		String user = new String();
		switch(userType) {
		case 0:
			user = "admin";
			break;
		case 1:
			user = "trans";
			break;
		case 2:
			user = "mall";
			break;
		case 3:
			user = "supply";
			break;
		}
		return user;
	}

}
