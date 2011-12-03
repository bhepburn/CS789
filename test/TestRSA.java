import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class TestRSA {

	@Test
	public void testRSA() {
		try {
			String message = "7654";

			RSA rsa = new RSA();
			BigInteger p = BigInteger.valueOf(937);
			BigInteger q = BigInteger.valueOf(991);
			rsa.setPrivateInfo(p, q);
			rsa.setEncryptionKey(BigInteger.valueOf(19));

			RSA rsa2 = new RSA();
			rsa2.setPublicInfo(rsa.getEncryptionKey(), rsa.getN());

			// Encrypt message
			BigInteger encryptedMessage = rsa2.encrypt(message);

			// Decrypt message
			String decryptedMessage = rsa.decrypt(encryptedMessage.toString());
			assertEquals(message, decryptedMessage);

		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void testRSAGenerated() {
		try {
			String message = "2";

			RSA rsaGen = new RSA();
			RSA rsa2Gen = new RSA();

			// Encrypt message
			rsaGen.setPublicInfo(rsa2Gen.getEncryptionKey(), rsa2Gen.getN());
			BigInteger encryptedMessage = rsaGen.encrypt(message);

			// Decrypt message
			String decryptedMessage = rsa2Gen.decrypt(encryptedMessage
					.toString());
			assertEquals(message, decryptedMessage);
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}
}
