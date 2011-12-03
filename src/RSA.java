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

	private BigInteger phi() {
		return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
	}

	public BigInteger encrypt(String message) throws Exception {
		if (e == null || n == null) {
			throw new Exception("Missing public information");
		}

		BigInteger encodedMessage = Util.convertStringToBigInt(message);
		if (encodedMessage.compareTo(n) != -1) {
			throw new Exception("Message too large for public key!");
		}
		return FastExponentiation.fastExponentiation(encodedMessage, e, n);
	}

	public String decrypt(String message) throws Exception {
		if (p == null || q == null) {
			throw new Exception("Missing private information");
		} else if (e == null) {
			throw new Exception("Missing public information");
		}

		BigInteger val = Euclidean.extendedEuclidean(phi(), e)[1].mod(phi());
		BigInteger result = FastExponentiation.fastExponentiation(
				new BigInteger(message), val, n);
		return Util.convertBigIntToString(result);
	}
}
