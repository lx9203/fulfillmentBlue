package supply;

import java.io.*;
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
		LOG.trace("SupplyProc.action : "+action);
		HttpSession session = request.getSession();
		RequestDispatcher rd;
		
		CustomerFunction cf = new CustomerFunction();
		String curMonth = cf.curMonth();
		String supplierCode = new String();
		String userId = new String();
		int thisYearTotalSales = 0;
		int monthTotalPrice = 0;

		SupplyDAO sDao = new SupplyDAO();
		List<SupplyDTO> sDtoLists = new ArrayList<SupplyDTO>();
	   	List<Integer> lastTotalSalesList = new ArrayList<Integer>();
    	List<Integer> thisTotalSalesList = new ArrayList<Integer>();

		switch(action){
		case "complete":
			// complete를 하면 대기중(sState = 0)인 발주를 배송중(sState = 1)으로 만듬
			LOG.trace("sProc.complete진입");
			userId = (String)session.getAttribute("userId");
			LOG.trace("sProc.intoMain userID : " + userId);
			supplierCode = CustomerFunction.SupplierCode(userId);
			LOG.trace(supplierCode);
			String sCode = sDao.searchsCode(supplierCode);
			sDao.updateState(sCode);
			LOG.trace("sProc.complete 완료. sProc.complete퇴장");
			break;
			
		case "supplyBeforeList":
			LOG.trace("sProc.supplyBeforeList진입");
			// 상태가 0,1 인 목록
			userId = (String)session.getAttribute("userId");
			LOG.trace("sProc.intoMain userID : " + userId);
			supplierCode = CustomerFunction.SupplierCode(userId);
			LOG.trace(supplierCode);
			sDtoLists = sDao.selectBeforeAll(supplierCode);
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
			rd = request.getRequestDispatcher("supply/sAfterSupply.jsp");
			rd.forward(request, response);
			LOG.trace("sProc.supplyAfterList끝");
			break;
		
		case "supplyAfterListSearch":
			// 상태가 2인 목록(월검색)
			LOG.trace("sProc.supplyAfterListSearch진입");
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
			LOG.trace("sProc.supplyAfterListSearch끝");
			break;
		
		case "intoMain":
			LOG.trace("sProc.intoMain진입");
			
			//	변수
			userId = (String)session.getAttribute("userId");
			LOG.trace("sProc.intoMain변수 userID : " + userId);
			supplierCode = CustomerFunction.SupplierCode(userId);
			LOG.trace("SupplyProc.intoMain supplierCode : "+supplierCode);
			int curMonthTotalSales = 0;
			int curYearTotalSales = 0;
			
			//----------------------------------------------------
			
			//1.이번년도 매출액
			LOG.trace("SupplyProc.intoMain2-1 금년 매출액 계산시작");
			sDtoLists = sDao.supplySalesCurYear(supplierCode);
			for(SupplyDTO supply : sDtoLists) {curYearTotalSales += supply.getsTotalPrice();}
			LOG.trace("sProc.intoMain1 curYearTotalPrice : " + curYearTotalSales);
			LOG.trace("SupplyProc.intoMain2-1 금년 매출액 계산완료");
			
    		//2-1. 작년이맘까지의 매출액 
    		LOG.trace("SupplyProc.intoMain2-1 작년 매출액 계산시작");
    		String lastYear = cf.lastYear(cf.curYear());
    		LOG.trace("SupplyProc.intoMain2-1 lastYear : "+lastYear);
			sDtoLists = sDao.supplySalesLastYear(supplierCode,lastYear);
			int lastYearTotalSales = 0;
			for (SupplyDTO supply : sDtoLists) {
				lastYearTotalSales += supply.getsTotalPrice();
			}
			LOG.trace("SupplyProc.intoMain2-1 lastYearTotalSales : " + lastYearTotalSales);
			LOG.trace("SupplyProc.intoMain2-1 작년 매출액 계산완료");
			
			//2-2. 작년이맘 매출 대비 올해 매출 현황
			LOG.trace("SupplyProc.intoMain2-2 작년이맘 매출 대비 매출액 계산시작");
			int CurYearSalesRatio = curYearTotalSales-lastYearTotalSales;
			LOG.trace("SupplyProc.intoMain2-2 CurYearSalesRatio : " + CurYearSalesRatio);
			LOG.trace("SupplyProc.intoMain2-2 작년이맘 대비 매출액 계산완료");
			
			//3. 이번달 매출액 
			LOG.trace("sProc.intoMain3 이번달 매출액 계산 시작");
			sDtoLists = sDao.searchByMonth(curMonth, supplierCode);
			LOG.trace("sProc.intoMain3 sDtoLists : " + sDtoLists);
			for (SupplyDTO supply : sDtoLists) {curMonthTotalSales += supply.getsTotalPrice();}
			LOG.trace("sProc.intoMain3 이번달 매출액 : "+ curMonthTotalSales);
			LOG.trace("sProc.intoMain3 이번달 매출액 계산 완료");
			
			//4. 이번달 처리완료 건수
			LOG.trace("SupplyProc.intoMain2-4 이번달 처리완료 건수 계산시작");
			int monthListCount = sDao.count(supplierCode);
			LOG.trace("sProc.intoMain3 monthListCount : "+monthListCount);
			LOG.trace("SupplyProc.intoMain2-4 이번달 처리완료 건수 계산완료");
			
			//5.올해 월별 지불 액수
			for(int m=1;m<13;m++) {
				monthTotalPrice = 0;
				sDtoLists = sDao.SalesYearMonth(cf.curYear(),supplierCode,m);
				LOG.trace("sProc.intoMain 올해 월별 총액 : "+m+"월 송장 수 : "+sDtoLists.size()+"");
				for(SupplyDTO supply : sDtoLists) {
					sDtoLists = sDao.selectAll(supplierCode);
	    				monthTotalPrice += supply.getsTotalPrice();
	    		}
				thisTotalSalesList.add(monthTotalPrice); //월별 총액을 리스트로 저장
				thisYearTotalSales += monthTotalPrice; //올해 총액을 int로 저장
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
    		request.setAttribute("CurYearSalesRatio", CurYearSalesRatio);	//2. 작년대비 매출현황
    		request.setAttribute("curMonthTotalSales", curMonthTotalSales); //3. 이번달 매출액
    		request.setAttribute("monthListCount", monthListCount); //4. 이번달 처리완료 건수
       		request.setAttribute("thisTotalSalesList", thisTotalSalesList); //5. 올해 월별 총 지불액
    		request.setAttribute("lastTotalSalesList", lastTotalSalesList); //5. 작년 월별 총 지불액
    		
    		LOG.trace("SupplyProc.intoMain종합 curYearTotalSales : "+curYearTotalSales);
    		LOG.trace("SupplyProc.intoMain종합 CurYearSalesRatio : "+CurYearSalesRatio);
    		LOG.trace("SupplyProc.intoMain종합 curMonthTotalSales : "+curMonthTotalSales);
    		LOG.trace("SupplyProc.intoMain종합 monthListCount : "+monthListCount);
    		LOG.trace("SupplyProc.intoMain종합 thisTotalSalesList : "+thisTotalSalesList);
    		LOG.trace("SupplyProc.intoMain종합 lastTotalSalesList : "+lastTotalSalesList);
    		
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
