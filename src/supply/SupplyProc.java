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

import invoice.*;

@WebServlet("/view/SupplyProc")
public class SupplyProc extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceProc.class);
	private static final long serialVersionUID = 1L;
       
    public SupplyProc() {
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
		
		String sProductCode = new String();
		
		SupplyDAO sDao = new SupplyDAO();
		List<SupplyDTO> sDtoLists = (List<SupplyDTO>) new SupplyDTO();
		
		
    	switch(action) {
    		case "requestSupply" :
    			sProductCode = request.getParameter("pCode");
//    			List<SupplyDTO> request = sDao.selectAll();
//    			for (int i=0; i < request.size(); i++) {
//    				System.out.println(request.get(i));
//    			}
//    			String sDate = curTime();
//    			sDtoLists = sDao.searchByDay(sDate);
//    			sCodeCreate(sProductCode, sDate, count)
//    			request.setAttribute("supplyLists", sDtoLists);
//    			rd = request.getRequestDispatcher("supply/");
//    			rd.forward(request, response);
    			break;
    			
    		case "complete" :
    			
    			break;
    	
    	default:
			break;
		}
	}
	
	//발주코드 생성 함수
	public static String sCodeCreate(String pCode, String date, int count) {
		SupplyDAO sDao = new SupplyDAO();
//		userType  = (Integer)session.getAttribute("userType");
		char supplierCode = pCode.charAt(0);
		Date curDate = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
    	date = sdf.format(curDate);
    	String id = request.getParameter("id");	;
    	String supplier = id.charAt(0);
    	int YesOrNo = sDao.searchStateByDay(date);	// 날짜로 sState 검색후 state가 0인것이 
    	if(YesOrNo != 0) {	//	없으면
    		count =101;	//101번부터 시작
		} else {	// state가 0인것이 있으면
									/*			sCode	   *//*userId첫글자*/
			count = Integer.parseInt(sDao.searchsCodeBySupplier(supplier).substring(7))+1;	// count = 이미 있는 sCode의 마지막번호 +1로 시작
		}
    	
    	String sCode = Character.toString(supplierCode) + date + count;
		return sCode;
	}
	
	//현재 시간 구하는 함수
		public String curTime() {
			LocalDateTime curTime = LocalDateTime.now();
	    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	
	    	return curTime.format(dateTimeFormatter);
		}
}
