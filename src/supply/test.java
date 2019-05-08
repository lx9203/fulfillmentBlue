package supply;

import java.time.*;
import java.time.format.*;
import java.util.*;

import javax.servlet.*;

import org.slf4j.*;

import invoice.*;

public class test {
	private static final Logger LOG = LoggerFactory.getLogger(OrderDAO.class);
	public static void main(String[] args) {
		SupplyDTO sDto = new SupplyDTO();
		SupplyDAO sDao = new SupplyDAO();
		
		/*			
		private String sCode;	// 기록, 읽어오기
		private String sProductCode;	// 기록
		private String sProductName;	// 읽어오기
		private int sProductPrice;	// 읽어오기
		private String sDate;	// 기록, 읽어오기
		private int sQuantity;	// 기록, 읽어오기
		private int sState;	// 기록
		private int sTotalPrice; // 기록
		 제품코드 ,번호,   날짜   ,양 ,상태
		A190505001,A105,2019-05-05,100,0
		String sCode = "A190505001";
		String sProductCode = "A105";
		int sQuantity = 0;
		int sState = 0;
		 */          
		
//		List<SupplyDTO> request = sDao.selectAll();
//		for (int i=0; i < request.size(); i++) {
//			System.out.println(request.get(i));
//		}
		
		String sDate = curTime();
		List<SupplyDTO> request = sDao.searchByDay(sDate);
		for (int i=0; i < request.size(); i++) {
			System.out.println(request.get(i));
		}
		

	}
	
	public static String curTime() {
		LocalDateTime curTime = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMdd");	
    	return curTime.format(dateTimeFormatter);
	}
}
