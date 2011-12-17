import java.math.BigInteger;

public class BlumBlumShub {

	// Larger bit size is stalling prime factorization in primitive root search
	private static final int BIT_SIZE = 64;

	// Value of n can be reused so lets generate once per java runtime
	private static BigInteger n;
	static {
		BigInteger p, q;
		// Look for a prime (p) thats 3mod4
		do {
			p = Util.randomBigInteger(BIT_SIZE / 2, BIT_SIZE / 2);
		} while (!p.mod(Util.FOUR).equals(Util.THREE)
				|| !MillerRabin.testStrongPrime(p));

		// Look for a prime (q) thats 3mod4 and not equal to p
		do {
			q = Util.randomBigInteger(BIT_SIZE / 2, BIT_SIZE / 2);
		} while (!q.mod(Util.FOUR).equals(Util.THREE)
				|| !MillerRabin.testStrongPrime(q) || p.equals(q));

		n = p.multiply(q);
	}

	private static BigInteger randomNumber() {
		// Lets find a seed signically smaller than p*q and relatively prime
		BigInteger seed;
		do {
			seed = Util.randomBigInteger(BIT_SIZE, BIT_SIZE);
		} while (!Euclidean.euclidean(seed, n).equals(BigInteger.ONE));

		byte[] bytes = new byte[BIT_SIZE / 8];
		for (int i = 0; i < BIT_SIZE; i++) {
			// if seed % 2 = 1 set the bit otherwise leave it zero
			if (seed.mod(Util.TWO).equals(BigInteger.ONE))
				bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
			// compute next value
			seed = FastExponentiation.fastExponentiation(seed, Util.TWO, n);
		}

		return new BigInteger(1, bytes);
	}

	public static BigInteger randomStrongPrime() {
		BigInteger rand;
		do {
			rand = randomNumber();
		} while (!MillerRabin.testStrongPrime(rand)
				|| !rand.mod(Util.FOUR).equals(Util.THREE));
		return rand;
	}
}
