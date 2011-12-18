import java.math.BigInteger;

public class FastExponentiation {

	public static BigInteger fastExponentiation(BigInteger base,
			BigInteger exponent, BigInteger modulus) {
		return recurseFastExponentiation(base, exponent, modulus,
				BigInteger.ONE);
	}

	private static BigInteger recurseFastExponentiation(BigInteger base,
			BigInteger exponent, BigInteger modulus, BigInteger result) {

		if (exponent.equals(BigInteger.ZERO)) {
			return result;
		} else if (exponent.mod(BigInteger.valueOf(2)).equals(BigInteger.ONE)) {
			return recurseFastExponentiation(base,
					exponent.subtract(BigInteger.ONE), modulus,
					base.multiply(result).mod(modulus));
		} else {
			return recurseFastExponentiation(base.multiply(base).mod(modulus),
					exponent.divide(BigInteger.valueOf(2)), modulus, result);
		}
	}
}
