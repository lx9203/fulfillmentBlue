package user;


import java.awt.FileDialog;
import java.awt.Frame;
import java.util.regex.Pattern;

public class Test{
	public static void main(String[] args) {
		/*int a = isValidForm("asdfg","asdfg");
		int b = isValidForm("asdfg","asdfg453425");
		int c = isValidForm("as","asdfg");
		
		System.out.println("결과 : " + a);
		System.out.println("결과 : " + b);
		System.out.println("결과 : " + c);
		*/
		FileTest();
	}
	
	public static int isValidForm( String pwd1, String pwd2) {
		String rgx_pwd = "[a-zA-Z0-9!@#$%^*+=-_]{4,20}";
		if (!Pattern.matches(rgx_pwd, pwd1)) {
			return 2;
		}
		if (!pwd1.equals(pwd2)) {
			return 3;
		}
		return 1;
	}
	
	public static String FileTest(){
        Frame f1 = new Frame();
        //파일 열기(프레임선택, 파일선택창 Title, 파일 선택 유형)
        FileDialog f = new FileDialog(f1,"송장 파일 선택",FileDialog.LOAD);  
        f.setSize(300, 300);
        f.setLocation(0, 100);
        f.setDirectory("c:\\Temp"); //파일선택창 기본 경로 지정
        f.setVisible(true); //파일선택창 보이게 설정
        
        return f.getDirectory()+f.getFile();
    }
}
