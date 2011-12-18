import java.math.BigInteger;
import java.util.SortedMap;
import java.util.TreeMap;

public class BabyStepGiantStep {

	public static BigInteger babyStepGiantStep(BigInteger cyclicGroup,
			BigInteger primitiveRoot, BigInteger publicKey) {

		// Setup sorted map of b^(0...m)
		SortedMap<BigInteger, BigInteger> map = new TreeMap<BigInteger, BigInteger>();
		BigInteger m = Util.getSqRoot(cyclicGroup.subtract(BigInteger.ONE));
		for (BigInteger i = BigInteger.ZERO; i.compareTo(m) < 0; i = i
				.add(BigInteger.ONE)) {
			map.put(FastExponentiation.fastExponentiation(primitiveRoot, i,
					cyclicGroup), i);
		}

		// Find the inverse of the generator in the cyclic group
		BigInteger inverse = Euclidean
				.extendedEuclidean(cyclicGroup, primitiveRoot)[1].mod(cyclicGroup);
		// Calculate inverse to the power of m mod p
		BigInteger inverseToM = FastExponentiation.fastExponentiation(inverse,
				m, cyclicGroup);

		// Try to find value in map
		BigInteger result = publicKey;
		BigInteger iterations = BigInteger.ZERO;
		while (map.get(result) == null && iterations.compareTo(m) < 1) {
			result = result.multiply(inverseToM).mod(cyclicGroup);
			iterations = iterations.add(BigInteger.ONE);
		}

		if (map.get(result) != null)
			return m.multiply(iterations).add(map.get(result));
		else
			return null;
	}
}
