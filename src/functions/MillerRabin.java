package functions;

import java.math.BigInteger;

import main.Util;

public class MillerRabin {

	private static int attempts = 20;

	public static boolean testStrongPrime(BigInteger n) {
		// If p is 0, 1, or an event number return false
		if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)
				|| n.mod(Util.TWO).equals(BigInteger.ZERO))
			return false;

		// 2 is prime so return true
		if (n.equals(Util.TWO))
			return true;

		// Generate 2 ^ r * m
		int r = 0;
		BigInteger m = n.subtract(BigInteger.ONE);
		BigInteger nMinusOne = n.subtract(BigInteger.ONE);
		while (m.mod(Util.TWO).equals(BigInteger.ZERO)) {
			m = m.divide(Util.TWO);
			r++;
		}

		for (int i = 0; i < attempts; i++) {
			// Pick a random number
			BigInteger b = Util.randomBigInteger(BigInteger.ONE,
					n.subtract(BigInteger.ONE));

			// Compute b ^ m mod n
			BigInteger z = FastExponentiation.fastExponentiation(b, m, n);

			// If y = 1 mod n or -1 mod n skip and try next random number
			if (!z.equals(BigInteger.ONE) && !z.equals(nMinusOne)) {
				boolean isWitness = false;
				for (int j = 0; j < r; j++) {
					z = FastExponentiation.fastExponentiation(b, Util.TWO
							.pow(j).multiply(m), n);

					// n is a composite
					if (z.equals(BigInteger.ONE))
						return false;

					// b is a witness to n primality
					if (z.equals(nMinusOne)) {
						isWitness = true;
						break;
					}
				}
				if (!isWitness) {
					return false;
				}
			}
		}
		return true;
	}

}
