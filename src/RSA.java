import java.math.BigInteger;

public class RSA {

	private BigInteger p, q, e, n;

	public RSA() {
	}

	public BigInteger setPrivateInfo(BigInteger p, BigInteger q) {
		this.p = p;
		this.q = q;

		return q.multiply(p);
	}

	public void setPublicInfo(BigInteger encryptionKey, BigInteger modulus) {
		this.e = encryptionKey;
		this.n = modulus;
	}

	private BigInteger sizeOfGroup() {
		return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
	}

	public BigInteger encrypt(String message) throws Exception {
		if (e == null || n == null) {
			throw new Exception("Missing public information");
		}

		BigInteger encodedMessage = convertStringToBigInt(message);
		if (encodedMessage.compareTo(n) != -1) {
			throw new Exception("Message too large for public key!");
		}
		return FastExponentiation.fastExponentiation(encodedMessage, e, n);
	}

	public BigInteger decrypt(BigInteger message) throws Exception {
		if (p == null || q == null) {
			throw new Exception("Missing private information");
		} else if (e == null) {
			throw new Exception("Missing public information");
		}

		BigInteger d = Euclidean.extendedEuclidean(sizeOfGroup(), e)[2];
		return FastExponentiation.fastExponentiation(message, d, n);
	}

	public BigInteger convertStringToBigInt(String message) {
		BigInteger retVal = BigInteger.valueOf(0);
		for (int i = 0; i < message.length(); i++) {
			int charVal = message.charAt(message.length() - (i + 1));
			// Add the value by offsetting by 3 decminal places
			retVal = retVal.add(BigInteger.valueOf(charVal).multiply(
					BigInteger.TEN.pow(i * 3)));
		}
		return retVal;
	}

	public String convertBigIntToString(BigInteger value) {
		StringBuffer result = new StringBuffer();
		String encodedValue = value.toString();
		for (int i = 0; i <= encodedValue.length() / 3 - 1; i++) {
			int length = encodedValue.length() - (i * 3);
			result.insert(0, (char) Integer.valueOf(encodedValue.substring(length - 3, length)).intValue());
		}

		return result.toString();
	}
}
