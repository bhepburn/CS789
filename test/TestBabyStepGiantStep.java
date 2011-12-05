import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class TestBabyStepGiantStep {

	@Test
	public void testBabyStepGiantStep() {
		assertEquals(BigInteger.valueOf(69),
				BabyStepGiantStep.babyStepGiantStep(BigInteger.valueOf(101),
						BigInteger.valueOf(2), BigInteger.valueOf(3)));

		assertEquals(BigInteger.valueOf(5),
				BabyStepGiantStep.babyStepGiantStep(BigInteger.valueOf(29),
						BigInteger.valueOf(2), BigInteger.valueOf(3)));

		assertEquals(BigInteger.valueOf(24),
				BabyStepGiantStep.babyStepGiantStep(BigInteger.valueOf(29),
						BigInteger.valueOf(11), BigInteger.valueOf(7)));
	}
}
