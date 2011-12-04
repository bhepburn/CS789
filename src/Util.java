import java.math.BigInteger;
import java.security.SecureRandom;

public class Util {

	public static final BigInteger TWO = BigInteger.valueOf(2);
	public static final BigInteger THREE = BigInteger.valueOf(3);
	public static final BigInteger FOUR = BigInteger.valueOf(4);
	
	private static final SecureRandom rand = new SecureRandom();

	public static boolean isPrimeBruteForce(BigInteger n) {
		if (n.mod(TWO).equals(BigInteger.ZERO))
			return false;
		for (long i = 3; i * i <= Math.sqrt(n.doubleValue()); i += 2) {
			if (n.longValue() % i == 0)
				return false;
		}
		return true;
	}

	public static BigInteger convertStringToBigInt(String message) {
		BigInteger retVal = BigInteger.valueOf(0);
		for (int i = 0; i < message.length(); i++) {
			int charVal = message.charAt(message.length() - (i + 1));
			// Add the value by offsetting by 3 decminal places
			retVal = retVal.add(BigInteger.valueOf(charVal).multiply(
					BigInteger.TEN.pow(i * 3)));
		}
		return retVal;
	}

	public static String convertBigIntToString(BigInteger value) {
		StringBuffer result = new StringBuffer();
		String val = value.toString();
		for (int i = 0; i < Math.ceil((double) val.length() / (double) 3); i++) {
			int end = val.length() - (i * 3);
			int start = (end - 3 < 0) ? 0 : end - 3;
			result.insert(0, (char) Integer.valueOf(val.substring(start, end))
					.intValue());
		}

		return result.toString();
	}

	public static BigInteger randomBigInteger(BigInteger min, BigInteger max) {
		BigInteger r;
		do {
			r = new BigInteger(max.bitLength(), rand);
		} while (r.compareTo(min) <= 0 || r.compareTo(max) >= 0);
		return r;
	}
}
