import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import main.Util;

import org.junit.Test;


public class TestUtil {

	@Test
	public void testConvertBigIntToString(){
		String message = "z";
		BigInteger x = Util.convertStringToBigInt(message);
		assertEquals(message, Util.convertBigIntToString(x));
		
		System.out.println(Util.convertBigIntToString(new BigInteger("109101114114121032120109097115")));
	}
}
