package functions;
import java.math.BigInteger;

public class Euclidean {

	public static BigInteger euclidean(BigInteger x, BigInteger y) {
		if (x.compareTo(y) == 1) {
			return recurseEuclidean(x, y, null);
		} else {
			return recurseEuclidean(y, x, null);
		}

	}

	public static BigInteger[] extendedEuclidean(BigInteger x, BigInteger y) {
		BigInteger[] vals = new BigInteger[2];
		if (y.compareTo(x) == 1) {
			recurseEuclidean(x, y, vals);
			return vals;
		} else {
			recurseEuclidean(y, x, vals);
			// Need to swap because x is larger
			return new BigInteger[] { vals[1], vals[0] };
		}
	}

	private static BigInteger recurseEuclidean(BigInteger x, BigInteger y,
			BigInteger[] vals) {

		if (y.equals(BigInteger.ZERO)) {
			if (vals != null) {
				vals[0] = BigInteger.ONE;
				vals[1] = BigInteger.ZERO;
			}
			return x;
		}

		BigInteger ans = recurseEuclidean(y, x.mod(y), vals);
		if (vals != null) {
			BigInteger temp = vals[0].subtract(x.divide(y).multiply(vals[1]));
			vals[0] = vals[1];
			vals[1] = temp;
		}
		return ans;
	}
}
