package circularorbit;

import configuration.Parser;
import configuration.StellarParser;
import factory.OrbitWithPositionFactory;
import ioperformance.BufferedIoReader;
import ioperformance.Reader;
import org.junit.Test;
import physicalobject.Planet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CircularIteratorTest {

  @Test
  public void testIterator() throws IOException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/StellarSystem.txt"), parser);
    StellarSystem stellarSystem = new OrbitWithPositionFactory()
            .buildStellarSystem(reader);
    CircularOrbitIterator<Planet> iterator = stellarSystem.iterator();
    List<Planet> planets = new ArrayList<>();
    while (iterator.hasNext()) {
      planets.add(iterator.next());
    }
    assertEquals(8, planets.size());
  }


}
