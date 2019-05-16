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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import product.*;
import function.*;
import supply.*;

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
		String action = request.getParameter("action");
		String message = new String();
		CustomerFunction cf = new CustomerFunction();
		
		
		AdminDAO aDao = new AdminDAO();
		AdminDTO aDto = new AdminDTO();
		ProductDTO pDto = new ProductDTO();
		ProductDAO pDao = new ProductDAO();
		SupplyDTO sDto = new SupplyDTO();
		SupplyDAO sDao = new SupplyDAO();
		List<AdminDTO> orderList = new ArrayList<AdminDTO>();
		List<AdminDTO> invoiceList = new ArrayList<AdminDTO>();
		List<AdminDTO> productList = new ArrayList<AdminDTO>();
		List<AdminDTO> supplyList = new ArrayList<AdminDTO>();		
		List<ProductDTO> pDtoList = new ArrayList<ProductDTO>();
		
		// 일반 변수
		int invoiceTotalPrice =0;
		int productTotalSales = 0;
		String month = new String();
		int count =0;
		
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
			int invoiceTotalSales = 0;
		
			List<Integer> lastTotalSalesList = new ArrayList<Integer>();
			List<Integer> thisTotalSalesList = new ArrayList<Integer>();
			
			//1.이번 달 판매수익
			List<String> thisInvoiceCodes = aDao.selectThisMonth();
			LOG.trace("이번달 처리 송장 수 : "+thisInvoiceCodes.size());
			for(String invoiceCode : thisInvoiceCodes) {
				orderList = aDao.selectOrderList(invoiceCode);
				for(AdminDTO order : orderList) {
					supplyTotalSales += order.getoQuantity()*order.getpPrice(); //6. 공급사 대금 청구
					thisTotalSales += order.getoQuantity()*order.getpPrice()*0.1; 
					shopTotalSales += order.getoQuantity()*order.getpPrice()*1.1; //5. 쇼핑몰 대금 청구
				}
				shopTotalSales += 10000;
				transTotalSales += 10000; //7. 운송사 대금 청구
			}
			
			//3. 이번년도 판매수익 + 그래프 이번년도 월별 매출 총액
			for(int m=1;m<13;m++) {
				monthTotalPrice = 0;
				if(m>Integer.parseInt(cf.curMonth().substring(5))) {
					LOG.trace("현재 달 보다 숫자가 큼");
					break;
				}
				List<String> monthTotalInvoiceList = aDao.selectYear(cf.curYear(),m);
				LOG.trace(m+"월 송장 수 : "+monthTotalInvoiceList.size()+"");
				for(String invoiceCode : monthTotalInvoiceList) {
					orderList = aDao.selectOrderList(invoiceCode);
					for(AdminDTO order : orderList) {
						monthTotalPrice += order.getoQuantity()*order.getpPrice()*0.1;	
					}
					monthTotalPrice += 10000;
				}
				thisTotalSalesList.add(monthTotalPrice); //월별 총액을 리스트로 저장
				thisYearTotalSales += monthTotalPrice; //올해 총액을 int로 저장
			}
			//4. 작년도 판매수익
			for(int m=1;m<13;m++) {
				monthTotalPrice = 0;
				List<String> monthTotalInvoiceList = aDao.selectYear(cf.lastYear(cf.curYear()),m);
				LOG.trace(m+"월 송장 수 : "+monthTotalInvoiceList.size()+"");
				for(String invoiceCode : monthTotalInvoiceList) {
					orderList = aDao.selectOrderList(invoiceCode);
					for(AdminDTO order : orderList) {
						monthTotalPrice += order.getoQuantity()*order.getpPrice()*0.1;	
					}
					monthTotalPrice += 10000;
				}
				lastTotalSalesList.add(monthTotalPrice); //월별 총액을 리스트로 저장
				lastYearTotalSales += monthTotalPrice; //올해 총액을 int로 저장
			}
			
			request.setAttribute("thisTotalSales",thisTotalSales); //1.이번 달 판매수익
			request.setAttribute("lastYearTotalSales",lastYearTotalSales); //2.전년도 판매수익
			request.setAttribute("thisYearTotalSales", thisYearTotalSales); //3. 이번년도 판매수익
			request.setAttribute("totalInvoice", aDao.selectThisMonthReady().size()); //4. 이번달 처리 송장 수
			request.setAttribute("shopTotalSales", shopTotalSales); //5. 쇼핑몰 대금 청구
			request.setAttribute("supplyTotalSales", supplyTotalSales); //6. 공급사 대금 청구
			request.setAttribute("transTotalSales", transTotalSales); //7. 운송사 대금 청구
			request.setAttribute("totalSupply", ""); //8. 이번달 발주 처리 수
			
			
			request.setAttribute("thisTotalSalesList", thisTotalSalesList); // 이번년도 그래프 월별 총액 값
			request.setAttribute("lastTotalSalesList", lastTotalSalesList); // 작년년도 그래프 월별 총액 값
			rd = request.getRequestDispatcher("admin/adminMain.jsp");
			rd.forward(request, response);
			break;
			
