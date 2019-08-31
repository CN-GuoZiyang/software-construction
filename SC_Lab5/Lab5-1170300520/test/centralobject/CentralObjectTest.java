package centralobject;

import factory.StellarFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
