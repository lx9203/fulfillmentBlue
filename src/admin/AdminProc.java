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

import product.*;
import function.*;

@WebServlet("/view/AdminProc")
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
		CustomerFunction cf = new CustomerFunction();
		
		
		AdminDAO aDao = new AdminDAO();
		AdminDTO aDto = new AdminDTO();
		ProductDTO pDto = new ProductDTO();
		ProductDAO pDao = new ProductDAO();
		List<AdminDTO> orderList = new ArrayList<AdminDTO>();
		List<AdminDTO> invoiceList = new ArrayList<AdminDTO>();
		List<ProductDTO> productList = new ArrayList<ProductDTO>();
		
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
			
			
			int thisTotalSales = 0;
			int monthTotalPrice = 0;
			int thisYearTotalSales =0;
			int lastYearTotalSales =0;
			int shopTotalSales = 0;
			int supplyTotalSales = 0;
			int transTotalSales =0;
		
			int[] itemTotalQuantity = {0,0,0,0,0};
			List<Integer> monthTotalSalesList = new ArrayList<Integer>();
			
			//1.이번 달 판매수익
			List<String> thisInvoiceCodes = aDao.selectThisMonth();
			LOG.trace("이번달 처리 송장 수 : "+thisInvoiceCodes.size());
			for(String invoiceCode : thisInvoiceCodes) {
				orderList = aDao.selectOrder(invoiceCode);
				for(AdminDTO order : orderList) {
					supplyTotalSales += order.getoQuantity()*order.getpPrice(); //6. 공급사 대금 청구
					thisTotalSales += order.getoQuantity()*order.getpPrice()*0.1; 
					shopTotalSales += order.getoQuantity()*order.getpPrice()*1.1; //5. 쇼핑몰 대금 청구
				}
				shopTotalSales += 10000;
				transTotalSales += 10000; //7. 운송사 대금 청구
			}
			//2.전년도 판매수익
			List<String> MonthInvoiceCodes = aDao.selectLastYear();
			LOG.trace("작년 처리 송장 수 : "+MonthInvoiceCodes.size());
			for(String invoiceCode : MonthInvoiceCodes) {
				orderList = aDao.selectOrder(invoiceCode);
				for(AdminDTO order : orderList) {
					lastYearTotalSales += order.getoQuantity()*order.getpPrice()*0.1;	
				}
				lastYearTotalSales += 10000;
			}
			
			//3. 이번년도 판매수익 + 그래프 이번년도 월별 매출 총액
			for(int m=1;m<13;m++) {
				monthTotalPrice = 0;
				List<String> monthTotalInvoiceList = aDao.selectThisYear(m);
				LOG.trace(m+"월 송장 수 : "+monthTotalInvoiceList.size()+"");
				for(String invoiceCode : monthTotalInvoiceList) {
					orderList = aDao.selectOrder(invoiceCode);
					for(AdminDTO order : orderList) {
						monthTotalPrice += order.getoQuantity()*order.getpPrice()*0.1;	
					}
					monthTotalPrice += 10000;
				}
				monthTotalSalesList.add(monthTotalPrice); //월별 총액을 리스트로 저장
				thisYearTotalSales += monthTotalPrice; //올해 총액을 int로 저장
			}
			
			request.setAttribute("thisTotalSales",thisTotalSales); //1.이번 달 판매수익
			request.setAttribute("lastYearTotalSales",lastYearTotalSales); //2.전년도 판매수익
			request.setAttribute("thisYearTotalSales", thisYearTotalSales); //3. 이번년도 판매수익
			request.setAttribute("totalInvoice", thisInvoiceCodes.size()); //4. 이번달 처리 송장 수
			request.setAttribute("shopTotalSales", shopTotalSales); //5. 쇼핑몰 대금 청구
			request.setAttribute("supplyTotalSales", supplyTotalSales); //6. 공급사 대금 청구
			request.setAttribute("transTotalSales", transTotalSales); //7. 운송사 대금 청구
			request.setAttribute("totalSupply", ""); //8. 이번달 발주 처리 수
			
			
			request.setAttribute("monthTotalSalesList", monthTotalSalesList); // 그래프 월별 총액 값
			rd = request.getRequestDispatcher("admin/adminMain.jsp");
			rd.forward(request, response);
			break;
			
		case "productList":
			LOG.trace("재고 물품 확인");
			productList = pDao.selectAll();
			
			request.setAttribute("productList", productList); 
			request.setAttribute("curDate", cf.curDate());
			rd = request.getRequestDispatcher("admin/stockList.jsp");
			rd.forward(request, response);
			break;
			
		case "categoryProductList": //카테고리로 물품을 구별한다.
			LOG.trace("재고 물품 확인");
			String pCode = new String();
			productList = pDao.selectCategory(pCode);
			
			request.setAttribute("productList", productList); 
			request.setAttribute("curDate", cf.curDate());
			rd = request.getRequestDispatcher("admin/stockList.jsp");
			rd.forward(request, response);
			break;
			
		case "transList":
			LOG.trace("운송 준비 송장 확인");
			int invoiceTotalPrice =0;
			invoiceList = aDao.adminTransDay();
			for(AdminDTO invoice : invoiceList) {
				orderList = aDao.selectOrder(invoice.getiCode());
				for(AdminDTO order : orderList) {
					invoiceTotalPrice += order.getoQuantity()*order.getpPrice();	
				}
				invoice.setiTotalPrice(invoiceTotalPrice);
			}
			
			
			request.setAttribute("invoiceList", invoiceList);
			rd = request.getRequestDispatcher("admin/monthlyTrans.jsp");
			rd.forward(request, response);
			break;
		default:
			break;
		}
	}

}
