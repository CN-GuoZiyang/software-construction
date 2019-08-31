package circularorbit;

import configuration.Parser;
import configuration.StellarParser;
import factory.OrbitWithPositionFactory;
import ioperformance.BufferedIoReader;
import ioperformance.Reader;
import org.junit.Test;
import physicalobject.Planet;
import position.AnglePosition;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class OrbitWithPositionTest {

  @Test
  public void moveTest() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/StellarSystem.txt"), parser);
    StellarSystem stellarSystem = new OrbitWithPositionFactory()
            .buildStellarSystem(reader);
    Planet earth = stellarSystem.getObject("Earth");
    stellarSystem.move(earth, 120D);
    assertEquals(Double.valueOf(120), ((AnglePosition) stellarSystem.getPositions().get(earth)).getAngle());
  }

}
