package supply;

import java.io.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import org.slf4j.*;

import function.*;
import invoice.*;

@WebServlet("/view/SupplyProc")
public class SupplyProc extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(SupplyProc.class);
	private static final long serialVersionUID = 1L;

	public SupplyProc() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 공통 설정
		LOG.trace("액션");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		LOG.trace(action);
		HttpSession session = request.getSession();
		RequestDispatcher rd;
		
		String pCode = new String();
		
		CustomerFunction cf = new CustomerFunction();
		String curMonth = cf.curMonth();

		SupplyDAO sDao = new SupplyDAO();
		List<SupplyDTO> sDtoLists = new ArrayList<SupplyDTO>();

		switch(action){
		case "complete":
			// complete를 하면 어제에 해당하는 모든 발주를 배송하고(pQuantity+100) 처리완료상태(sState=1)로 만듬
			LOG.trace("sProc.complete진입");
			pCode = request.getParameter("pCode");
			int pQuantity = sDao.selectQuantity(pCode);
			sDao.SupplyQuantity(pCode, pQuantity);
			LOG.trace("sProc.complete퇴장");
			break;
			
		case "supplyBeforeList":
			LOG.trace("sProc.supplyBeforeList진입");
			// 상태가 0,1 인 목록
			String userId = (String)session.getAttribute("userId");
			LOG.trace("sProc.userId : " + userId);
			sDtoLists = sDao.selectBeforeAll(userId);
			int supplyTotalPrice =0;
			for (SupplyDTO supply : sDtoLists) {
				supplyTotalPrice += supply.getsTotalPrice();
			}
			request.setAttribute("supplyTotalPrice",supplyTotalPrice);
			request.setAttribute("supplyList",sDtoLists);
			LOG.trace("sProc.sDtoLists : " + sDtoLists);
			rd = request.getRequestDispatcher("supply/sBeforeSupply.jsp");
			rd.forward(request, response);
			LOG.trace("sProc.supplyBeforeList끝");
			break;
			
		case "supplyAfterList":
			// 상태가 2인 목록
			LOG.trace("sProc.supplyAfterList진입");
			userId = (String)session.getAttribute("userId");
			LOG.trace("sProc.userId : " + userId);
			sDtoLists = sDao.selectAfterAll(userId);
			supplyTotalPrice =0;
			for (SupplyDTO supply : sDtoLists) {
				supplyTotalPrice += supply.getsTotalPrice();
			}
			request.setAttribute("supplyTotalPrice",supplyTotalPrice);
			request.setAttribute("supplyList",sDtoLists);
			rd = request.getRequestDispatcher("supply/sAfterSupply.jsp");
			rd.forward(request, response);
			LOG.trace("sProc.supplyAfterList끝");
			break;
		
		case "supplyAfterListSearch":
			// 상태가 2인 목록(월검색)
			LOG.trace("sProc.supplyAfterListSearch진입");
			userId = (String)session.getAttribute("userId");
			LOG.trace("sProc.userId : " + userId);
			String month = request.getParameter("month");
			LOG.trace("sProc.month : " + month);
			sDtoLists = sDao.searchByMonth(month, userId);
			supplyTotalPrice =0;
			for (SupplyDTO supply : sDtoLists) {
				supplyTotalPrice += supply.getsTotalPrice();
			}
			request.setAttribute("supplyTotalPrice",supplyTotalPrice);
			request.setAttribute("selectMonth", month);
			request.setAttribute("supplyList",sDtoLists);
			LOG.trace("sProc.sDtoLists : "+ sDtoLists);
			rd = request.getRequestDispatcher("supply/sAfterSupply.jsp");
			rd.forward(request, response);
			LOG.trace("sProc.supplyAfterListSearch끝");
			break;
		
		case "intoMain":
			LOG.trace("sProc.intoMain진입");
    		//1. 이번달 지불 액수 
			userId = (String)session.getAttribute("userId");
			LOG.trace("sProc.intoMain userID : " + userId);
			sDtoLists = sDao.searchByMonth(curMonth, userId); //이번달 처리가 완료된 발주리스트를 불러온다.
			LOG.trace("sProc.intoMain sDtoLists : " + sDtoLists);
    		int monthListCount = sDtoLists.size();
    		supplyTotalPrice = 0;
			for (SupplyDTO supply : sDtoLists) {
				supplyTotalPrice += supply.getsTotalPrice();
			}
			LOG.trace("sProc.intoMain 이번달 매출액 : "+ supplyTotalPrice);
			request.setAttribute("monthListCount",monthListCount);
			request.setAttribute("supplyTotalPrice",supplyTotalPrice);
    		LOG.trace("sProc.intoMain 이번달 매출액 계산 완료");
    		
//    		//2. 전년도 지불 액수 
//    		LOG.trace(cf.lastYear(cf.curYear()));
//			sDtoLists = sDao.supplySalesYear(userId.charAt(0),cf.lastYear(cf.curYear()));
//			for(InvoiceDTO invoice : sDtoLists) {
//    			sDtoLists = sDao.selectAll(supply.getsCode());
//    			for(OrderDTO order : sDtoLists) {
//    				lastYearTotalSales += order.getoQuantity()*order.getpPrice()*1.1;
//    			}
//    		}
//			LOG.trace("[쇼핑몰 Proc] 전년도 매출액 계산 완료");
			
			
//			//3.이번년도 월별 지불 액수
//			for(int m=1;m<13;m++) {
//				monthTotalPrice = 0;
//				iDtoLists = iDao.mallSalesCurYearMonth(userId.charAt(0),m);
//				LOG.trace("[쇼핑몰 Proc]"+m+"월 송장 수 : "+iDtoLists.size()+"");
//				for(InvoiceDTO invoice : iDtoLists) {
//					oDtoLists = oDao.selectAll(invoice.getiCode());
//	    			for(OrderDTO order : oDtoLists) {
//	    				monthTotalPrice += order.getoQuantity()*order.getpPrice()*1.1;
//	    			}
//	    			monthTotalPrice += 10000;
//	    		}
//				monthTotalSalesList.add(monthTotalPrice); //월별 총액을 리스트로 저장
//				thisYearTotalSales += monthTotalPrice; //올해 총액을 int로 저장
//			}
//    		
//    		request.setAttribute("monthTotalSales", monthTotalSales); //1. 이번달 지불액
//    		request.setAttribute("lastYearTotalSales", lastYearTotalSales); //2. 작년 총 지불액
//    		request.setAttribute("thisYearTotalSales", thisYearTotalSales);//3. 이번년 총 지불액 
//    		request.setAttribute("monthListCount", monthListCount); //4. 이번달 처리 송장 건수
//    		
//    		request.setAttribute("monthTotalSalesList", monthTotalSalesList); //5. 월별 총 지불액
    		rd = request.getRequestDispatcher("supply/supplierMain.jsp");
			rd.forward(request, response);
			LOG.trace("sProc.intoMain끝");
			break;

		default:
			break;
		}
		LOG.trace("끝");
		
	}

	


}
