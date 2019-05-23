package functionTest;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import function.*;

public class sCodeCreateTest {
	private CustomerFunction cf = new CustomerFunction();
	
	@Test
	public void iAreaCode() {
		assertNotNull("조회결과 Null", cf.sCodeCreate("A101"));
	}
}
