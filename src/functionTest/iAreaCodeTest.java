package functionTest;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import function.*;

public class iAreaCodeTest {
	private CustomerFunction cf = new CustomerFunction();
	
	@Test
	public void iAreaCode() {
		assertNotNull("조회결과 Null", cf.iAreaCode("001"));
	}
}
