import java.math.BigInteger;

public class ElGamal {

	private BigInteger p;
	private BigInteger privateKey;

	public ElGamal() {

	}

	public void setCyclicGroup(BigInteger p) throws Exception {
		if (MillerRabin.testStrongPrime(p))
			throw new Exception("Value is not prime!");
		this.p = p;
	}

	public void setPrivateInformation(BigInteger privateKey) {
		this.privateKey = privateKey;
	}

	public BigInteger[] getPublicInformation() {
		BigInteger primitiveRoot = PrimitiveRootSearch.primitiveRootSearch(p);
		BigInteger publicKey = FastExponentiation.fastExponentiation(
				primitiveRoot, privateKey, p);
		return new BigInteger[] { primitiveRoot, publicKey };
	}

	public BigInteger encryptValue(BigInteger[] publicInformation, String msg)
			throws Exception {
		BigInteger val = Util.convertStringToBigInt(msg);
		if (val.compareTo(p) != -1) {
			throw new Exception("Message too large for public key!");
		}

		BigInteger publicKey = publicInformation[1];
		BigInteger encryptionKey = FastExponentiation.fastExponentiation(
				publicKey, privateKey, p);
		return val.multiply(encryptionKey).mod(p);
	}

	public String decryptValue(BigInteger[] publicInformation, BigInteger msg) {
		BigInteger publicKey = publicInformation[1];
		BigInteger decryptionKey = FastExponentiation.fastExponentiation(
				publicKey, p.subtract(BigInteger.ONE).subtract(privateKey), p);
		return Util.convertBigIntToString(msg.multiply(decryptionKey).mod(p));
	}
}
