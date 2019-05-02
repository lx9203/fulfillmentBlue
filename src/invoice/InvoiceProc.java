package invoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet("/test/InvoiceProc")
public class InvoiceProc extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceProc.class);
	private static final long serialVersionUID = 1L;
       
    public InvoiceProc() {
        super();
        // TODO Auto-generated constructor stub
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
		String message = new String();
		String url = new String();
		
		String userId = (String)session.getAttribute("userId");
		
		InvoiceDAO iDao = new InvoiceDAO();
		
		switch(action) {
		case "readCSV":
			JFileChooser chooser = new JFileChooser(); //객체 생성
			int ret = chooser.showOpenDialog(null);  //열기창 정의

			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
		 
			String filePath = chooser.getSelectedFile().getPath();  //파일경로를 가져옴
			System.out.println(filePath);  //출력
			
			try {
				File csv = new File(filePath);
				BufferedReader br = new BufferedReader(new FileReader(csv));
				String line = new String();
				while((line = br.readLine()) != null) {
					String[] tokens = line.split(",",-1);
					for(String token : tokens) { 
						String str = token;
						System.out.print(str);
					}
					System.out.println();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			break;
		
		case "test": 
			int sum=0;
			List<InvoiceProductDTO> productList = iDao.selectAll();
			for(InvoiceProductDTO product : productList) {
				sum += product.getiProductTotal();
			}
			request.setAttribute("productList", productList);
			request.setAttribute("totalSum", sum);
			iDao.close();
			rd = request.getRequestDispatcher("shopMain.jsp");
			rd.forward(request, response);
			LOG.trace("전송 완료");
			break;
			
		
			
		}
		
	}

}
