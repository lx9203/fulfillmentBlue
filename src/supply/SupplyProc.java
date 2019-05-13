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
		
		String sCode = new String();
		String pCode = new String();
		
		CustomerFunction cf = new CustomerFunction();
		String curTime = cf.curTime();

		SupplyDAO sDao = new SupplyDAO();
		SupplyDTO sDto = new SupplyDTO();
		List<SupplyDTO> sDtoLists = new ArrayList<SupplyDTO>();

		switch(action){
		case "complete":
			// complete를 하면 어제에 해당하는 모든 발주를 배송하고(pQuantity+100) 처리완료상태(sState=1)로 만듬
			pCode = request.getParameter("pCode");
			int pQuantity = sDao.selectQuantity(pCode);
			sDao.SupplyQuantity(pCode, pQuantity);
			break;
			
		case "supplyBeforeList":
			LOG.trace("supplyBeforeList진입");
			// 상태가 0 인 목록
			String userId = (String)session.getAttribute("userId");
			LOG.trace("userId : " + userId);
			sDtoLists = sDao.selectBeforeAll(userId);
			int supplyTotalPrice =0;
			for (SupplyDTO supply : sDtoLists) {
				supplyTotalPrice += supply.getsTotalPrice();
			}
			request.setAttribute("supplyTotalPrice",supplyTotalPrice);
			request.setAttribute("supplyList",sDtoLists);
			rd = request.getRequestDispatcher("supply/sBeforeSupply.jsp");
			rd.forward(request, response);
			LOG.trace("supplyBeforeList끝");
			break;
		
		case "supplyAfterListSearch":
			// 상태가 1인 목록
			LOG.trace("supplyAfterListSearch진입");
			userId = (String)session.getAttribute("userId");
			String month = request.getParameter("month");
			sDtoLists = sDao.searchByMonth(month, userId);
			request.setAttribute("selectMonth", month);
			request.setAttribute("supplyList",sDtoLists);
			rd = request.getRequestDispatcher("supply/sAfterSupply.jsp");
			rd.forward(request, response);
			LOG.trace("supplyAfterListSearch끝");
			break;
			
		case "supplyAfterList":
			LOG.trace("supplyAfterList진입");
			userId = (String)session.getAttribute("userId");
			LOG.trace("userId : " + userId);
			sDtoLists = sDao.selectAfterAll(userId);
			request.setAttribute("supplyList",sDtoLists);
			rd = request.getRequestDispatcher("supply/sAfterSupply.jsp");
			rd.forward(request, response);
			LOG.trace("supplyAfterList끝");
			break;
		
		case "intoMain":
			LOG.trace("intoMain진입");
			rd = request.getRequestDispatcher("supply/supplierMain.jsp");
			rd.forward(request, response);
			LOG.trace("intoMain끝");
			break;

		default:
			break;
		}
		LOG.trace("끝");
		
	}

	


}
