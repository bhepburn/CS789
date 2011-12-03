import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class TestEuclidean {

	@Test
	public void testEuclidean() {
		BigInteger five = BigInteger.valueOf(5);
		BigInteger thirteen = BigInteger.valueOf(13);
		BigInteger twenty = BigInteger.valueOf(20);
		assertEquals(BigInteger.ONE, Euclidean.euclidean(five, thirteen));
		assertEquals(five, Euclidean.euclidean(five, twenty));
	}

	@Test
	public void testExtendedEuclidean() {
		BigInteger five = BigInteger.valueOf(5);
		BigInteger thirteen = BigInteger.valueOf(13);
		BigInteger negativeFive = BigInteger.valueOf(-5);
		BigInteger two = BigInteger.valueOf(2);

		BigInteger[] result = Euclidean.extendedEuclidean(five, thirteen);
		assertEquals(negativeFive, result[0]);
		assertEquals(two, result[1]);

		result = Euclidean.extendedEuclidean(thirteen, five);
		assertEquals(two, result[0]);
		assertEquals(negativeFive, result[1]);
	}

}