//------------------------------------- 1. 재고 관련 ------------------------------------------ 

//(1). 페이지를 들어가면서 모든 물품의 재고를 확인한다. 
		case "productList":
			LOG.trace("재고 물품 확인");
			productList = new ArrayList<AdminDTO>();
			pDtoList = pDao.selectAll();
			for(ProductDTO product : pDtoList) {
				aDto = new AdminDTO();
				aDto.setoQuantity(0); //출고수량이 없을 경우, 0으로 지정
				aDto.setsQuantity(0); //발주수량이 없을 경우, 0으로 지정
				aDto = aDao.selectProductOne(product.getpCode());//제품 출고 수량
				sDto = sDao.productQuantity(product.getpCode());
				aDto.setpCode(product.getpCode());//제품코드
				aDto.setpName(product.getpName()); //제품이름
				aDto.setpQuantity(product.getpQuantity()); //제품 재고량
				aDto.setsQuantity(sDto.getsQuantity());//제품 발주 수량
				LOG.trace("제품 코드 :" + aDto.getpCode() + ", 제품 발주 수량 : "+ aDto.getsQuantity());
				productList.add(aDto);
			}
			request.setAttribute("productList", productList); 
			request.setAttribute("curDate", cf.curDate());
			rd = request.getRequestDispatcher("admin/stockList.jsp"); 
			rd.forward(request, response);
			break;
			
//(2). 카테고리를 선택하여 물품을 구별한다.
		case "categoryProductList": //카테고리로 물품을 구별한다.
			LOG.trace("재고 물품 확인");
			String pCode = new String();
			pDtoList = pDao.selectCategory(Character.toString(pCode.charAt(0)));
			for(ProductDTO product : pDtoList) {
				aDto = new AdminDTO();
				aDto.setoQuantity(0); //출고수량이 없을 경우, 0으로 지정
				aDto.setsQuantity(0); //발주수량이 없을 경우, 0으로 지정
				aDto = aDao.selectProductOne(product.getpCode());//제품 출고 수량
				sDto = sDao.productQuantity(product.getpCode());
				aDto.setpCode(product.getpCode());//제품코드
				aDto.setpName(product.getpName()); //제품이름
				aDto.setpQuantity(product.getpQuantity()); //제품 재고량
				aDto.setsQuantity(sDto.getsQuantity());//제품 발주 수량
				LOG.trace("제품 코드 :" + aDto.getpCode() + ", 제품 발주 수량 : "+ aDto.getsQuantity());
				productList.add(aDto);
			}
			request.setAttribute("productList", productList); 
			request.setAttribute("curDate", cf.curDate());
			rd = request.getRequestDispatcher("admin/stockList.jsp");
			rd.forward(request, response);
			break;
//------------------------------------- 2. 판매 목록 관련 ------------------------------------------ 
			
//(1). 월별 제품이 얼마나 팔렸는지 그 수량을 확인 한다.
		case "salesMonthList" :
			LOG.trace("[관리자 Proc] 이번달 제품 판매 수량 목록");
			productTotalSales = 0;
			invoiceList = aDao.selectProductMonth(cf.curMonth());
			for(AdminDTO invoice : invoiceList) {
				pDto = pDao.searchOne(invoice.getpCode());
				invoice.setpName(pDto.getpName());
				invoice.setpPrice(pDto.getpPrice());
				invoice.setiTotalPrice(invoice.getpPrice()*invoice.getoQuantity());
				productTotalSales += invoice.getiTotalPrice();
			}
			
			request.setAttribute("selectMonth", cf.curMonth());
			request.setAttribute("invoiceList", invoiceList);
			request.setAttribute("productTotalSales", productTotalSales);
			rd = request.getRequestDispatcher("admin/monthlySell.jsp");
			rd.forward(request, response);
			break;
