import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class TestElGamal {

	@Test
	public void testElGamal() {
		try {
			String msg = "Hi";
			ElGamal elGamal = new ElGamal();
			elGamal.setCyclicGroup(BigInteger.valueOf(150001));
			elGamal.setPrivateInformation(BigInteger.valueOf(113));

			ElGamal elGamal2 = new ElGamal();
			elGamal2.setCyclicGroup(BigInteger.valueOf(150001));
			elGamal2.setPrivateInformation(BigInteger.valueOf(1000));

			BigInteger[] publicInfo1 = elGamal.getPublicInformation();
			BigInteger[] publicInfo2 = elGamal2.getPublicInformation();

			BigInteger encryptedValue = elGamal2.encryptValue(publicInfo1, msg);
			String decryptedValue = elGamal.decryptValue(publicInfo2,
					encryptedValue);
			assertEquals(msg, decryptedValue);
		} catch (Exception e) {
			assert (false);
		}
	}
}
