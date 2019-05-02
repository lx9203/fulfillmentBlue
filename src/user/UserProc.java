package user;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import product.ProductDAO;
import product.ProductDTO;

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
		//공통 설정 
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher rd;
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String message = new String();
		String url = new String();
		
		//변수 목록
		String id = new String();
		String name = new String();
		String password = request.getParameter("password");
		
		//User관련 변수 목록
		int userType =0;
		
		String area = new String();
		
		
		//user 메소드 관련 변수 설정
		UserDAO uDao = new UserDAO();
		UserDTO uDto = new UserDTO();
		
		switch(action){
		case "intoMain":
			userType  = (Integer)session.getAttribute("userType");
			LOG.trace("사용자 유형 :" + userType);
			
			switch(userType) {
			case 1:
				ProductDAO pDao = new ProductDAO();
				List<ProductDTO> productList = pDao.selectAll();
				
				request.setAttribute("productList",productList);
				LOG.trace(productList.toString());
				rd = request.getRequestDispatcher("productMain.jsp");
				rd.forward(request, response);
				break;
			case 2:
				response.sendRedirect("InvoiceProc?action=test"); 
				
				
			}
			break;
		case "login" : //로그인을 위한 처리 부분
			if(!request.getParameter("userType").equals("")) {
				userType = Integer.parseInt(request.getParameter("userType"));
			}
			LOG.trace("userType :" +userType);
			
			id = request.getParameter("id");	
			password = request.getParameter("password");
			LOG.trace("사용자 유형 : " + userType + ", 아이디 : " + id +", 비밀번호 : " + password);
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
				session.setAttribute("userType", uDto.getuserType());
				LOG.trace("userId : " + id + ", userName : " + uDto.getName() + ", userType : " + uDto.getuserType());
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
			session.removeAttribute("userType");
			
			response.sendRedirect("login.jsp");
			break;
		case"register":
			if(!request.getParameter("userType").equals("")) {
				userType = Integer.parseInt(request.getParameter("userType"));
			}
			LOG.trace("userType : " + userType);
			switch (userType) {
			case 1:
				int areaId = Integer.parseInt(request.getParameter("areaId"));
				id = areaId + "area"; //물류센터 아이디 생성시 숫자+지역번호로 지정
				break;
			case 2:
				//맨 처음 회원가입시 객체를 가져오지 않아 확인을 해주기 위해 Optional클래스 이용
				Optional<String> op1 = Optional.ofNullable(uDao.lastId(userType).getId());
				if(!op1.isPresent()) {
					LOG.trace("처음 만든 아이디");
					id = "aShopping";	
				} else {
					LOG.trace(op1.get().charAt(0)+"");
					id = (char)(op1.get().charAt(0)+1)+"Shopping";	
				}
							
				break;
			case 3:
				String buyId = request.getParameter("userId");
				LOG.trace("buyId : " + buyId);
				//맨 처음 회원가입시 객체를 가져오지 않아 확인을 해주기 위해 Optional클래스 이용
				Optional<String> op2 = Optional.ofNullable(uDao.lastId(userType).getId());
				LOG.trace(op2.isPresent()+ "");
				if(!op2.isPresent()) {
					id = "ASeller";	
					LOG.trace("처음 만든 아이디");
				} else {
					id = (char)(op2.get().charAt(0)+1)+ "Seller";	
				}		
				break;
			default:
				message = "사용자 유형 입력을 잘못 받았습니다.\\n";
				url = "register.html";
				request.setAttribute("message", message);
				request.setAttribute("url", url);
				rd = request.getRequestDispatcher("alertMsg.jsp");
				rd.forward(request, response);
				break;	
			}
			
			name = request.getParameter("name");
			password = request.getParameter("password");
			uDto = new UserDTO(userType, id, name, password);
			uDao.insertUser(uDto);
			
			LOG.trace("회원 가입 완료");
			//회원 가입 완료 문구 작성
			message = "축하합니다! 회원 가입이 완료 되었습니다.\\n 회원님의 아이디는 " + id + " 입니다.\\n";
			url = "login.jsp";
			LOG.trace(message);
			LOG.trace(url);
			request.setAttribute("message", message);
			request.setAttribute("url", url);
			rd = request.getRequestDispatcher("alertMsg.jsp");
			rd.forward(request, response);
			LOG.trace("전달 완료");
			break;
		default:
		}
	}

}
