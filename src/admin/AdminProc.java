package admin;

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

@WebServlet("/admin/AdminProc")
public class AdminProc extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(AdminProc.class);
	private static final long serialVersionUID = 1L;
       
    public AdminProc() {
        super();
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
		
		AdminDAO aDao = new AdminDAO();
		AdminDTO aDto = new AdminDTO();
		List<AdminDTO> orderList = new ArrayList<AdminDTO>();
		
		switch(action) {
		case "intoMain":
			LOG.trace("intoMain");
			int thisTotalPrice = 0;
			int lastTotalPrice = 0;
			int MonthTotalPrice =0;
			List<Integer> MonthTotalPriceList = new ArrayList<Integer>();
			
			//1.이번 달 판매수익
			List<String> thisInvoiceCodes = aDao.selectThisMonth();
			LOG.trace("이번달 송장 수 : "+thisInvoiceCodes.size()+"");
			for(String invoiceCode : thisInvoiceCodes) {
				orderList = aDao.selectOrder(invoiceCode);
				for(AdminDTO order : orderList) {
					thisTotalPrice += order.getoQuantity()*order.getpPrice()*1.1;	
				}
				thisTotalPrice += 10000;
			}
			//2.전월 판매수익
			List<String> LastInvoiceCodes = aDao.selectLastMonth();
			LOG.trace("지난달 송장 수 : "+LastInvoiceCodes.size()+"");
			for(String invoiceCode : LastInvoiceCodes) {
				orderList = aDao.selectOrder(invoiceCode);
				for(AdminDTO order : orderList) {
					lastTotalPrice += order.getoQuantity()*order.getpPrice()*1.1;	
				}
				lastTotalPrice += 10000;
			}
			
			//3. 그래프 이번년도 월별 매출 총액
			for(int m=1;m<13;m++) {
				List<String> MonthInvoiceCodes = aDao.selectThisYear(m);
				LOG.trace(m+"월 송장 수 : "+MonthInvoiceCodes.size()+"");
				for(String invoiceCode : MonthInvoiceCodes) {
					orderList = aDao.selectOrder(invoiceCode);
					for(AdminDTO order : orderList) {
						MonthTotalPrice += 1000/*order.getoQuantity()*order.getpPrice()*1.1*/;	
					}
					MonthTotalPrice += 10000;
				}
				MonthTotalPriceList.add(MonthTotalPrice);
			}
			
			request.setAttribute("totalInvoice", thisInvoiceCodes.size()); //3. 이번달 처리 송장 개수
			request.setAttribute("thisTotalPrice",thisTotalPrice);
			request.setAttribute("lastTotalPrice",lastTotalPrice);
			request.setAttribute("MonthTotalPriceList", MonthTotalPriceList); // 그래프 월별 총액 값
			rd = request.getRequestDispatcher("adminMain.jsp");
			rd.forward(request, response);
			break;
		default:
			break;
		}
	}

}
