import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestElGamal {

	@Test
	public void testElGamal() {
		try {
			String msg = "12345";
			ElGamal elGamal = new ElGamal();
			ElGamal elGamal2 = new ElGamal();

			// elGamal has chosen p & primitive root
			// provide them to elGamal2
			elGamal2.setPublicInfo(elGamal.getCyclicGroup(),
					elGamal.getPrimitiveRoot());

			// Exchange public keys
			elGamal2.setSharedKey(elGamal.getPublicKey());
			elGamal.setSharedKey(elGamal2.getPublicKey());

			//Encrypt a message
			String encryptedValue = elGamal2.encrypt(msg);
			
			//Decrypt the message
			String decryptedValue = elGamal.decrypt(encryptedValue);
			
			// Verify the result
			assertEquals(msg, decryptedValue);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
