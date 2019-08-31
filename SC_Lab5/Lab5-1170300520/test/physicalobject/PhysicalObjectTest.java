package physicalobject;

import factory.PlanetFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PhysicalObjectTest {

  @Test
  public void planetTest() {
    Planet earth = new PlanetFactory().build("Earth", "Solid", "Blue", Double.valueOf(6378.137), Double.valueOf("1.49e8"), Double.valueOf(29.783), true, 0D);
    assertEquals("Solid", earth.getState());
    assertEquals("Blue", earth.getColor());
    assertEquals(Double.valueOf(6378.137), earth.getRadius());
    assertEquals(Double.valueOf("1.49e8"), earth.getTrackRadius());
    assertEquals(Double.valueOf(29.783), earth.getSpeed());
    assertTrue(earth.getClockwise());
    assertEquals(Double.valueOf(0), earth.getStartAngle());
  }

}
