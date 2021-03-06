import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import functions.MillerRabin;

public class TestMillerRabin {

	@Test
	public void testTest() {
		assertFalse(MillerRabin.testStrongPrime(BigInteger.valueOf(221)));
		assertFalse(MillerRabin.testStrongPrime(BigInteger.valueOf(49)));
		assertFalse(MillerRabin.testStrongPrime(BigInteger.valueOf(53)));
		assertTrue(MillerRabin.testStrongPrime(BigInteger.valueOf(5)));
		assertFalse(MillerRabin.testStrongPrime(BigInteger.valueOf(789)));
		assertFalse(MillerRabin.testStrongPrime(BigInteger.valueOf(1287)));
		assertTrue(MillerRabin.testStrongPrime(BigInteger.valueOf(9091)));
		assertFalse(MillerRabin.testStrongPrime(BigInteger.valueOf(3587)));
		assertFalse(MillerRabin.testStrongPrime(BigInteger.valueOf(4983)));
		assertTrue(MillerRabin.testStrongPrime(BigInteger.valueOf(5479)));
		assertFalse(MillerRabin.testStrongPrime(BigInteger.valueOf(6891)));
		assertTrue(MillerRabin.testStrongPrime(BigInteger.valueOf(44381)));
		assertFalse(MillerRabin.testStrongPrime(BigInteger.valueOf(7987)));
		assertFalse(MillerRabin.testStrongPrime(BigInteger.valueOf(8657)));
		assertTrue(MillerRabin.testStrongPrime(BigInteger.valueOf(90659)));
		assertTrue(MillerRabin.testStrongPrime(BigInteger.valueOf(9221)));
		assertTrue(MillerRabin.testStrongPrime(BigInteger.valueOf(61843)));
		assertFalse(MillerRabin.testStrongPrime(BigInteger.valueOf(9897)));
		assertTrue(MillerRabin.testStrongPrime(BigInteger.valueOf(104491)));
		assertFalse(MillerRabin.testStrongPrime(new BigInteger(
				"15364942138330601650")));
	}
}
