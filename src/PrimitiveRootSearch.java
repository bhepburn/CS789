import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PrimitiveRootSearch {

	public static BigInteger primitiveRootSearch(BigInteger p) {
		if (p == null)
			return null;

		// Find prime factors of p-1 once
		BigInteger n = p.subtract(BigInteger.ONE);
		Set<BigInteger> factors = findPrimeFactors(n);

		// Now look for a primitive root starting at 2
		// May need to change this to be random
		for (long i = 2; i < p.longValue(); i++) {
			BigInteger g = BigInteger.valueOf(i);
			if (checkPrimitiveRoot(g, p, n, factors)) {
				return g;
			}
		}
		return null;
	}

	private static boolean checkPrimitiveRoot(BigInteger g, BigInteger p,
			BigInteger n, Set<BigInteger> factors) {
		// Run g^(n / "each factor) mod p
		// If the is 1 mod p then g is not a primitive root
		Iterator<BigInteger> i = factors.iterator();
		while (i.hasNext()) {
			if (FastExponentiation.fastExponentiation(g, n.divide(i.next()), p)
					.equals(BigInteger.ONE)) {
				return false;
			}
		}
		return true;
	}

	private static Set<BigInteger> findPrimeFactors(BigInteger n) {
		// Set is unique
		Set<BigInteger> factors = new HashSet<BigInteger>();
		for (long i = 2; i <= n.longValue(); i++) {
			BigInteger y = BigInteger.valueOf(i);
			while (n.mod(y).equals(BigInteger.ZERO)) {
				// Add y to factors and decrease n
				factors.add(y);
				n = n.divide(y);
			}
		}
		return factors;
	}
}
