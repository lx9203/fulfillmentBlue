package admin;

import function.CustomerFunction;

public class Test {
	public static void main(String[] args) {
		CustomerFunction cf = new CustomerFunction();
		String str = cf.curMonth().substring(5); 
		int num = Integer.parseInt(str);
		System.out.println(str);
		System.out.println(num);
	}
}
