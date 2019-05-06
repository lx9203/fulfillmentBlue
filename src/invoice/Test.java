package invoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
    	InvoiceDTO iDto = new InvoiceDTO();
    	OrderDTO oDto = new OrderDTO();
    	
        
        try {
            // csv 데이터 파일
            File csv = new File("C:\\Temp/Test1.csv");
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";
            int row =0 ,i;
            String[] customer = new String[5]; //이름,전화번호,주소를 저장할 공간
 
            while ((line = br.readLine()) != null) {
            	int count = 10001;//자동증가 숫자 (실제로 DB이 마지막 숫자부터 시작한다.)
                // -1 옵션은 마지막 "," 이후 빈 공백도 읽기 위한 옵션
                String[] token = line.split(",", -1);
//                System.out.println(token.length); //길이는 값이 있던 없던 똑같다.
                //이름이 비어있을 경우, 이전 고객 정보로 저장
                if(!token[0].equals("")) {
                	System.out.println("고객정보 있음");
                	//같을 경우, 새로운 송장 정보를 생성
                	LocalDate currentDate = LocalDate.now();
                	customer[1] = token[0]; //이름
                	customer[2] = token[1]; //전화번호
                	customer[3] = token[2]; //주소
                	customer[4] = iAreaCode(token[2]); //지역코드
                	customer[0] = "a"+1+currentDate+count; //송장번호
                	iDto = new InvoiceDTO(customer);
                	System.out.println(iDto.toString());
                }   
                
                oDto = new OrderDTO(token[3],customer[0],Integer.parseInt(token[4]));
                
                System.out.println(oDto.toString());
                

            }
            br.close();
 
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
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
    		area = "error! 지역을 찾을수 없습니다.";
    		break;
    	}
    	return area;
    }
    
 
}