//(2). 지정한 월에 제품 판매 수량을 조사한다.
		case "salesMonthSearchList" :
			LOG.trace("[관리자 Proc] 지정한 월별 제품 판매 수량 목록");
			productTotalSales = 0;
			month = request.getParameter("month");
			invoiceList = aDao.selectProductMonth(month);
			for(AdminDTO invoice : invoiceList) {
				pDto = pDao.searchOne(invoice.getpCode());
				invoice.setpName(pDto.getpName());
				invoice.setpPrice(pDto.getpPrice());
				invoice.setiTotalPrice(invoice.getpPrice()*invoice.getoQuantity());
				productTotalSales += invoice.getiTotalPrice();
			}
			
			request.setAttribute("selectMonth", month);
			request.setAttribute("invoiceList", invoiceList);
			request.setAttribute("productTotalSales", productTotalSales);
			rd = request.getRequestDispatcher("admin/monthlySell.jsp");
			rd.forward(request, response);
			break;
			
			
//------------------------------------- 3. 운송 처리 관련 ------------------------------------------ 
//(1). 송장 처리 신청을 한 송장 리스트를 가져온다.
		case "transPermissionList":
			LOG.trace("운송 준비 송장 확인");
			invoiceTotalSales = 0;
			invoiceList = aDao.selectTransList();
			for(AdminDTO invoice : invoiceList) {
				invoiceTotalPrice = 0;
				orderList = aDao.selectOrderList(invoice.getiCode());
				for(AdminDTO order : orderList) {
					invoiceTotalPrice += order.getoQuantity()*order.getpPrice();	
				}
				invoice.setiTotalPrice(invoiceTotalPrice);
				invoiceTotalSales += invoiceTotalPrice;
			}
			request.setAttribute("invoiceList", invoiceList);
			request.setAttribute("invoiceTotalSales", invoiceTotalSales);
			rd = request.getRequestDispatcher("admin/permissionTrans.jsp");
			rd.forward(request, response);
			break;
//(2). 이번달 처리 완료된 송장을 가져온다.
		case "transMonthList":
			LOG.trace("이번달 운송 송장 확인");
			invoiceTotalSales = 0;
			invoiceList = aDao.adminMonthList(cf.curMonth());
			for(AdminDTO invoice : invoiceList) {
				invoiceTotalPrice =0;
				orderList = aDao.selectOrderList(invoice.getiCode());
				for(AdminDTO order : orderList) {
					invoiceTotalPrice += order.getoQuantity()*order.getpPrice();	
				}
				invoice.setiTotalPrice(invoiceTotalPrice);
				invoiceTotalSales += invoiceTotalPrice;
			}
			request.setAttribute("selectMonth", cf.curMonth());
			request.setAttribute("invoiceList", invoiceList);
			request.setAttribute("invoiceTotalSales", invoiceTotalSales);
			rd = request.getRequestDispatcher("admin/monthlyTrans.jsp");
			rd.forward(request, response);
			break;
			
		case "transSearchMonthList" :
			invoiceTotalSales = 0;
			LOG.trace("검색 월 운송 송장 확인");
			month = request.getParameter("month");
			invoiceList = aDao.adminMonthList(month);
			for(AdminDTO invoice : invoiceList) {
				invoiceTotalPrice =0;
				orderList = aDao.selectOrderList(invoice.getiCode());
				for(AdminDTO order : orderList) {
					invoiceTotalPrice += order.getoQuantity()*order.getpPrice();	
				}
				invoice.setiTotalPrice(invoiceTotalPrice);
				invoiceTotalSales += invoiceTotalPrice;
			}
			request.setAttribute("selectMonth", month);
			request.setAttribute("invoiceList", invoiceList);
			request.setAttribute("invoiceTotalSales", invoiceTotalSales);
			rd = request.getRequestDispatcher("admin/monthlyTrans.jsp");
			rd.forward(request, response);
			break;
		case "permitInvoice":
			LOG.trace("송장 허가");
			count =0;
			invoiceList = aDao.selectTransList();
			LOG.trace("송장 개수 = " + invoiceList.size());
			if(invoiceList.size() == 0) {
				message = "처리 할 송장이 없습니다.";
				request.setAttribute("message", message);
				request.setAttribute("msgState", true);
				rd = request.getRequestDispatcher("AdminProc?action=transPermissionList");
				rd.forward(request, response);
				break;
			}
			for(AdminDTO invoice : invoiceList) {
				LOG.trace("[출고중] 현재 처리 송장 번호 : " + invoice.getiCode());			
				boolean pm = true;
				orderList = aDao.selectOrderList(invoice.getiCode());
				for(AdminDTO order : orderList) {
					if(order.getoQuantity()>order.getpQuantity()) {
						LOG.trace("[출고중] 제품 재고수 : " + order.getpQuantity());
						LOG.trace("[출고중] 제품 출고수 : " + order.getoQuantity());
						aDao.invoiceOutofStockState(invoice.getiCode());
						pm = false;	
						break;
					}
				}
				if(pm) {
					for(AdminDTO order : orderList) {
						pDao.updateQuantity(order.getpCode(),order.getpQuantity()-order.getoQuantity()); //창고에서 물품 출고
						LOG.trace("[출고중] 제품 명 : " + order.getpCode());
						LOG.trace("[출고중] 제품 출고 후 : " + (order.getpQuantity()-order.getoQuantity()));
					}
					aDao.invoiceCompleteState(invoice.getiCode());//재고에 이상이 없으면 해당 송장을 출고 완료 상태로 변경한다.
					count++;
				}
			}
			message = "총 "+count+"건의 송장 처리가 완료되었습니다. <br> (재고 부족으로 인해 발송 못한 송장이 있을 수도 있습니다.)";
			request.setAttribute("message", message);
			request.setAttribute("msgState", true);
			rd = request.getRequestDispatcher("AdminProc?action=transPermissionList");
			rd.forward(request, response);
			break;
