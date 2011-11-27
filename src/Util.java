import java.math.BigInteger;

public class Util {

	public static final BigInteger TWO = BigInteger.valueOf(2);

	public static boolean isPrime(BigInteger n) {
		if (n.mod(TWO).equals(BigInteger.ZERO))
			return false;
		for (long i = 3; i * i <= Math.sqrt(n.doubleValue()); i += 2) {
			if (n.longValue() % i == 0)
				return false;
		}
		return true;
	}
}
