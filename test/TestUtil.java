import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;


public class TestUtil {

	@Test
	public void testConvertBigIntToString(){
		String message = "z";
		BigInteger x = Util.convertStringToBigInt(message);
		assertEquals(message, Util.convertBigIntToString(x));
	}
}
