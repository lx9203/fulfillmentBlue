package invoice;

import java.io.IOException;
import java.util.ArrayList;
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

import function.CustomerFunction;
import supply.*;

@WebServlet("/view/TransProc")
public class TransProc extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(TransProc.class);
	private static final long serialVersionUID = 1L;
       
    public TransProc() {
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//공통 설정
			request.setCharacterEncoding("UTF-8");
			RequestDispatcher rd;
			HttpSession session = request.getSession();
			String action = request.getParameter("action");
			String message = new String();
			CustomerFunction cf = new CustomerFunction();
			
			//DTO,DAO 관련 변수
			InvoiceDAO iDao = new InvoiceDAO();
			OrderDAO oDao = new OrderDAO();
			InvoiceDTO iDto = new InvoiceDTO();
			List<InvoiceDTO> iDtoLists = new ArrayList<InvoiceDTO>();
	    	List<OrderDTO> oDtoLists = new ArrayList<OrderDTO>();
	    	SupplyDAO sDao = new SupplyDAO();
	    	
	    	//session 변수
	    	String userId = (String)session.getAttribute("userId");
	    	LOG.trace("[쇼핑몰 Proc] 사용자 ID : " + userId);
			
	    	//일반 변수
	    	String iCode = new String(); //송장번호를 받는 변수
	    	String date = new String();
	    	String month = new String();
	    	int curHour = 0;
	    	int monthTotalSales = 0; //1. 이번달 매출액을 저장
	    	int lastYearTotalSales = 0; //2. 작년도 매출액을 저장
	    	int thisYearTotalSales = 0; //3. 이번년도 매출액을 저장
	    	int monthListCount = 0; //4. 이번달 처리 송장 건수를 저장
	    	int monthTotalPrice = 0; //월별 매출액을 저장
	    	
	    	List<Integer> lastTotalSalesList = new ArrayList<Integer>();
	    	List<Integer> thisTotalSalesList = new ArrayList<Integer>();
	    	
	    	switch(action) {
	    	case "intoMain":
	    		LOG.trace("[운송사 Proc] 메인페이지 입장");
	    		//1. 이번달 지불 액수 
	    		iDtoLists = iDao.transSalesMonth(userId,cf.curMonth()); //이번달 처리가 완료된 송장 리스트를 불러온다.
	    		monthListCount = iDtoLists.size();
	    		monthTotalSales = monthListCount * 10000;
	    	
	    		LOG.trace("[운송사 Proc] 이번달 매출액 계산 완료");
				
				
				//3.이번년도 월별 지불 액수
				for(int m=1;m<13;m++) {
					monthTotalPrice = 0;
					iDtoLists = iDao.transSalesYearMonth(cf.curYear(),userId,m);
					LOG.trace("[운송사 Proc]"+m+"월 송장 수 : "+iDtoLists.size()+"");
		    		monthTotalPrice = iDtoLists.size()*10000;
		    		thisTotalSalesList.add(monthTotalPrice); //월별 총액을 리스트로 저장
					thisYearTotalSales += monthTotalPrice; //올해 총액을 int로 저장
				}
				//4. 작년도 월별 지불 액수
				for(int m=1;m<13;m++) {
					monthTotalPrice = 0;
					iDtoLists = iDao.transSalesYearMonth(cf.lastYear(cf.curYear()),userId,m);
					LOG.trace("[운송사 Proc]"+m+"월 송장 수 : "+iDtoLists.size()+"");
		    		monthTotalPrice = iDtoLists.size()*10000;
		    		lastTotalSalesList.add(monthTotalPrice); //월별 총액을 리스트로 저장
		    		lastYearTotalSales += monthTotalPrice; //올해 총액을 int로 저장
				}
	    		
	    		request.setAttribute("monthTotalSales", monthTotalSales); //1. 이번달 지불액
	    		request.setAttribute("lastYearTotalSales", lastYearTotalSales); //2. 작년 총 지불액
	    		request.setAttribute("thisYearTotalSales", thisYearTotalSales);//3. 이번년 총 지불액 
	    		request.setAttribute("monthListCount", monthListCount); //4. 이번달 처리 송장 건수
	    		
	    		request.setAttribute("thisTotalSalesList", thisTotalSalesList); //5. 월별 총 지불액
	    		request.setAttribute("lastTotalSalesList", lastTotalSalesList); //5. 월별 총 지불액
	    		rd = request.getRequestDispatcher("transfer/transMain.jsp");
				rd.forward(request, response);
	    		break;
	    		
	    	//1. [일별 배송목록] 날짜에서 일에 해당하는 부분을 가져와 해당 하루 리스트를 DTO로 받는다.
	    	case "transInvoiceListDay" :
	    		LOG.trace("[운송사 Proc] 일별 배송 목록");
				iDtoLists = iDao.transSearchAllDay(userId,cf.curDate()); //쇼핑몰의 코드를 통해 오늘 날짜의 송장 목록을 가져온다.
				request.setAttribute("invoiceLists", iDtoLists);
				rd = request.getRequestDispatcher("transfer/invoiceDayList.jsp"); //쇼핑몰 일별 리스트 화면으로 송장 리스트를 던져준다.
				rd.forward(request, response);
				break;
				
			//2. [월별 배송목록] 날짜에서 일에 해당하는 부분을 가져와 해당 하루 리스트를 DTO로 받는다.
	    	case "transInvoiceListMonth" :
	    		LOG.trace("[운송사 Proc] 월별 배송 목록");
				iDtoLists = iDao.transSearchAllMonth(userId,cf.curMonth()); //쇼핑몰의 코드를 통해 오늘 날짜의 송장 목록을 가져온다.
				request.setAttribute("invoiceLists", iDtoLists);
				rd = request.getRequestDispatcher("transfer/invoiceMonthList.jsp"); //쇼핑몰 일별 리스트 화면으로 송장 리스트를 던져준다.
				rd.forward(request, response);
				break;
				
			//3. [기능 : 날짜 검색] 날짜를 선택해서 해당항는 부분의 하루 리스트를 DTO로 받는다.
			case "transSearchDayList":
				date = request.getParameter("date"); //페이지로부터 선택한 날짜를 가져온다.
				LOG.trace("[운송사 Proc] 선택한 날짜 : "+date);
				iDtoLists = iDao.transSearchAllDay(userId, date); //쇼핑몰의 코드와 날짜를 통해 이번 월의 송장목록을 가져온다.
				request.setAttribute("selectDate", date); //날짜를 표시하기위해 다시 던져준다.
				request.setAttribute("invoiceLists", iDtoLists);
				rd = request.getRequestDispatcher("transfer/invoiceDayList.jsp"); //쇼핑몰의 하루 리스트 화면으로 송장 리스트를 던져준다.
				rd.forward(request, response);
				break;
				
			case "transSearchMonthList":
				LOG.trace("[운송사 Proc] 월별 배송 목록 검색");
				month = request.getParameter("month"); //페이지로부터 선택한 날짜를 가져온다.
				LOG.trace("[운송사 Proc] 선택한 달 : "+ month);
				iDtoLists = iDao.transSearchAllMonth(userId, month); //쇼핑몰의 코드와 날짜를 통해 이번 월의 송장목록을 가져온다.
				request.setAttribute("selectDate", month); //날짜를 표시하기위해 다시 던져준다.
				request.setAttribute("invoiceLists", iDtoLists);
				rd = request.getRequestDispatcher("transfer/invoiceMonthList.jsp"); //쇼핑몰의 하루 리스트 화면으로 송장 리스트를 던져준다.
				rd.forward(request, response);
				break;
				
			//4. [송장 상세 보기] 송장 번호에 해당하는 제품 목록을 가져온다.
			case "detailList": 
				//하나의 송장 정보와 해당 송장정보의 모든 제품 목록을 가져온다.
				iCode = request.getParameter("iCode");
				LOG.trace("[운송사 Proc] 송장 번호 : "+iCode);
				
				iDto = iDao.selectOneCode(iCode);
				oDtoLists = oDao.selectAll(iCode);
				
				int totalProductPrice = 0;
				
				for(OrderDTO order : oDtoLists) totalProductPrice += order.getoTotalPrice();
				
				request.setAttribute("invoiceTotalPrice", totalProductPrice);
				request.setAttribute("invoice", iDto);
				request.setAttribute("orderLists", oDtoLists);
				rd = request.getRequestDispatcher("transfer/invoiceDetailList.jsp");
				rd.forward(request, response);
				
				break;
			case "forwarding": //물건 출고 case
				LOG.trace("출고 시작");
				boolean available = true;
				int count = 0;
				String Area = (String)session.getAttribute("userId");
				//1.현재 시간을 확인 한다.
				try {
					String curTime = cf.curTime();
					curHour = Integer.parseInt(curTime.substring(11,13));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				
				//2. 9시 이전, 9시에서 18시 사이, 18시 이후 3가지 경우로 나눈다.
				if(curHour>=12 && curHour<=18) { //(1) 12시에서 18시 사이에 출고를 할 경우, 당일 송장을 출고한다.
					LOG.trace("12시 이후 출고");
					iDtoLists = iDao.selectAllWorkTime(Area);
				} else if(curHour<12) { //(2) 9시 이전에 출고를 할 경우 전날부터 오늘 오전 9시 까지의 송장을 출고한다.
					LOG.trace("12시 이전 출고");
					iDtoLists = iDao.selectAllBeforeWork(Area);
				} else if(curHour>18) { //(3)18시 이후에 출고 불가 처리 
					//출고 불가
					break; 
				}
				
				//3. 각 시간에 맞는 송장만 가져와 출고 작업을 시작한다. 
				for(InvoiceDTO invoice : iDtoLists) {
					LOG.trace("출고 시작");
					available = true;
					oDtoLists = oDao.selectQuantity(invoice.getiCode()); //해당 송장의 제품코드, 출고해야할 수량과 창고의 재고량을 가져온다.
					for(OrderDTO order : oDtoLists) { // 1. 출고 가능 여부를 먼저 검사한다.
						//출고 가능한 수량 = 재고 수량 + 재고 신청 수량 - 출고 준비중인 수량
						LOG.trace("현재 출고 검사 물품 : " + order.getoProductCode());
						SupplyDTO sDto = sDao.productQuantity(order.getoProductCode());
						int availQuantity = order.getpQuantity() + sDto.getsQuantity() - cf.productQuantity(order.getoProductCode());
						LOG.trace("출고 가능 수량 : " +  availQuantity);
						//모든 송장 물품에 대해서 출고 가능한 수량이 충분하면 출고 신청을 한다. 
						//재고가 모자르거나 10개 이하인 경우 해당 제품 발주 신청
						if(order.getoQuantity() > availQuantity || availQuantity < 10 ) {
							LOG.trace("해당 물품 발주 신청 : " + order.getoProductCode());
							sDao.insertSupply(order.getoProductCode(),order.getoQuantity()+20); //해당 제품 20수량 발주
							available = false;
						}
					}
					LOG.trace("송장 처리 1건 추가");
					iDao.requestState(invoice.getiCode());//해당 송장을 출고 처리상태로 변경(0->1)한다.
					count ++;
				}
				message = "총"+count+" 건의 출고 처리가 완료되었습니다.";
				request.setAttribute("message", message);
				request.setAttribute("msgState", true);
				rd = request.getRequestDispatcher("TransProc?action=transInvoiceListDay");
				rd.forward(request, response);
				break;
	    	}
	}

}
