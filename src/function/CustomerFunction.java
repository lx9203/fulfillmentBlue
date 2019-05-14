package function;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import invoice.*;
import supply.*;

public class CustomerFunction {
	private static final Logger LOG = LoggerFactory.getLogger(CustomerFunction.class);
	
	// (invoice) 지역코드 변환 함수 - 송장 등록 관련
	public String iAreaCode(String Address) {
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
    	case "세종":
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

	//(invoice) 송장번호 생성 함수 - 송장 등록 관련
	public String iCodeProc(String shopping, String areaCode) {
    	InvoiceDAO iDao = new InvoiceDAO();
		char shoppingCode = shopping.charAt(0);
		char area = areaCode.charAt(0);
		Date curDate = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
    	int increment =0;
    	Optional<String> op = Optional.ofNullable(iDao.selectOneDayLast(sdf.format(curDate)).getiCode());
    	LOG.trace(op.isPresent()+ "");
		if(!op.isPresent()) {
			increment =10001; //송장번호를 생성시 당일 첫 송장번호는 10001부터 시작
			LOG.trace("처음 생성한 송장번호");
		} else {
			String iCode = iDao.selectOneDayLast(sdf.format(curDate)).getiCode();
			increment = Integer.parseInt(iCode.substring(8))+1;
			System.out.println(increment);
		}	
    	
		return Character.toString(shoppingCode)+Character.toString(area)+sdf.format(curDate)+increment;
	}
	
	// (supply) 발주코드(sCode) 생성 함수 { 공급사구분+날짜+자동(3) }
		public static String sCodeCreate(String pCode) {
			// 변수
			LOG.trace("CF.sCodeCreate진입");
			SupplyDAO sDao = new SupplyDAO();
			int increment = 0;
			String supplier = "";
			// 날짜
			Date curDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String date = sdf.format(curDate);
			
			// 공급자 구분
			char supplierCode = pCode.charAt(0);
			supplier = Character.toString(supplierCode);
			LOG.trace("CF.supplier : "+supplier);
			// 자동(3)
			int OneOrZero = sDao.searchState(supplier); // sState 검색후 state가 0인것이
			LOG.trace("CF.OneOrZero : "+OneOrZero);
			if (OneOrZero != 0) { // 1, 2 이면
				increment = 101; // 101번부터 시작
				LOG.trace("CF.OneOrZero가 0이 아닐 경우의 increment : "+increment);
			} else { // state가 0인것이 있으면
				increment = Integer.parseInt(sDao.searchsCodeBySupplier(supplier).substring(7))+ 1; 
				// count = 이미 있는 sCode의 마지막번호 +1로 시작
				LOG.trace("CF.OneOrZero가 0일 경우의 increment : "+increment);
			}
			String sCode = supplier + date + increment;
			LOG.trace(sCode);
			LOG.trace("CF.sCodeCreate퇴장");
			return sCode;
		}
		
// ------------------------- 제품 출고 관련 함수 ------------------------------------------
	public int productQuantityState1(String pCode) {//출고 대기중인 상품의 개수를 출력하는 메소드
		int totalQuantity = 0;
		List<InvoiceDTO> iDtoList = new ArrayList<InvoiceDTO>();
		List<OrderDTO> oDtoList = new ArrayList<OrderDTO>();
		InvoiceDAO iDao = new InvoiceDAO();
		OrderDAO oDao = new OrderDAO();
		
		
		iDtoList = iDao.transState1(); //출고 준비중인 모든 송장 리스트를 가져온다.
		for(InvoiceDTO invoice : iDtoList) { 
			oDtoList = oDao.selectQuantity(invoice.getiCode());
			for(OrderDTO order : oDtoList) {
				if(order.getoProductCode().equals(pCode)) { //해당 제품의 개수를 모두 합한다.
					totalQuantity += order.getoQuantity();
				}
			}
		}
		
		return totalQuantity; //출고 준비중인 송장의 해당 상품 개수 총합을 return 한다. 
	}
	
	public int supplyQuantity(String pCode) {
		int totalQuantity = 0;
		SupplyDTO sDto = new SupplyDTO();
		SupplyDAO sDao = new SupplyDAO();
		return totalQuantity;
	}
		
//----------------------------------- 시간 관련 함수 ----------------------------------------------------
//------------------------------- 01. 현재 시간 관련 함수 ------------------------------------------------
	
	//현재 시간을 구하는 함수
	public String curTime() {
		LocalDateTime curTime = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	
    	return curTime.format(dateTimeFormatter);
	}
	
	//현재 날짜를 구하는 함수
	public String curDate() {
		LocalDateTime curTime = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");	
    	return curTime.format(dateTimeFormatter);
	}
	//현재 달을 구하는 함수
	public String curMonth() {
		LocalDateTime curTime = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");	
    	return curTime.format(dateTimeFormatter);
	}
	//현재 년도를 구하는 함수
	public String curYear() {
		LocalDateTime curTime = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy");	
    	return curTime.format(dateTimeFormatter);
	}
	
//-------------------------------- 02. 날짜 계산 함수 ----------------------------------------------------
	// 전날 날짜 구하기
	public String yesterday(String date) {
		LocalDate lastDay = LocalDate.parse(date);
		lastDay = lastDay.minusDays(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");	
    	return lastDay.format(dateTimeFormatter);
	}
	
	// 다음날 날짜 구하기
	public String tomorrow(String date) {
		LocalDate tomorrow = LocalDate.parse(date);
		tomorrow = tomorrow.plusDays(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");	
    	return tomorrow.format(dateTimeFormatter);
	}
	
	// 전달 날짜 구하기
	public String lastMonth(String date) {
		LocalDate lastMonth = LocalDate.parse(date+"-01");
		lastMonth = lastMonth.minusMonths(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");	
    	return lastMonth.format(dateTimeFormatter);
	}
	// 다음달 날짜 구하기
	public String nextMonth(String date) {
		LocalDate nextMonth = LocalDate.parse(date+"-01");
		nextMonth = nextMonth.plusMonths(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");	
    	return nextMonth.format(dateTimeFormatter);
	}
		
	// 전년 날짜 구하기
	public String lastYear(String date) {
		LocalDate lastYear = LocalDate.parse(date+"-01-01");
		lastYear = lastYear.minusYears(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy");	
    	return lastYear.format(dateTimeFormatter);
	}
	// 다음년 날짜 구하기
	public String nextYear(String date) {
		LocalDate nextYear = LocalDate.parse(date+"-01-01");
		nextYear = nextYear.plusYears(1);
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy");	
    	return nextYear.format(dateTimeFormatter);
	}

}
