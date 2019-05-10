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
		case "intoMain": //메인 페이지 들어갈 때
			/*
			1. 이번 달 창고의 매출 = 쇼핑몰-송장-제품가격 ( 물건가격 * 개수 * 0.1)
			2. 창고 매출 (이번년도) 
			3. 창고 매출 (전년도)
			4. 출고 건수 (이번달)
			5. 쇼핑몰로부터 받는 금액 = 물건가격* 개수 * 1.1 + 10000
			6. 공급사에 줘야 하는 금액 = 물건 가격 * 개수
			7. 운송사에게 줘야하는 금액 = 송장 * 10000
			8. 발주 건수 (이번달)
			*/
			
			
			int thisTotalPrice = 0;
			int monthTotalPrice = 0;
			int thisYearTotalPrice =0;
			int lastYearTotalPrice =0;
			int shopTotalPrice = 0;
			int supplyTotalPrice = 0;
			int transTotalPrice =0;
		
			int[] itemTotalQuantity = {0,0,0,0,0};
			List<Integer> monthTotalPriceList = new ArrayList<Integer>();
			
			//1.이번 달 판매수익
			List<String> thisInvoiceCodes = aDao.selectThisMonth();
			LOG.trace("이번달 송장 수 : "+thisInvoiceCodes.size()+"");
			for(String invoiceCode : thisInvoiceCodes) {
				orderList = aDao.selectOrder(invoiceCode);
				for(AdminDTO order : orderList) {
					shopTotalPrice += order.getoQuantity()*order.getpPrice()*1.1; //5. 쇼핑몰 대금 청구
					supplyTotalPrice += order.getoQuantity()*order.getpPrice(); //6. 공급사 대금 청구
					thisTotalPrice += shopTotalPrice-supplyTotalPrice; 
					
					
				}
				transTotalPrice += 10000; //7. 운송사 대금 청구
				shopTotalPrice += 10000;
			}
			//2.전년도 판매수익
			for(int m=1;m<13;m++) {
				List<String> MonthInvoiceCodes = aDao.selectLastYear(m);
				LOG.trace(m+"월 송장 수 : "+MonthInvoiceCodes.size()+"");
				for(String invoiceCode : MonthInvoiceCodes) {
					orderList = aDao.selectOrder(invoiceCode);
					for(AdminDTO order : orderList) {
						lastYearTotalPrice += 1000/*order.getoQuantity()*order.getpPrice()*0.1*/;	
					}
					lastYearTotalPrice += 10000;
				}
			}
			
			//3. 이번년도 판매수익 + 그래프 이번년도 월별 매출 총액
			for(int m=1;m<13;m++) {
				monthTotalPrice = 0;
				List<String> monthTotalInvoiceList = aDao.selectThisYear(m);
				LOG.trace(m+"월 송장 수 : "+monthTotalPriceList.size()+"");
				for(String invoiceCode : monthTotalInvoiceList) {
					orderList = aDao.selectOrder(invoiceCode);
					for(AdminDTO order : orderList) {
						monthTotalPrice += 1000/*order.getoQuantity()*order.getpPrice()*0.1*/;	
					}
					monthTotalPrice += 10000;
				}
				monthTotalPriceList.add(monthTotalPrice); //월별 총액을 리스트로 저장
				thisYearTotalPrice += monthTotalPrice; //올해 총액을 int로 저장
			}
			
			request.setAttribute("thisTotalPrice",thisTotalPrice); //1.이번 달 판매수익
			request.setAttribute("lastYearTotalPrice",lastYearTotalPrice); //2.전년도 판매수익
			request.setAttribute("thisYearTotalPrice", thisYearTotalPrice); //3. 이번년도 판매수익
			request.setAttribute("totalInvoice", thisInvoiceCodes.size()); //4. 이번달 처리 송장 수
			request.setAttribute("shopTotalPrice", shopTotalPrice); //5. 쇼핑몰 대금 청구
			request.setAttribute("supplyTotalPrice", supplyTotalPrice); //6. 공급사 대금 청구
			request.setAttribute("transTotalPrice", transTotalPrice); //7. 운송사 대금 청구
			request.setAttribute("totalSupply", ""); //8. 이번달 발주 처리 수
			
			
			request.setAttribute("monthTotalPriceList", monthTotalPriceList); // 그래프 월별 총액 값
			rd = request.getRequestDispatcher("adminMain.jsp");
			rd.forward(request, response);
			break;
		default:
			break;
		}
	}
	
	/*public static int itemQuantity(AdminDTO order, int[] list) {
		char shop = order.getpCode().charAt(0);
		switch(shop) {
		case 'a':
			list[0] += 
			break;
		}
	}*/

}
