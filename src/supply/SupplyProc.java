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
		RequestDispatcher rd;
		
		String sCode = new String();
		String pCode = new String();
		String sDate = new String();

		SupplyDAO sDao = new SupplyDAO();
		SupplyDTO sDto = new SupplyDTO();
		List<SupplyDTO> sDtoLists = new ArrayList<SupplyDTO>();

		switch(action){
		case "requestSupply":
			// 발주신청(pCode를 받아 발주코드와 현재시간, 처리상태를 붙임)
			LOG.trace("시작");
			pCode = request.getParameter("pCode"); // pCode받기
			sCode = sCodeCreate(pCode); // pCode로 발주코드 만들기
			sDate = curTime(); // 현재시간만들기
			int sQuantity = 100; // 발주량
			int sState = 0; // 상태를 0으로 부여
			int totalProductPrice = 0;

			sDto.setsCode(sCode);
			sDto.setsProductCode(pCode);
			sDto.setsDate(sDate);
			sDto.setsQuantity(sQuantity);
			sDto.setsState(sState);

			sDao.insertSupply(sDto); // DB에 추가

			break;

		case "complete":
			// complete를 하면 어제에 해당하는 모든 발주를 배송하고(pQuantity+100) 처리완료상태(sState=1)로 만듬
			pCode = request.getParameter("pCode");
			int pQuantity = sDao.selectQuantity(pCode);
			sDao.SupplyQuantity(pCode, pQuantity);
			break;
			
		case "supplyBeforeList":
			LOG.trace("supplyBeforeList");
			// 상태가 0 인 목록
			sDtoLists = sDao.selectBeforeAll();
			request.setAttribute("supplyList",sDtoLists);
			rd = request.getRequestDispatcher("sBeforeSupply.jsp");
			rd.forward(request, response);
			LOG.trace("supplyBeforeList끝");
			break;
		
		case "supplyAfterListSearch":
			// 상태가 1인 목록
			String month = request.getParameter("month");
			sDtoLists = sDao.searchByMonth(month);
			request.setAttribute("selectMonth", month);
			request.setAttribute("supplyList",sDtoLists);
			rd = request.getRequestDispatcher("mall/sAfterSupply.jsp");
			rd.forward(request, response);
			break;
			
		case "supplyAfterList":
			LOG.trace("시작");
			sDtoLists = sDao.searchByMonth();
			request.setAttribute("supplyList",sDtoLists);
			rd = request.getRequestDispatcher("sAfterSupply.jsp");
			rd.forward(request, response);
			break;

		default:
			break;
		}
		LOG.trace("끝");
		
	}

	// 발주코드 생성 함수 { 공급사구분+날짜+자동(3) }
	public static String sCodeCreate(String pCode) {
		// 변수
		SupplyDAO sDao = new SupplyDAO();
		int count = 0;
		String supplier = "";
		// 날짜
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String date = sdf.format(curDate);
		// 공급자 구분
		char supplierCode = pCode.charAt(0);
		supplier = Character.toString(supplierCode);
		// 자동(3)
		int OneOrZero = sDao.searchState(supplier); // sState 검색후 state가 0인것이
		if (OneOrZero != 0) { // 없으면
			count = 101; // 101번부터 시작
		} else { // state가 0인것이 있으면
			count = Integer.parseInt(sDao.searchsCodeBySupplier(supplier).substring(7)) + 1; // count = 이미 있는 sCode의
																								// 마지막번호 +1로 시작
		}

		String sCode = supplier + date + count;
		return sCode;
	}

	// 현재 시간 구하는 함수
	public String curTime() {
		LocalDateTime curTime = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return curTime.format(dateTimeFormatter);
	}

}
