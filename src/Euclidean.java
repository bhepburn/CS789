import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Euclidean {

	public static BigInteger euclidean(BigInteger x, BigInteger y) {
		if (x.compareTo(y) == 1) {
			return recurseEuclidean(x, y, null);
		} else {
			return recurseEuclidean(y, x, null);
		}

	}

	public static BigInteger[] extendedEuclidean(BigInteger x, BigInteger y) {
		List<BigInteger> vals = new ArrayList<BigInteger>();
		if (x.compareTo(y) == 1) {
			recurseEuclidean(x, y, vals);
		} else {
			recurseEuclidean(y, x, vals);
		}
		
		int[] result;
		if (x.compareTo(y) == 1) {
			result = ExtendedEuclid(x.intValue(), y.intValue());
		} else {
			result = ExtendedEuclid(y.intValue(), x.intValue());
		}
		
		return new BigInteger[] {BigInteger.valueOf(result[1]), BigInteger.valueOf(result[2])};
	}

	private static BigInteger recurseEuclidean(BigInteger x, BigInteger y,
			List<BigInteger> vals) {
		BigInteger divisor = x.divide(y);
		BigInteger dividend = x.mod(y);

		if (dividend.equals(BigInteger.ZERO)) {
			vals.add(BigInteger.ONE);
			vals.add(BigInteger.ZERO);
			return y;
		} else {
			BigInteger ans = recurseEuclidean(y, dividend, vals);
			if (vals != null) {
				BigInteger result = vals.get(0).subtract(
						divisor.multiply(vals.get(1)));
				vals.set(0, vals.get(1));
				vals.set(1, result);
			}
			return ans;
		}
	}

	public static int[] ExtendedEuclid(int a, int b) {
		int[] ans = new int[3];
		int q;

		if (b == 0) { /* If b = 0, then we're done... */
			ans[0] = a;
			ans[1] = 1;
			ans[2] = 0;
		} else { /* Otherwise, make a recursive function call */
			q = a / b;
			ans = ExtendedEuclid(b, a % b);
			int temp = ans[1] - ans[2] * q;
			ans[1] = ans[2];
			ans[2] = temp;
		}

		return ans;
	}
}
