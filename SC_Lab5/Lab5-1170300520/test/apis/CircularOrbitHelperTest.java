package apis;

import circularorbit.AtomStructure;
import circularorbit.StellarSystem;
import configuration.AtomParser;
import configuration.Parser;
import configuration.StellarParser;
import factory.OrbitWithPositionFactory;
import factory.OrbitWithoutPositionFactory;
import ioperformance.BufferedIoReader;
import ioperformance.Reader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class CircularOrbitHelperTest {

  @Test
  public void atomicStructureTest() throws IOException, InterruptedException {
    Parser parser = new AtomParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/AtomicStructure.txt"), parser);
    AtomStructure atomStructure = new OrbitWithoutPositionFactory()
            .buildAtomStructure(reader);
    CircularOrbitHelper.visualize(atomStructure);
    Thread.sleep(100);
  }

  @Test
  public void stellarSystemTest() throws IOException, InterruptedException {
    Parser parser = new StellarParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/StellarSystem.txt"), parser);
    StellarSystem stellarSystem = new OrbitWithPositionFactory()
            .buildStellarSystem(reader);
    CircularOrbitHelper.visualize(stellarSystem);
    Thread.sleep(100);
  }

}
