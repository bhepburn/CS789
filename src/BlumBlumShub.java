import java.math.BigInteger;

public class BlumBlumShub {
	public static final int BIT_SIZE = 128;

	public static BigInteger randomNumber() {
		BigInteger p, q, n;
		// Look for a prime (p) thats 3mod4
		do {
			p = Util.randomBigInteger(BIT_SIZE);
		} while (!p.mod(Util.FOUR).equals(Util.THREE)
				|| !MillerRabin.testStrongPrime(p));

		// Look for a prime (q) thats 3mod4 and not equal to p
		do {
			q = Util.randomBigInteger(BIT_SIZE);
		} while (!q.mod(Util.FOUR).equals(Util.THREE)
				|| !MillerRabin.testStrongPrime(q) || p.equals(q));
		
		return q;
	}
}
