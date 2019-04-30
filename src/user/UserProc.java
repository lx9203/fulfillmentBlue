package user;

import java.io.IOException;
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

@WebServlet("/test/UserProc")
public class UserProc extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(UserProc.class);
	private static final long serialVersionUID = 1L;

    public UserProc() {
    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//인코딩 및 session 설정 
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher rd;
		HttpSession session = request.getSession();
		
		//공통변수 목록
		String action = request.getParameter("action");
		String id = new String();
		String name = new String();
		String password = request.getParameter("password");
		String message = new String();
		String url = new String();
		
		//User관련 변수 목록
		int id_no =0;
		
		String area = new String();
		
		
		//user 메소드 관련 변수 설정
		UserDAO uDao = new UserDAO();
		UserDTO uDto = new UserDTO();
		
		switch(action){
		case "intoMain":
			char user = ((String)session.getAttribute("userId")).charAt(0);
			LOG.trace("사용자 유형 :" + user);
			
			switch(user) {
			case '1':
				ProductDAO pDao = new ProductDAO();
				List<ProductDTO> productList = pDao.selectAll();
				
				request.setAttribute("productList",productList);
				LOG.trace(productList.toString());
				rd = request.getRequestDispatcher("productMain.jsp");
				rd.forward(request, response);
				break;
				
			}
			break;
		case "login" : //로그인을 위한 처리 부분
			if(!request.getParameter("id_no").equals("")) {
				id_no = Integer.parseInt(request.getParameter("id_no"));
			}
			
			id = id_no + request.getParameter("id"); //사용자 유형과 아이디를 합쳐 기록		
			password = request.getParameter("password");
			LOG.trace("사용자 유형 : " + id_no + ", 아이디 : " + id +", 비밀번호 : " + password);
			int result = uDao.verifyIdPassword(id, password);
			String errorMessage = null;
			
			switch(result){
			case UserDAO.ID_PASSWORD_MATCH:
				break;
			case UserDAO.ID_DOES_NOT_EXIST:
				errorMessage = "아이디가 존재하지 않음"; break;
			case UserDAO.PASSWORD_IS_WRONG:
				errorMessage = "패스워드가 틀렸음"; break;
			case UserDAO.DATABASE_ERROR:
				errorMessage = "DB 오류"; break;
			}
			
			if(result == UserDAO.ID_PASSWORD_MATCH){
				uDto = uDao.searchById(id);
				session.setAttribute("userId", id);
				session.setAttribute("userName", uDto.getName());
				LOG.trace("userId : " + id + ", userName : " + uDto.getName());
				message = "로그인이 되었습니다. 축하합니다. 로그인 프로그램을 완성 하였습니다.";
				url = "UserProc?action=intoMain";
				request.setAttribute("message", message);
				request.setAttribute("url", url);
				rd = request.getRequestDispatcher("alertMsg.jsp");
				rd.forward(request, response);
			} else{
				request.setAttribute("message", errorMessage);
				request.setAttribute("url", "login.jsp");
				rd = request.getRequestDispatcher("alertMsg.jsp");
				rd.forward(request, response);
			}
			uDao.close();
			break;
		case "logout":
			session.removeAttribute("userId");
			session.removeAttribute("userName");
			
			response.sendRedirect("login.jsp");
			break;
		case"register":
			if(!request.getParameter("id_no").equals("")) {
				id_no = Integer.parseInt(request.getParameter("id_no"));
			}
			//구매처와 쇼핑몰에서는 지역 넘버가 들어가면 안됨
			if(!request.getParameter("area").equals("")) {
				area = request.getParameter("area");
			} else area ="";
			
			id = id_no + request.getParameter("id"); //사용자 유형과 아이디를 합쳐 기록	
			name = request.getParameter("name");
			password = request.getParameter("password");
			uDto = new UserDTO(area, id, name, password);
			LOG.trace(uDto.toString());
			uDao.insertUser(uDto);
			
			//회원 가입 완료 문구 작성
			message = "축하합니다! 회원 가입이 완료 되었습니다.";
			url = "login.jsp";
			
			request.setAttribute("message", message);
			request.setAttribute("url", url);
			rd = request.getRequestDispatcher("alertMsg.jsp");
			rd.forward(request, response);
			
			break;
		default:
		}
	}

}
