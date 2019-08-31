package centralObject;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import factory.StellarFactory;

public class CentralObjectTest {
	
	@Test
	public void stellarTest() {
		StellarFactory stellarFactory = new StellarFactory();
		Stellar stellar = stellarFactory.build("Sun", Double.valueOf("6.96392e5"), Double.valueOf("1.9885e30"));
		assertEquals("Sun", stellar.getName());
		assertEquals(Double.valueOf("6.96392e5"), stellar.getRadius());
		assertEquals(Double.valueOf("1.9885e30"), stellar.getWeight());
	}
	
}
