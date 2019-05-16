package function;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import invoice.InvoiceDAO;

public class Test {
	public static void main(String[] args) {
		String str = iCodeProc("ashop","1area");
		System.out.println(str);
		
	}
	
	public static String iCodeProc(String shopping, String areaCode) {
    	InvoiceDAO iDao = new InvoiceDAO();
		char shoppingCode = shopping.charAt(0);
		char area = areaCode.charAt(0);
		Date curDate = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
    	int increment =0;
    	Optional<String> op = Optional.ofNullable(iDao.selectOneDayLast().getiCode());
    	System.out.println(op.toString());
		if(!op.isPresent()) {
			increment =10001; //송장번호를 생성시 당일 첫 송장번호는 10001부터 시작
		} else {
			String iCode = iDao.selectOneDayLast().getiCode();
			increment = Integer.parseInt(iCode.substring(0,5))+1;
			System.out.println(increment);
		}	
    	
		return increment + Character.toString(shoppingCode)+Character.toString(area)+sdf.format(curDate);
	}

}
