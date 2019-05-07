package invoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
    	
    	
    	
    	
    /*	String shopping = "aMall";
    	String areaCode = "1area";
    	*/
    	/*int count = 10001;
    	char shoppingCode = shopping.charAt(0);
    	System.out.println((char)shoppingCode);
		char area = areaCode.charAt(0);
		System.out.println((char)area);
		Date curDate = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	
		
		System.out.println(Character.toString(shoppingCode)+Character.toString(area)+sdf.format(curDate)+count);
    	*/
    	LocalDateTime curTime = LocalDateTime.now();
    	
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	
    	String nowString = curTime.format(dateTimeFormatter);
    	
    	
    	System.out.println(nowString.substring(5, 7));
    	System.out.println(nowString.substring(8, 10));
    	System.out.println(nowString.substring(11, 13));
    	
    	/*String str = "04";
    	int num = Integer.parseInt(str);
    	System.out.println(num);
		*/
    	InvoiceDTO iDto = new InvoiceDTO();
    	InvoiceDAO iDao = new InvoiceDAO();
    	OrderDTO oDto = new OrderDTO();
    	OrderDAO oDao = new OrderDAO();
    	
    	/*iDto = iDao.selectOneCode("a119050710001");
    	System.out.println(iDto.toString());*/
        
    	 /*try {
	            // csv 데이터 파일
	            File csv = new File("C:\\Temp/Test.csv");
	            BufferedReader br = new BufferedReader(new FileReader(csv));
	            String line = "";
	            String[] customer = new String[5]; //이름,전화번호,주소를 저장할 공간
	            int count = 10001;//자동증가 숫자 (실제로 DB이 마지막 숫자부터 시작한다.)
	 
	            while ((line = br.readLine()) != null) {
	                // -1 옵션은 마지막 "," 이후 빈 공백도 읽기 위한 옵션
	                String[] token = line.split(",", -1);
//	                System.out.println(token.length); //길이는 값이 있던 없던 똑같다.
	                //이름이 비어있을 경우, 이전 고객 정보로 저장
	                if(!token[0].equals("")) {
	                	System.out.println("고객정보 있음");
	                	//같을 경우, 새로운 송장 정보를 생성
	                	customer[1] = token[0]; //이름
	                	customer[2] = token[1]; //전화번호
	                	customer[3] = token[2]; //주소
	                	customer[4] = iAreaCode(token[2]); //지역코드
	                	customer[0] = iCodeProc("amall",customer[4],count); //송장번호
	                	count ++;
	                	//송장번호 이름 전화번호 주소 지역코드를 DTO에 넣는다. 
	                	iDto = new InvoiceDTO(customer); 
	                	System.out.println(iDto.toString());
	                	//송장번호 이름 전화번호 주소 지역코드를 DB에 넣는다.
	                	//날짜와 배송 상태는 DAO에서 처리한다.
	                	iDao.insertInvoice(iDto);
	                	
	                }   
	                
	                //제품번호, 송장번호, 제품수량을 DTO에 넣는다.
	                try {
						oDto = new OrderDTO(token[3],customer[0],Integer.parseInt(token[4]));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
	                System.out.println(oDto.toString());
	                //제품번호, 송장번호, 제품수량을 DB에 넣는다.
	                //제품 인덱스는 DB에서 처리한다.
	                oDao.insertOrder(oDto);      
	            }
	            br.close();
	 
	        } 
	        catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } 
	        catch (IOException e) {
	            e.printStackTrace();
	        }*/
    }   
    public static String iCodeProc(String shopping, String areaCode, int count) {
		char shoppingCode = shopping.charAt(0);
		char area = areaCode.charAt(0);
		Date curDate = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return Character.toString(shoppingCode)+Character.toString(area)+sdf.format(curDate)+count;
		
	}
    
    public static String iAreaCode(String Address) {
    	String area = new String();
    	String strAd = Address.substring(0,2);
    	switch(strAd) {
    	case "서울":
    	case "경기":
    	case "인천":
    		area = "1area";
    		break;
    	case "대전":
    	case "충청":
    	case "강원":
    		area = "2area";
    		break;
    	case "광주":
    	case "전라":
    	case "제주":
    		area = "3area";
    		break;
    	case "대구":
    	case "울산":
    	case "부산":
    	case "경상":
    		area = "4area";
    		break;
    	default:
    		area = "error!";
    		break;
    	}
    	return area;
    }
    
 
}
