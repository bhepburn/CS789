import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class TestRSA {

	@Test
	public void testRSA() {
		try {
			String message = "Hi";
			
			RSA rsa = new RSA();
			BigInteger p = BigInteger.valueOf(937);
			BigInteger q = BigInteger.valueOf(991);
			BigInteger n = rsa.setPrivateInfo(p, q);
			rsa.setPublicInfo(BigInteger.valueOf(19), n);

			RSA rsa2 = new RSA();
			rsa2.setPublicInfo(BigInteger.valueOf(19), n);
			BigInteger encryptedMessage = rsa2.encrypt(message);
			String decryptedMessage = rsa.decrypt(encryptedMessage.toString());
			assertEquals(message, decryptedMessage);
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}
}
