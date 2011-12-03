import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class TestFastExponentiation {
	@Test
	public void fastExponentiation() {
		BigInteger result = FastExponentiation.fastExponentiation(
				BigInteger.TEN, BigInteger.valueOf(237812),
				BigInteger.valueOf(32574));
		assertEquals(BigInteger.valueOf(31864), result);
		
		result = FastExponentiation.fastExponentiation(BigInteger.ONE,
				BigInteger.ONE, BigInteger.ONE);
		assertEquals(BigInteger.ZERO, result);
	}
}
