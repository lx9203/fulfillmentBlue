package supply;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import org.slf4j.*;

import function.*;

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
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		String message = new String();
		RequestDispatcher rd;
		
		CustomerFunction cf = new CustomerFunction();
		String curMonth = cf.curMonth();
		String supplierCode = new String();
		String userId = new String();
		int monthTotalPrice = 0;
		int count=0;

		SupplyDAO sDao = new SupplyDAO();
		List<SupplyDTO> sDtoLists = new ArrayList<SupplyDTO>();
	   	List<Integer> lastTotalSalesList = new ArrayList<Integer>();
    	List<Integer> thisTotalSalesList = new ArrayList<Integer>();

		switch(action){
		case "complete":
			// complete를 하면 대기중(sState = 0)인 발주를 배송중(sState = 1)으로 만듬
			LOG.trace("sProc.complete진입");
			userId = (String)session.getAttribute("userId");
			supplierCode = CustomerFunction.SupplierCode(userId);
			sDtoLists = sDao.selectBeforeState(supplierCode);
			for(SupplyDTO supply : sDtoLists) {
				sDao.updateState(supply.getsCode());
				count++;
			}
			if(count == 0) {
				message = "당일 납품할 제품이 없습니다.";
				request.setAttribute("message", message);
				request.setAttribute("msgState", true);
				rd = request.getRequestDispatcher("SupplyProc?action=supplyBeforeList");
				rd.forward(request, response);
				break;
				
			}
			
			message = "총 "+count+" 건의 납품 승인 요청이 완료되었습니다.";
			request.setAttribute("message", message);
			request.setAttribute("msgState", true);
			rd = request.getRequestDispatcher("SupplyProc?action=supplyBeforeList");
			rd.forward(request, response);
			break;
			
		case "supplyBeforeList":
			LOG.trace("sProc.supplyBeforeList진입");
			// 상태가 0,1 인 목록
			userId = (String)session.getAttribute("userId");
			supplierCode = CustomerFunction.SupplierCode(userId);
			sDtoLists = sDao.selectBeforeAll(supplierCode);
			int supplyTotalPrice =0;
			for (SupplyDTO supply : sDtoLists) {
				supplyTotalPrice += supply.getsTotalPrice();
			}
			request.setAttribute("supplyTotalPrice",supplyTotalPrice);
			request.setAttribute("supplyList",sDtoLists);
			rd = request.getRequestDispatcher("supply/sBeforeSupply.jsp");
			rd.forward(request, response);
			break;
			
		case "supplyAfterList":
			// 상태가 2인 목록
			LOG.trace("sProc.supplyAfterList진입");
			userId = (String)session.getAttribute("userId");
			LOG.trace("sProc.intoMain userID : " + userId);
			supplierCode = CustomerFunction.SupplierCode(userId);
			LOG.trace(supplierCode);
			sDtoLists = sDao.selectAfterAll(supplierCode);
			supplyTotalPrice =0;
			for (SupplyDTO supply : sDtoLists) {
				supplyTotalPrice += supply.getsTotalPrice();
			}
			request.setAttribute("supplyTotalPrice",supplyTotalPrice);
			request.setAttribute("supplyList",sDtoLists);
			request.setAttribute("selectMonth",cf.curMonth());
			
			rd = request.getRequestDispatcher("supply/sAfterSupply.jsp");
			rd.forward(request, response);
			break;
		
		case "supplyAfterListSearch":
			// 상태가 2인 목록(월검색)
			LOG.trace("sProc.supplyAfterListSearch진입");
			if (request.getParameter("month").equals("")) {
				message = "날짜를 선택해 주세요.";
				request.setAttribute("message", message);
				request.setAttribute("msgState", true);
				rd = request.getRequestDispatcher("SupplyProc?action=supplyAfterList");
				rd.forward(request, response);
				break;
			}
			userId = (String)session.getAttribute("userId");
			LOG.trace("sProc.intoMain userID : " + userId);
			supplierCode = CustomerFunction.SupplierCode(userId);
			LOG.trace(supplierCode);
			String month = request.getParameter("month");
			LOG.trace("sProc.month : " + month);
			sDtoLists = sDao.searchByMonth(month, supplierCode);
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
			break;
		
		case "intoMain":
			LOG.trace("sProc.intoMain진입");
			//	변수
			userId = (String)session.getAttribute("userId");
			LOG.trace("sProc.intoMain변수 userID : " + userId);
			supplierCode = CustomerFunction.SupplierCode(userId);
			LOG.trace("SupplyProc.intoMain supplierCode : "+supplierCode);
			int curMonthTotalSales = 0;
			int lastYearTotalSales = 0;
			int curYearTotalSales = 0;
			
			//----------------------------------------------------
			
			//2-2. 작년이맘 매출 대비 올해 매출 현황
			int CurYearSalesRatio = curYearTotalSales-lastYearTotalSales;
			LOG.trace("SupplyProc.intoMain2-2 CurYearSalesRatio : " + CurYearSalesRatio);
			
			//3. 이번달 매출액 
			sDtoLists = sDao.searchByMonth(curMonth, supplierCode);
			LOG.trace("sProc.intoMain3 sDtoLists : " + sDtoLists);
			for (SupplyDTO supply : sDtoLists) {curMonthTotalSales += supply.getsTotalPrice();}
			LOG.trace("sProc.intoMain3 이번달 매출액 : "+ curMonthTotalSales);
			
			//4. 이번달 처리완료 건수
			int monthListCount = sDao.count(supplierCode);
			LOG.trace("sProc.intoMain3 monthListCount : "+monthListCount);
			
			//5.올해 월별 지불 액수
			for(int m=1;m<13;m++) {
				monthTotalPrice = 0;
				if(m>Integer.parseInt(cf.curMonth().substring(5))) {
					LOG.trace("현재 달 보다 숫자가 큼");
					break;
				}
				sDtoLists = sDao.SalesYearMonth(cf.curYear(),supplierCode,m);
				LOG.trace("sProc.intoMain 올해 월별 총액 : "+m+"월 송장 수 : "+sDtoLists.size()+"");
				for(SupplyDTO supply : sDtoLists) {
					sDtoLists = sDao.selectAll(supplierCode);
	    				monthTotalPrice += supply.getsTotalPrice();
	    		}
				thisTotalSalesList.add(monthTotalPrice); //월별 총액을 리스트로 저장
				curYearTotalSales +=monthTotalPrice;
			}
			
			//3.작년 월별 지불 액수
			for(int m=1;m<13;m++) {
				monthTotalPrice = 0;
				sDtoLists = sDao.SalesYearMonth(cf.lastYear(cf.curYear()),supplierCode,m);
				LOG.trace("sProc.intoMain 작년 월별 총액 : "+m+"월 송장 수 : "+sDtoLists.size()+"");
				for(SupplyDTO supply : sDtoLists) {
					sDtoLists = sDao.selectAll(supplierCode);
	    				monthTotalPrice += supply.getsTotalPrice();
	    		}
				lastTotalSalesList.add(monthTotalPrice); //월별 총액을 리스트로 저장
				lastYearTotalSales += monthTotalPrice; //올해 총액을 int로 저장
			}
			
			// 종합
    		request.setAttribute("curYearTotalSales", curYearTotalSales);//1. 이번년 매출액
    		request.setAttribute("lastYearTotalSales", lastYearTotalSales);	//2. 작년 매출액
    		request.setAttribute("curMonthTotalSales", curMonthTotalSales); //3. 이번달 매출액
    		request.setAttribute("monthListCount", monthListCount); //4. 이번달 처리완료 건수
       		request.setAttribute("thisTotalSalesList", thisTotalSalesList); //5. 올해 월별 총 지불액
    		request.setAttribute("lastTotalSalesList", lastTotalSalesList); //5. 작년 월별 총 지불액
    		
    		rd = request.getRequestDispatcher("supply/supplierMain.jsp");
			rd.forward(request, response);
			break;

		default:
			break;
		}
		
	}

	


}
