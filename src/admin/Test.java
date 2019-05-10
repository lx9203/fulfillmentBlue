package admin;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		
		/*for(int a=1;a<13;a++) {
			String str1 = String.format("%02d", a);
			String str2 = String.format("%02d", a+1);
			if(str2.equals("13")) str2 = "01";
			
			System.out.print(str1+ "  ");
			System.out.println(str2);
			
		}*/
		
		
		AdminDAO aDao = new AdminDAO();
		AdminDTO aDto = new AdminDTO();
		List<AdminDTO> orderList = new ArrayList<AdminDTO>();
		
		
		int MonthTotalPrice =0;
		List<Integer> MonthTotalPriceList = new ArrayList<Integer>();
		
		for(int m=1;m<13;m++) {
			List<String> MonthInvoiceCodes = aDao.selectThisYear(m);
		/*	LOG.trace(MonthInvoiceCodes.size()+"");*/
			for(String invoiceCode : MonthInvoiceCodes) {
				orderList = aDao.selectOrder(invoiceCode);
				for(AdminDTO order : orderList) {
					MonthTotalPrice += 1000;	
				}
				MonthTotalPrice += 10000;
			}
			MonthTotalPriceList.add(MonthTotalPrice);
		}
		
		System.out.println(MonthTotalPriceList.toString());
		
		/*int totalPrice = 0;
		List<String> invoiceCodes = aDao.selectThisMonth();
		for(String invoiceCode : invoiceCodes) {
			System.out.println(invoiceCode);
			orderList = aDao.selectOrder("invoiceCode");
			for(AdminDTO order : orderList) {
				totalPrice += order.getoQuantity()*order.getpPrice()*1.1;	
			}
		}
		System.out.println("총액은 " + totalPrice);*/
	}
}
