import java.math.BigInteger;

public class ElGamal {

	private BigInteger p;
	private BigInteger r;

	public ElGamal() {

	}

	public void setCyclicGroup(BigInteger p) throws Exception {
		if (!Util.isPrime(p))
			throw new Exception("Value is not prime!");
		this.p = p;
	}

	public void setPrivateInformation(BigInteger r) {
		this.r = r;
	}

	public BigInteger[] getPublicInformation() {
		BigInteger primitiveRoot = PrimitiveRootSearch.primitiveRootSearch(p);
		BigInteger primitiveRootR = r.pow(primitiveRoot.intValue());
		return new BigInteger[] { primitiveRoot, primitiveRootR };
	}
	
	public BigInteger encryptValue(BigInteger[] publicInformation, BigInteger value){
		BigInteger primitiveRootL = publicInformation[1];
		return value.multiply(primitiveRootL.pow(r.intValue()));
	}
	
	public BigInteger decryptValue(BigInteger[] publicInformation, BigInteger value){
		BigInteger primitiveRootR = publicInformation[1];
		BigInteger primitiveRootRL = primitiveRootR.pow(r.intValue());
		BigInteger inverse = Euclidean.extendedEuclidean(primitiveRootRL, p)[0];
		return inverse.multiply(value).mod(p);
	}

}
