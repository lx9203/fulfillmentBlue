package user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class test {
	public static void main(String[] args) {
		/*UserDAO u = new UserDAO();
		UserDTO m = u.lastId(3);
		System.out.println(m.getId());*/
		
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
	}

}
