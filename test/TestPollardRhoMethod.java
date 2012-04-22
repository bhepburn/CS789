import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functions.PollardRhoMethod;

import java.math.BigInteger;

public class TestPollardRhoMethod {

	@Test
	public void testPollardRhoMethod() {
		BigInteger result;
		try {
			result = PollardRhoMethod
					.pollardRhoMethod(BigInteger.valueOf(8051));
			if (result.equals(BigInteger.valueOf(83))
					|| result.equals(BigInteger.valueOf(97)))
				assertTrue(true);
			else
				assertTrue(false);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
}
