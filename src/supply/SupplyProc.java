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
		LOG.trace("액션");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		LOG.trace("SupplyProc.action : "+action);
		HttpSession session = request.getSession();
		RequestDispatcher rd;
		
		String pCode = new String();
		
		CustomerFunction cf = new CustomerFunction();
		String curMonth = cf.curMonth();
		String supplierCode = new String();
		String userId = new String();

		SupplyDAO sDao = new SupplyDAO();
		List<SupplyDTO> sDtoLists = new ArrayList<SupplyDTO>();

		switch(action){
		case "complete":
			// complete를 하면 어제에 해당하는 모든 발주를 배송하고(pQuantity+100) 처리완료상태(sState=1)로 만듬
			LOG.trace("sProc.complete진입");
			pCode = request.getParameter("pCode");
			int pQuantity = sDao.selectQuantity(pCode);
			sDao.SupplyQuantity(pCode, pQuantity);	// supply DB에 추가
			sDao.insertSupply(pCode);	// Product의 갯수를 증가시킴
			LOG.trace("sProc.complete 완료. sProc.complete퇴장");
			break;
			
		case "supplyBeforeList":
			LOG.trace("sProc.supplyBeforeList진입");
			// 상태가 0,1 인 목록
			userId = (String)session.getAttribute("userId");
			LOG.trace("sProc.intoMain userID : " + userId);
			supplierCode = CustomerFunction.SupplierCode(userId);
			LOG.trace(supplierCode);
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
			LOG.trace("sProc.intoMain userID : " + userId);
			supplierCode = CustomerFunction.SupplierCode(userId);
			LOG.trace(supplierCode);
			int curMonthTotalSales = 0;
			int curYearTotalSales = 0;
			
			//----------------------------------------------------
    		
			//3. 이번달 매출액 
			sDtoLists = sDao.searchByMonth(curMonth, supplierCode); //이번달 처리가 완료된 발주리스트를 불러온다.
			LOG.trace("sProc.intoMain sDtoLists : " + sDtoLists);
			int monthListCount = sDao.count(supplierCode);
			for (SupplyDTO supply : sDtoLists) {
				curMonthTotalSales += supply.getsTotalPrice();
			}
			LOG.trace("sProc.intoMain 이번달 매출액 : "+ curMonthTotalSales);
			LOG.trace("sProc.intoMain 이번달 매출액 계산 완료");
			
			//1.이번년도 월별 지불 액수
			for(SupplyDTO supply : sDtoLists) {
				sDtoLists = sDao.supplySalesCurYear(supplierCode);
				curYearTotalSales += supply.getsTotalPrice();
			}
			LOG.trace("sProc.intoMain curYearTotalPrice : " + curYearTotalSales);
			
    		//2-1. 작년 매출액 
    		LOG.trace("SupplyProc.intoMain 작년 매출액 계산시작");
    		String lastYear = cf.lastYear(cf.curYear());
    		LOG.trace(cf.lastYear(cf.curYear()));
			sDtoLists = sDao.supplySalesYear(supplierCode,lastYear);
			int lastYearTotalSales = 0;
			for (SupplyDTO supply : sDtoLists) {
				lastYearTotalSales += supply.getsTotalPrice();
			}
			LOG.trace("SupplyProc.intoMain lastYearTotalSales 계산완료");
			LOG.trace("SupplyProc.intoMain lastYearTotalSales : " + lastYearTotalSales);
			request.setAttribute("lastYearTotalSales", lastYearTotalSales);
			
			//2-2. 작년매출 대비 올해 매출 현황
			double CurYearSalesRatio = curYearTotalSales-lastYearTotalSales;
			
    		request.setAttribute("curYearTotalSales", curYearTotalSales);//1. 이번년 매출액
    		LOG.trace("SupplyProc.intoMain curYearTotalSales : "+curYearTotalSales);
    		request.setAttribute("CurYearSalesRatio", CurYearSalesRatio);	//2. 작년대비 매출현황
    		LOG.trace("SupplyProc.intoMain CurYearSalesRatio : "+CurYearSalesRatio);
    		request.setAttribute("curMonthTotalPrice", curYearTotalSales); //3. 이번달 매출액
    		LOG.trace("SupplyProc.intoMain curYearTotalSales : "+curYearTotalSales);
    		request.setAttribute("monthListCount", monthListCount); //4. 이번달 처리완료 건수
    		LOG.trace("SupplyProc.intoMain monthListCount : "+monthListCount);
    		
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
