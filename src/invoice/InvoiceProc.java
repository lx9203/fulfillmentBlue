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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    	
    	//session 변수
    	/*String userId = (String)session.getAttribute("userId");
    	LOG.trace(userId);
    	String userName = (String)session.getAttribute("userName");
    	int userType = (Integer)session.getAttribute("userType");*/
		
    	//일반 변수
    	String now = curTime();
    	int curTime = 0;
    	int curDate = 0;
    	String iCode = new String();
    	
		
		switch(action) {
		//---------------------------------쇼핑몰 관련 Action ----------------------------------------------------------
		case "mallInvoiceListMonth":
			//날짜에서 월에 해당하는 부분을 가져와 해당 월의 리스트를 DTO로 받는다.
			iDtoLists = iDao.selectAllMonth(); 
			
			request.setAttribute("invoiceLists", iDtoLists);
			rd = request.getRequestDispatcher("mall/");
			rd.forward(request, response);
			break;
		case "mallInvoiceListDay":
			//날짜에서 일에 해당하는 부분을 가져와 해당 일의 리스트를 DTO로 받는다.
			iDtoLists = iDao.selectAllDay();
			
			request.setAttribute("invoiceLists", iDtoLists);
			rd = request.getRequestDispatcher("mall/sInvoiceList.jsp");
			rd.forward(request, response);
			
			break;
		case "DetailList": //송장 번호에 해당하는 제품 목록을 가져온다.
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
			rd = request.getRequestDispatcher("mall/sDetailTest.jsp");
			rd.forward(request, response);
			
			break;
		case "readCSV":
			
	        try {
	            // csv 데이터 파일
	            File csv = new File("C:\\Temp/Test1.csv");
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
			iDtoLists = iDao.selectAllMonth(); 
			
			request.setAttribute("invoiceLists", iDtoLists);
			rd = request.getRequestDispatcher("mall/");
			rd.forward(request, response);
			break;
		case "transInvoiceListDay":
			//날짜에서 월에 해당하는 부분을 가져와 해당 일의 리스트를 DTO로 받는다.
			iDtoLists = iDao.selectAllMonth();
			
			request.setAttribute("invoiceLists", iDtoLists);
			rd = request.getRequestDispatcher("mall/");
			rd.forward(request, response);
			
			break;
		case "InvoiceProcess":
			//1.현재 날짜와 시간을 확인 한다.
			try {
				curDate = Integer.parseInt(now.substring(11,13));
				curTime = Integer.parseInt(now.substring(11,13));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(curTime<9 && curTime>18) {
				
			}
			
			break;
			
		default:
			break;
		}
	}
	
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
	public static String iCodeProc(String shopping, String areaCode, int count) {
		char shoppingCode = shopping.charAt(0);
		char area = areaCode.charAt(0);
		Date curDate = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return Character.toString(shoppingCode)+Character.toString(area)+sdf.format(curDate)+count;
		
	}
	//현재 시간 구하는 함수
	public String curTime() {
		LocalDateTime curTime = LocalDateTime.now();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	
    	return curTime.format(dateTimeFormatter);
	}
}
