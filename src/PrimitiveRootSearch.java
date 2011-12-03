import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PrimitiveRootSearch {

	public static BigInteger primitiveRootSearch(BigInteger p) {
		if (p == null)
			return null;

		for (long i = 2; i < p.longValue(); i++) {
			BigInteger g = BigInteger.valueOf(i);
			if (checkPrimitiveRoot(g, p)) {
				return g;
			}
		}
		return null;
	}

	private static boolean checkPrimitiveRoot(BigInteger g, BigInteger p) {
		BigInteger n = p.subtract(BigInteger.ONE);
		Iterator<BigInteger> factors = findPrimeFactors(n).iterator();

		while (factors.hasNext()) {
			if (FastExponentiation.fastExponentiation(g,
					n.divide(factors.next()), p).equals(BigInteger.ONE)) {
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
				factors.add(y);
				n = n.divide(y);
			}
		}
		return factors;
	}
}
