package user;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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
import invoice.*;


@WebServlet("/view/UserProc")
public class UserProc extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(UserProc.class);
	private static final long serialVersionUID = 1L;
	
	public static final int TWO_PASSWORD_MATCH = 1;
	public static final int PASSWORD_FORMAT_ERROR = 2;
	public static final int PASSWORD_NOT_MATCH = 3;
	public static final int DATABASE_ERROR = -1;

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
		String password1 = new String();
		String password2 = new String();
		
		//User관련 변수 목록
		int userType = 0;
		int result = 0; //match 검사용
		String area = new String();
		String errorMessage = null;
		
		
		//user 메소드 관련 변수 설정
		UserDAO uDao = new UserDAO();
		UserDTO uDto = new UserDTO();
		
		switch(action){
		case "intoMain": //로그인 유형에 따른 메인페이지 이동
			userType  = (Integer)session.getAttribute("userType");
			LOG.trace("사용자 유형 :" + userType);
			
			switch(userType) {
			case 1:
				LOG.trace("운송사 입장");
				ProductDAO pDao = new ProductDAO();
				List<ProductDTO> productList = pDao.selectAll();
				rd = request.getRequestDispatcher("TransProc?action=intoMain");
				rd.forward(request, response);
				break;
			case 2:
				LOG.trace("쇼핑몰 입장");
				name = (String)session.getAttribute("userId");
				rd = request.getRequestDispatcher("MallProc?action=intoMain");
				rd.forward(request, response);
				break;
			case 3:
				LOG.trace("공급사 입장");
				name = (String)session.getAttribute("userId");
				LOG.trace(name);
				rd = request.getRequestDispatcher("SupplyProc?action=intoMain");
				rd.forward(request, response);
				break;
			case 0:
				rd = request.getRequestDispatcher("AdminProc?action=intoMain");
				rd.forward(request, response);
				break;
			}
			break;
		case "login" : //로그인을 위한 처리 부분
			
			id = request.getParameter("id");	
			password = request.getParameter("password");
			LOG.trace("사용자 유형 : " + userType + ", 아이디 : " + id +", 비밀번호 : " + password);
			result = uDao.verifyIdPassword(id, password);
			LOG.trace(result+"");
			
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
				rd = request.getRequestDispatcher("UserProc?action=intoMain");
				rd.forward(request, response);
			} else{
				request.setAttribute("message", errorMessage);
				request.setAttribute("msgState", true);
				rd = request.getRequestDispatcher("login.jsp");
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
			//필요한 변수 목록 : userType, name, 
			
			if(!request.getParameter("userType").equals("")) {
				userType = Integer.parseInt(request.getParameter("userType"));
			}
			LOG.trace("userType : " + userType);
			
			name = request.getParameter("name");
			password1 = request.getParameter("InputPassword");
			password2 = request.getParameter("RepeatPassword");
			LOG.trace("비밀번호 1 : " + password1 + " 비밀번호 2 : " + password2);
			result = isValidForm(password1, password2);
			LOG.trace("비밀번호 상태 : " +result);
			switch(result){
			case TWO_PASSWORD_MATCH:
				break;
			case PASSWORD_FORMAT_ERROR:
				errorMessage = "유효한 패스워드를 입력해 주세요."; break;
			case PASSWORD_NOT_MATCH:
				errorMessage = "두개의 패스워드가 다릅니다.<br> 동일한 패스워드를 입력해 주세요."; break;
			}
			
			if(result != TWO_PASSWORD_MATCH) {
				request.setAttribute("message", errorMessage);
				request.setAttribute("msgState", true);
				rd = request.getRequestDispatcher("register.jsp");
				rd.forward(request, response);
				break;
			}
			
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
					id = "aShop";	
				} else {
					LOG.trace(op1.get().charAt(0)+"");
					id = (char)(op1.get().charAt(0)+1)+"Shopping";	
				}
							
				break;
			case 3:
				//맨 처음 회원가입시 객체를 가져오지 않아 확인을 해주기 위해 Optional클래스 이용
				Optional<String> op2 = Optional.ofNullable(uDao.lastId(userType).getId());
				LOG.trace(op2.isPresent()+ "");
				if(!op2.isPresent()) {
					id = "ASupply";	
					LOG.trace("처음 만든 아이디");
				} else {
					id = (char)(op2.get().charAt(0)+1)+ "Seller";	
				}		
				break;
			default:
				message = "사용자 유형 입력을 잘못 받았습니다.\\n";
				request.setAttribute("message", message);
				request.setAttribute("msgState", true);
				rd = request.getRequestDispatcher("register.jsp");
				rd.forward(request, response);
				break;	
			}
			
			uDto = new UserDTO(userType, id, name, password1);
			LOG.trace(uDto.toString());
			uDao.insertUser(uDto);
			
			LOG.trace("회원 가입 완료");
			//회원 가입 완료 문구 작성
			message = "축하합니다! 회원 가입이 완료 되었습니다.<br> 회원님의 아이디는 " + id + " 입니다.";
			url = "login.jsp";
			LOG.trace(message);
			LOG.trace(url);
			request.setAttribute("message", message);
			request.setAttribute("msgState", true);
	        rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
			LOG.trace("전달 완료");
			break;
		default:
		}
	}
	
	//패스워드 확인 form
	public static int isValidForm( String pwd1, String pwd2) {
		String rgx_pwd = "[a-zA-Z0-9!@#$%^*+=-_]{4,20}";
		if (!Pattern.matches(rgx_pwd, pwd1)) {
			return PASSWORD_FORMAT_ERROR;
		}
		if (!pwd1.equals(pwd2)) {
			return PASSWORD_NOT_MATCH;
		}
		return TWO_PASSWORD_MATCH;
	}

}
