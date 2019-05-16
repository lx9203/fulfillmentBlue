package product;

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

@WebServlet("/view/ProductProc")
public class ProductProc extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(ProductProc.class);
	private static final long serialVersionUID = 1L;

    public ProductProc() {
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//공통 설정 
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher rd;
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		
		List<ProductDTO> pDtoList = new ArrayList<ProductDTO>();
		ProductDAO pDao = new ProductDAO();
		
		switch(action) {
		case "intoMain":
			LOG.trace("카탈로그 입장");
			session.setAttribute("userName", "고객");
			rd = request.getRequestDispatcher("catalog/catalogMain.jsp");
			rd.forward(request, response);
			break;
		case "meat":
			pDtoList = pDao.selectCategory("A");
			request.setAttribute("pDtoList", pDtoList);
			LOG.trace(pDtoList.toString());
			rd = request.getRequestDispatcher("catalog/catalogList.jsp");
			rd.forward(request, response);
			break;
		case "seafood":
			pDtoList = pDao.selectCategory("B");
			request.setAttribute("pDtoList", pDtoList);
			rd = request.getRequestDispatcher("catalog/catalogList.jsp");
			rd.forward(request, response);
			break;
		case "BBQ":
			pDtoList = pDao.selectCategory("C");
			request.setAttribute("pDtoList", pDtoList);
			rd = request.getRequestDispatcher("catalog/catalogList.jsp");
			rd.forward(request, response);
			break;
		case "vegetable":
			pDtoList = pDao.selectCategory("D");
			request.setAttribute("pDtoList", pDtoList);
			rd = request.getRequestDispatcher("catalog/catalogList.jsp");
			rd.forward(request, response);
			break;
		case "spicy":
			pDtoList = pDao.selectCategory("E");
			request.setAttribute("pDtoList", pDtoList);
			rd = request.getRequestDispatcher("catalog/catalogList.jsp");
			rd.forward(request, response);
			break;
		}
	}

}
