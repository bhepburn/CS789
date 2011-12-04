import java.math.BigInteger;

public class BlumBlumShub {
	public static final BigInteger minRandom = BigInteger.TEN.pow(5),
			maxRandom = BigInteger.TEN.pow(10);

	public static BigInteger randomNumber() {
		BigInteger p, q, n;
		// Look for a prime (p) thats 3mod4
		do {
			p = Util.randomBigInteger(minRandom, maxRandom);
		} while (!p.mod(Util.FOUR).equals(Util.THREE)
				|| !MillerRabin.testStrongPrime(p));

		// Look for a prime (q) thats 3mod4 and not equal to p
		do {
			q = Util.randomBigInteger(minRandom, maxRandom);
		} while (!q.mod(Util.FOUR).equals(Util.THREE)
				|| !MillerRabin.testStrongPrime(q) || p.equals(q));

		return q;
	}
}
