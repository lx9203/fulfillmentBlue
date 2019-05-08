package invoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import product.*;

@WebServlet("/view/InvoiceProc")
public class InvoiceProc extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceProc.class);
	private static final long serialVersionUID = 1L;
       
    public InvoiceProc() {
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//공통 설정 
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher rd;
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String message = new String();
		String url = new String();
		
		//DTO,DAO 관련 변수
		InvoiceDAO iDao = new InvoiceDAO();
		OrderDAO oDao = new OrderDAO();
		InvoiceDTO iDto = new InvoiceDTO();
		List<InvoiceDTO> iDtoLists = new ArrayList<InvoiceDTO>();
    	OrderDTO oDto = new OrderDTO();
    	List<OrderDTO> oDtoLists = new ArrayList<OrderDTO>();
    	ProductDAO pDao = new ProductDAO();
    	ProductDTO pDto = new ProductDTO();
    	
    	//session 변수
    	String userId = (String)session.getAttribute("userId");
    	LOG.trace(userId);
    	String userName = (String)session.getAttribute("userName");
    	int userType = (Integer)session.getAttribute("userType");
		
    	//일반 변수
    	String now = curTime(); //현재 날짜와 시간을 받는 변수
    	int curTime = 0; //현재 시간을 정수로 받는 변수
    	String iCode = new String(); //송장번호를 받는 변수
    	int count = 0; //카운트가 필요할 때 사용
    	String date = new String();
    	
		
		switch(action) {
		//---------------------------------쇼핑몰 관련 Action ----------------------------------------------------------
		case "mallInvoiceListMonth":
			//날짜에서 월에 해당하는 부분을 가져와 해당 월의 리스트를 DTO로 받는다.
			iDtoLists = iDao.mallSelectAllMonth(userId.charAt(0)); 
			
			request.setAttribute("invoiceLists", iDtoLists);
			rd = request.getRequestDispatcher("mInvoiceMonthList.jsp");
			rd.forward(request, response);
			break;
			
		case "mallInvoiceListDay":
			//날짜에서 일에 해당하는 부분을 가져와 해당 일의 리스트를 DTO로 받는다.
			iDtoLists = iDao.mallSelectAllDay(userId.charAt(0)); 
			
			request.setAttribute("invoiceLists", iDtoLists);
			rd = request.getRequestDispatcher("mInvoiceDayList.jsp");
			rd.forward(request, response);
			break;
			
		case "mallSearchList":
			date = request.getParameter("date");
			LOG.trace(date);
			iDtoLists = iDao.mallSearchAllDay(userId.charAt(0), date);
			request.setAttribute("selectDate", date);
			request.setAttribute("invoiceLists", iDtoLists);
			rd = request.getRequestDispatcher("mInvoiceDayList.jsp");
			rd.forward(request, response);
			break;
			
		case "detailList": //송장 번호에 해당하는 제품 목록을 가져온다.
			//하나의 송장 정보와 해당 송장정보의 모든 제품 목록을 가져온다.
			iCode = request.getParameter("iCode");
			LOG.trace(iCode);
			
			iDto = iDao.selectOneCode(iCode);
			oDtoLists = oDao.selectAll(iCode);
			
			int totalProductPrice = 0;
			
			for(OrderDTO order : oDtoLists) totalProductPrice += order.getoTotalPrice();
			
			request.setAttribute("invoiceTotalPrice", totalProductPrice);
			request.setAttribute("invoice", iDto);
			request.setAttribute("orderLists", oDtoLists);
			rd = request.getRequestDispatcher("mDetailList.jsp");
			rd.forward(request, response);
			
			break;
			
		case "readCSV": //송장CSV파일 받기 case	
	        try {
	            // csv 데이터 파일
	            File csv = new File("C:\\Temp/Test1.csv");
	            BufferedReader br = new BufferedReader(new FileReader(csv));
	            String line = "";
	            String[] customer = new String[5]; //이름,전화번호,주소를 저장할 공간
	            count = 10001;//자동증가 숫자 (실제로 DB이 마지막 숫자부터 시작한다.)
	 
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
	                	//customer[0] = iCodeProc(userId,customer[4],count); //송장번호
	                	//송장번호 이름 전화번호 주소 지역코드를 DTO에 넣는다. 
	                	iDto = new InvoiceDTO(customer); 
	                	LOG.trace(iDto.toString());
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
	                LOG.trace(oDto.toString());
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
	        }
			break;
			
	//------------------------------------------운송사 관련 Action------------------------------------
		case "transInvoiceListMonth":
			//날짜에서 월에 해당하는 부분을 가져와 해당 월의 리스트를 DTO로 받는다.
			iDtoLists = iDao.transSelectAllMonth(userId); 
			
			request.setAttribute("invoiceLists", iDtoLists);
			rd = request.getRequestDispatcher("tInvoiceMonthList.jsp");
			rd.forward(request, response);
			break;
		case "transInvoiceListDay": 
			//날짜에서 일에 해당하는 부분을 가져와 해당 일의 리스트를 DTO로 받는다.
			iDtoLists = iDao.transSelectAllDay(userId);
			
			request.setAttribute("invoiceLists", iDtoLists);
			rd = request.getRequestDispatcher("tInvoiceDayList.jsp");
			rd.forward(request, response);
			
			break;
			
		case "transSearchList":
			date = request.getParameter("date");
			LOG.trace(date);
			iDtoLists = iDao.mallSearchAllDay(userId.charAt(0), date);
			request.setAttribute("selectDate", date);
			request.setAttribute("invoiceLists", iDtoLists);
			rd = request.getRequestDispatcher("mInvoiceDayList.jsp");
			rd.forward(request, response);
			break;
		case "forwarding": //물건 출고 case
			boolean available = true;
			//1.현재 시간을 확인 한다.
			try {
				curTime = Integer.parseInt(now.substring(11,13));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//2. 9시 이전, 9시에서 18시 사이, 18시 이후 3가지 경우로 나눈다.
			if(curTime>=9 && curTime<=18) { //(1) 9시에서 18시 사이에 출고를 할 경우 그 시간때의 송장만 출고를 한다.
				iDtoLists = iDao.selectAllWorkTime();
			} else if(curTime<9) { //(2) 9시 이전에 출고를 할 경우 전날 18시부터 당일 9시까지의 송장만 출고를 한다.
				iDtoLists = iDao.selectAllBeforeWork();
			} else if(curTime>18) { //(3)18시 이후에 출고를 할 경우 당일 18시부터 당일 24시까지의 송장만 출고를 한다.
				iDtoLists = iDao.selectAllAfterWork();
			}
			
			//3. 각 시간에 맞는 송장만 가져와 출고 작업을 시작한다. 
			for(InvoiceDTO invoice : iDtoLists) {
				oDtoLists = oDao.selectQuantity(invoice.getiCode()); //해당 송장의 제품코드, 출고해야할 수량과 창고의 재고량을 가져온다.
				for(OrderDTO order : oDtoLists) { // 1. 출고 가능 여부를 먼저 검사한다.
					//재고의 개수가 적으면 해당 송장의 모든 물건을 출고 하지 않는다. 동시에 공급처로 물품을 요청한다.
					if(order.getoQuantity() >order.getpQuantity()) { 
	//물풀 요청이 들어가야하는 자리!!
						available = false;
						break;
					} 
				}
				if(available) { //2. 송장에 해당하는 물건 전부가 출고 가능일 때, 출고 
					for(OrderDTO order : oDtoLists) {
						int afterRelease = order.getpQuantity()-order.getoQuantity();
						pDto = new ProductDTO(order.getoProductCode(), afterRelease);
						pDao.updateQuantity(pDto); //출고
					}
					iDao.updateState(invoice.getiCode());//해당 송장을 출고 상태로 변경한다.
					count ++;
				}
			}
			message = "총 "+count+"건의 출고 처리가 완료되었습니다.";
			url = "register.html";
			request.setAttribute("message", message);
			request.setAttribute("url", url);
			rd = request.getRequestDispatcher("alertMsg.jsp");
			rd.forward(request, response);
			break;
			
		default:
			break;
		}
	}
	
	
	//--------------------------------- 사용자 생성 함수 ----------------------------------------------------
	//지역코드 변환 함수
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

	//송장번호 생성 함수
	public static String iCodeProc(String shopping, String areaCode) {
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
	//현재 시간 구하는 함수
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
}
