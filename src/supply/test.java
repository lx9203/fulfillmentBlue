package supply;

import java.io.*;
import java.time.*;
import java.util.*;

public class test {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		SupplyDTO sDto = new SupplyDTO();
		SupplyDAO sDao = new SupplyDAO();
	
		try {
            File csv = new File("D:\\seong\\fulfillmentBlue\\supply(test).csv");
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";
            String[] supply = new String[6];
/*			
 * 			this.sCode = supply[0];
			this.sProductCode = supply[0];
			this.sProductName = supply[1];
			this.sProductPrice = Integer.parseInt(supply[2]);
			this.sDate = LocalDate.now()+"";
			this.sQuantity = Integer.parseInt(supply[3]);
			this.sState = Integer.parseInt(supply[4]);
			this.sTotalPrice = Integer.parseInt(supply[4])*Integer.parseInt(supply[3]);
*/            while ((line = br.readLine()) != null) {
            	String[] token = line.split(",", -1);
            	if(!token[0].equals("")) {
                	supply[0] = token[0]; //sCode
                	supply[1] = token[1];
                	supply[2] = token[2];
                	supply[3] = sDao.curTime();
                	supply[4] = token[4];
                	sDto = new SupplyDTO(supply);
                	System.out.println(sDto.toString());
                }
            } 
            br.close();
		}catch (FileNotFoundException e) {
                e.printStackTrace();
        }catch (IOException e) {
                e.printStackTrace();
        }
		scanner.close();
	}
}
