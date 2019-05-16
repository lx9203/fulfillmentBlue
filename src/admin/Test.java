package admin;

public class Test {
	public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {
			boolean k = true;
			for (int j = 0; j <4; j++) {
				if(i>2) {
					k=false;
					break;
				}
				System.out.println("j는"+j);
			}
			if(k) System.out.println("i는"+i);
		}
		
	}
}