//------------------------------------- 4. 발주 목록 관련 ------------------------------------------
		case "supplyPermissionList": //발주 신청 처리가 된 발주 리스트 불러오기
			supplyTotalSales = 0;
			supplyList = aDao.selectSupplyList();
			for(AdminDTO supply: supplyList) {
				supplyTotalSales += supply.getsTotalPrice();
			}
			request.setAttribute("selectMonth", month);
			request.setAttribute("supplyList", supplyList);
			request.setAttribute("supplyTotalSales", supplyTotalSales);
			rd = request.getRequestDispatcher("admin/permissionSupply.jsp");
			rd.forward(request, response);
			break;
		case "supplyMonthList": // 이번달 발주 완료가 된 발주 리스트 불러오기
			supplyTotalSales = 0;
			supplyList = aDao.selectSupplyListMonth(cf.curMonth());
			for(AdminDTO supply: supplyList) {
				supplyTotalSales += supply.getsTotalPrice();
			}
			
			request.setAttribute("selectMonth", cf.curMonth());
			request.setAttribute("supplyList", supplyList);
			request.setAttribute("supplyTotalSales", supplyTotalSales);
			rd = request.getRequestDispatcher("admin/monthlySupply.jsp");
			rd.forward(request, response);
			break;
		case "supplySearchMonthList": // 지정한 달 발주 완료가 된 발주 리스트 불러오기
			supplyTotalSales = 0;
			month = request.getParameter("month");
			supplyList = aDao.selectSupplyListMonth(month);
			for(AdminDTO supply: supplyList) {
				supplyTotalSales += supply.getsTotalPrice();
			}
			request.setAttribute("selectMonth", month);
			request.setAttribute("supplyList", supplyList);
			request.setAttribute("supplyTotalSales", supplyTotalSales);
			rd = request.getRequestDispatcher("admin/monthlySupply.jsp");
			rd.forward(request, response);
			break;
		case "permitSupply":
			LOG.trace("발주 허가");
			count =0;
			supplyList = aDao.selectSupplyList();
			LOG.trace("발주 개수 = " + supplyList.size());
			if(supplyList.size() == 0) {
				message = "처리 할 발주 리스트가 없습니다.";
				request.setAttribute("message", message);
				request.setAttribute("msgState", true);
				rd = request.getRequestDispatcher("AdminProc?action=supplyPermissionList");
				rd.forward(request, response);
				break;
			}
			for(AdminDTO supply : supplyList) {
				pDto = pDao.searchOne(supply.getpCode());
				pDao.updateQuantity(supply.getpCode(),pDto.getpQuantity()+supply.getsQuantity());
				LOG.trace("[발주중] 제품 발주 제품 : " + supply.getpCode());
				LOG.trace("[발주중] 제품 발주 후 : " + (pDto.getpQuantity()+supply.getsQuantity()));
				aDao.supplyCompleteState(supply.getsCode());//해당 발주 리스트를 출고 처리상태로 변경한다.
				count++;
			}
			message = "총 "+count+"건의 발주 처리가 완료되었습니다.";
			request.setAttribute("message", message);
			request.setAttribute("msgState", true);
			rd = request.getRequestDispatcher("AdminProc?action=supplyPermissionList");
			rd.forward(request, response);
			break;
		
			
		default:
			break;
		}
	}

}
