import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import methods.cryptography.RSA;

import org.junit.Test;

public class TestRSA {

	@Test
	public void testRSA() {
		try {
			BigInteger message = BigInteger.valueOf(12345);

			RSA rsa = new RSA();
			BigInteger p = BigInteger.valueOf(937);
			BigInteger q = BigInteger.valueOf(991);
			rsa.setPrivateInfo(p, q);
			rsa.setEncryptionKey(BigInteger.valueOf(17));

			RSA rsa2 = new RSA();
			rsa2.setPublicInfo(rsa.getEncryptionKey(), rsa.getN());

			// Encrypt message
			BigInteger encryptedMessage = rsa2.encrypt(message);

			// Decrypt message
			BigInteger decryptedMessage = rsa.decrypt(encryptedMessage);
			assertEquals(message, decryptedMessage);

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testRSAGenerated() {
		try {
			for (int i = 0; i < 10; i++) {
				BigInteger message = BigInteger.valueOf(54321);

				RSA rsaGen = new RSA();
				RSA rsa2Gen = new RSA();

				// Encrypt message
				rsaGen.setPublicInfo(rsa2Gen.getEncryptionKey(), rsa2Gen.getN());
				BigInteger encryptedMessage = rsaGen.encrypt(message);

				// Decrypt message
				BigInteger decryptedMessage = rsa2Gen.decrypt(encryptedMessage);
				assertEquals(message, decryptedMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
