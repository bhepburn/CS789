import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

public class BlumBlumShub {

	// Larger bit size is stalling prime factorization in primitive root search
	private static final int BIT_SIZE = 64;
	private static BigInteger n;

	// Value of n can be used so lets generate once run
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
		// Lets find a seed signically smaller than p*q
		BigInteger seed = Util.randomBigInteger(BIT_SIZE, BIT_SIZE);
		System.out.println(seed);

		ByteArrayOutputStream alist = new ByteArrayOutputStream();
		for (int i = 0; i < BIT_SIZE; i++) {
			int b = 0;
			for (int j = 7; j >= 0; j--) {
				// x[n+1] = x[n]^2 mod n
				seed = FastExponentiation.fastExponentiation(seed, Util.TWO, n);
				if (seed.testBit(0))
					b |= (1 << j);
			}

			alist.write(b);
		}
		return new BigInteger(1, alist.toByteArray());
	}

	public static BigInteger randomStrongPrime() {
		BigInteger p;
		do {
			p = Util.randomBigInteger(BIT_SIZE, BIT_SIZE);
		} while (!MillerRabin.testStrongPrime(p)
				|| !p.mod(Util.FOUR).equals(Util.THREE));
		return p;
	}
}
