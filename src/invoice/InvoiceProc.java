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
		//Í≥µÌÜµ ?Ñ§?†ï
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
			JFileChooser chooser = new JFileChooser(); //Í∞ùÏ≤¥ ?Éù?Ñ±
			int ret = chooser.showOpenDialog(null);  //?ó¥Í∏∞Ï∞Ω ?†ï?ùò

			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "Í≤ΩÎ°úÎ•? ?Ñ†?Éù?ïòÏß??ïä?ïò?äµ?ãà?ã§.", "Í≤ΩÍ≥†", JOptionPane.WARNING_MESSAGE);
				return;
			}
		 
			String filePath = chooser.getSelectedFile().getPath();  //?åå?ùºÍ≤ΩÎ°úÎ•? Í∞??†∏?ò¥
			System.out.println(filePath);  //Ï∂úÎ†•
			
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
		
	/*	case "test": 
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
			LOG.trace("?†Ñ?Ü° ?ôÑÎ£?");
			break;*/
			
		
			
		}
		
	}

}
