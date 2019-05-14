package supply;

import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

import org.slf4j.*;

import function.*;

public class test {
	public static void main(String[] args) {
		final Logger LOG = LoggerFactory.getLogger(test.class);
		SupplyDTO sDto = new SupplyDTO();
		SupplyDAO sDao = new SupplyDAO();
		String sCode = new String();
		String sProductCode = new String();
		String sDate = new String();
		CustomerFunction cf = new CustomerFunction();
		
		
//		sProductCode = "A102";
//		LOG.trace("test.sProductCode : "+sProductCode);
//		
//		sDao.insertSupply(sProductCode);
		
//		SupplyDAO sDao = new SupplyDAO();
//		int pQuantity = sDao.selectQuantity(pCode);
//		sDao.SupplyQuantity(pCode, pQuantity);
		
//		List<SupplyDTO> a = sDao.searchByMonth();
//		System.out.println(a);
		
//		List<SupplyDTO> a = sDao.selectAll();
//		for (int i = 0; i < 1; i++) {
//			System.out.println(a);
//		}
		
//		String str = sCodeCreate("A101");
//		System.out.println(str);    
		
//		List<SupplyDTO> request = sDao.selectAll();
//		for (int i=0; i < request.size(); i++) {
//			System.out.println(request.get(i));
//		}
		
//		String sDay = curTime();
//		List<SupplyDTO> request = sDao.searchsCodeByDay(sDay);
//		for (int i=1; i < request.size(); i++) {
//			System.out.println(request.get(i));
//		}
		
//		Date curDate = new Date();
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
//    	String date = sdf.format(curDate);
//		List<SupplyDTO> YesOrNo = sDao.searchByDay(date);
//		for (int i=0; i < YesOrNo.size(); i++) {
//			System.out.println(YesOrNo.get(i));
//		}

	}
	
	
}
