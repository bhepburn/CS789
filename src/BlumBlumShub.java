import java.math.BigInteger;

public class BlumBlumShub {

	public static final String minRandom = "1000000000000",
			maxRandom = "100000000000000000000000000000000000000000000";

	public static BigInteger randomNumber() {
		BigInteger p, q, n;
		// Look for a prime (p) thats 3mod4
		do {
			p = Util.randomBigInteger(new BigInteger(minRandom),
					new BigInteger(maxRandom));
		} while (!p.mod(Util.FOUR).equals(Util.THREE)
				|| !MillerRabin.testStrongPrime(p));

		// Look for a prime (q) thats 3mod4 and not equal to p
		do {
			q = Util.randomBigInteger(new BigInteger(minRandom),
					new BigInteger(maxRandom));
		} while (!q.mod(Util.FOUR).equals(Util.THREE)
				|| !MillerRabin.testStrongPrime(q) || p.equals(q));

		return q;
	}
}
