package functionTest;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import function.*;

public class iCodeProcTest {
	private CustomerFunction cf = new CustomerFunction();
	
	@Test
	public void iAreaCode() {
		assertNotNull("조회결과 Null", cf.iCodeProc("a", "001"));
	}
}
