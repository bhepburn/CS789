package functions;

import java.math.BigInteger;

import main.Util;

public class PollardRhoMethod {

	public static BigInteger pollardRhoMethod(BigInteger n) throws Exception {
		BigInteger x = Util.TWO;
		BigInteger y = (x.pow(2)).add(BigInteger.ONE);

		while (true) {
			BigInteger g = Euclidean.euclidean(x.subtract(y).abs(), n);
			if (g.compareTo(BigInteger.ONE) != 0 && g.compareTo(n) != 0) {
				return g;
			} else if (g.equals(BigInteger.ONE)) {
				x = ((x.pow(2)).add(BigInteger.ONE)).mod(n);
				y = (((y.pow(2).add(BigInteger.ONE)).pow(2))
						.add(BigInteger.ONE)).mod(n);
			} else {
				throw new Exception("Pollard Rho Method cannot break the value");
			}
		}
	}

}
