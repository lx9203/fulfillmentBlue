package supply;

import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class test {
	public static void main(String[] args) {
		SupplyDTO sDto = new SupplyDTO();
		SupplyDAO sDao = new SupplyDAO();
		String sCode = new String();
		String sProductCode = new String();
		String sDate = new String();
		
		sProductCode = "A102";
		sCode = sCodeCreate(sProductCode);	// pCode로 발주코드 만들기
		sDate = curTime();
		int sQuantity = 100;		// 발주량
		int sState = 0;				// 상태를 0으로 부여
		
		sDto.setsCode(sCode);
		sDto.setsProductCode(sProductCode);
		sDto.setsDate(sDate);
		sDto.setsQuantity(sQuantity);
		sDto.setsState(sState);
		
		sDao.insertSupply(sDto);
		
//		SupplyDAO sDao = new SupplyDAO();
//		int pQuantity = sDao.selectQuantity(pCode);
//		sDao.SupplyQuantity(pCode, pQuantity);
		
//		List<SupplyDTO> a = sDao.searchByMonth();
//		System.out.println(a);
		
//		List<SupplyDTO> a = sDao.selectAll();
//		for (int i = 0; i < 1; i++) {
//			System.out.println(a);
//		}
		
//		String str = sCodeCreate("A101");
//		System.out.println(str);    
		
//		List<SupplyDTO> request = sDao.selectAll();
//		for (int i=0; i < request.size(); i++) {
//			System.out.println(request.get(i));
//		}
		
//		String sDay = curTime();
//		List<SupplyDTO> request = sDao.searchsCodeByDay(sDay);
//		for (int i=1; i < request.size(); i++) {
//			System.out.println(request.get(i));
//		}
		
//		Date curDate = new Date();
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
//    	String date = sdf.format(curDate);
//		List<SupplyDTO> YesOrNo = sDao.searchByDay(date);
//		for (int i=0; i < YesOrNo.size(); i++) {
//			System.out.println(YesOrNo.get(i));
//		}

	}
	
	
	//	발주코드 생성
	public static String sCodeCreate(String pCode) {
		// 변수
		SupplyDAO sDao = new SupplyDAO();
		int count = 0;
		String supplier = "";
		// 날짜
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String date = sdf.format(curDate);
		System.out.println(date);
		// 공급자 구분
		char supplierCode = pCode.charAt(0);
		supplier = Character.toString(supplierCode);
		System.out.println(supplier);
		// 자동(3)
		int OneOrZero = sDao.searchStateByDay();	// 날짜로 sState 검색후 state가 0인것이 
		System.out.println("상태 = " +OneOrZero);
		if(OneOrZero != 0) {	//	없으면
			count =101;	//101번부터 시작
		} else {	// state가 0인것이 있으면
			count = Integer.parseInt(sDao.searchsCodeBySupplier(supplier).substring(7))+1;	// count = 이미 있는 sCode의 마지막번호 +1로 시작
		}
    	String sCode = supplier + date + count;
    	System.out.println(sCode);
		return sCode;
	}
	
	public static String curMonth() {
		LocalDateTime curTime = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy-MM");	
    	return curTime.format(dateTimeFormatter);
	}
	
	public static String curTime() {
		LocalDateTime curTime = LocalDateTime.now();

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

		return curTime.format(dateTimeFormatter);
	}
}
