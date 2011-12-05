import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;

public class Util {

	public static final BigInteger TWO = BigInteger.valueOf(2);
	public static final BigInteger THREE = BigInteger.valueOf(3);
	public static final BigInteger FOUR = BigInteger.valueOf(4);
	public static final BigDecimal TWO_DEC = BigDecimal.valueOf(2);

	private static final SecureRandom rand = new SecureRandom();

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
		BigInteger n;
		do {
			n = randomBigInteger(min.bitLength(), max.bitLength());
		} while (n.compareTo(min) <= 0 || n.compareTo(max) >= 0);
		return n;
	}

	public static BigInteger randomBigInteger(int minBits, int maxBits) {
		// Chose a random length
		int bits = rand.nextInt(maxBits - minBits + 1) + minBits;
		BigInteger n = new BigInteger(bits, rand);
		// Make sure we didn't get a random bigint outside range
		while (n.bitLength() <= minBits && n.bitLength() >= maxBits) {
			n = new BigInteger(bits, rand);
		}
		return n;
	}

	// Taken from http://www.merriampark.com/bigsqrt.htm
	public static BigInteger getSqRoot(BigInteger bigint) {
		BigDecimal n = new BigDecimal(bigint);
		int scale = bigint.toString().length() / 2;
		int length = bigint.toString().length();
		if ((length % 2) == 0)
			length--;
		length /= 2;

		BigDecimal guess = BigDecimal.ONE.movePointRight(length);
		BigDecimal lastGuess = BigDecimal.ZERO;
		BigDecimal error = BigDecimal.ZERO;

		boolean more = true;
		int iterations = 0;
		while (more) {
			lastGuess = guess;
			guess = n.divide(guess, scale, BigDecimal.ROUND_HALF_UP);
			guess = guess.add(lastGuess);
			guess = guess.divide(TWO_DEC, scale, BigDecimal.ROUND_HALF_UP);
			error = n.subtract(guess.multiply(guess));
			if (++iterations >= 50) {
				more = false;
			} else if (lastGuess.equals(guess)) {
				more = error.abs().compareTo(BigDecimal.ONE) >= 0;
			}
		}
		return guess.toBigInteger();
	}
}
