package supply;

import java.io.*;
import java.time.*;
import java.util.*;

import invoice.*;

public class test {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		SupplyDTO sDto = new SupplyDTO();
		SupplyDAO sDao = new SupplyDAO();
	
		try {
            // csv 데이터 파일
            File csv = new File("C:\\D:\\seong\\fulfillmentBlue\\supply(test).csv");
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";
            int row =0 ,i;
            String[] customer = new String[5];
            while ((line = br.readLine()) != null) {
            	int count = 10001;
            	String[] token = line.split(",", -1);
            	if(!token[0].equals("")) {
                	LocalDate currentDate = LocalDate.now();
                	customer[1] = token[0]; //sCode
                	customer[2] = token[1]; //sProductCode
                	customer[3] = token[2]; //sDate
                	customer[4] = token[3]; //sQuantity
                	customer[0] = "A"+1+currentDate+count; 
                	iDto = new InvoiceDTO(customer);
                	System.out.println(iDto.toString());
                }   
            	sDto = new SupplyDTO(sCode, sProductCode, sDate, sQuantity, sState);
            	System.out.println(sDto.toString());
            }
		scanner.close();
	}
}
