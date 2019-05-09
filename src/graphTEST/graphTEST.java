package graphTEST;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

import product.*;

@WebServlet("/view/graphTEST")
public class graphTEST extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(graphTEST.class);
	private static final long serialVersionUID = 1L;
       
    public graphTEST() {
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
		
		//int[] testArray = new int[]{3,3,3,3,3,3,3,3,5,8,9,4};
		String arraySum = "[3,3,3,3,3,3,3,3,5,8,9,4]";
		
		request.setAttribute("testArray", arraySum);
		
		rd = request.getRequestDispatcher("graphTEST.jsp");
		rd.forward(request, response);
		//System.out.println(arraySum);
	}
}