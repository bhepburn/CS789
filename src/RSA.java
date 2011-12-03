import java.math.BigInteger;

public class RSA {

	private BigInteger p, q, e, n;

	public RSA() {
		p = BlumBlumShub.randomNumber();
		q = BlumBlumShub.randomNumber();

		n = p.multiply(q);

		// e needs to be relatively prime to phi(n)
		do {
			// Find an encryption key between 1 and n (no inclusive)
			e = Util.randomBigInteger(Util.TWO, n.subtract(BigInteger.ONE));
		} while (!Euclidean.euclidean(e, phiOfN()).equals(BigInteger.ONE));
	}

	public void setPrivateInfo(BigInteger p, BigInteger q) {
		this.p = p;
		this.q = q;

		n = p.multiply(q);
	}

	public void setEncryptionKey(BigInteger e) {
		this.e = e;
	}

	public void setPublicInfo(BigInteger encryptionKey, BigInteger n) {
		this.e = encryptionKey;
		this.n = n;
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getEncryptionKey() {
		return e;
	}

	public BigInteger getP() {
		return p;
	}

	public BigInteger getQ() {
		return q;
	}

	private BigInteger phiOfN() {
		return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
	}

	public BigInteger encrypt(String message) throws Exception {
		if (e == null || n == null) {
			throw new Exception("Missing public information");
		}

		// BigInteger encodedMessage = Util.convertStringToBigInt(message);
		BigInteger encodedMessage = new BigInteger(message);
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

		BigInteger val = Euclidean.extendedEuclidean(phiOfN(), e)[1]
				.mod(phiOfN());
		BigInteger result = FastExponentiation.fastExponentiation(
				new BigInteger(message), val, n);
		// return Util.convertBigIntToString(result);
		return result.toString();
	}
}
