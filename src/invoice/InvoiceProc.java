package invoice;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/InvoiceProc")
public class InvoiceProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InvoiceProc() {
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
		
		switch(action) {
		case "DetailList": //송장 번호에 해당하는 제품 목록을 가져온다.
			//하나의 송장 정보와 해당 송장정보의 모든 제품 목록을 가져온다.
			String iCode = request.getParameter("iCode");
			
			break;
		default:
			break;
		}
	}

}
