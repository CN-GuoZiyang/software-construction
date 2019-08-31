package circularorbit;

import configuration.AtomParser;
import configuration.Parser;
import factory.ElectronFactory;
import factory.OrbitWithoutPositionFactory;
import ioperformance.BufferedIoReader;
import ioperformance.Reader;
import org.junit.Before;
import org.junit.Test;
import physicalobject.Electron;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CircularOrbitTest {

  private AtomStructure atomStructure;

  @Before
  public void before() throws IOException {
    Parser parser = new AtomParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/AtomicStructure.txt"), parser);
    atomStructure = new OrbitWithoutPositionFactory()
            .buildAtomStructure(reader);

  }

  @Test
  public void removeTrackTest() {
    assertEquals(5, atomStructure.getTracks().size());
    atomStructure.removeTrack(5D);
    assertEquals(4, atomStructure.getTracks().size());
  }

  @Test
  public void addTrackTest() {
    assertEquals(5, atomStructure.getTracks().size());
    atomStructure.addTrack(6D);
    assertEquals(6, atomStructure.getTracks().size());
  }

  @Test
  public void addCentralRelationTest() {
    ElectronFactory electronFactory = new ElectronFactory();
    Electron electron = electronFactory.build();
    atomStructure.addPhysicalObject(electron, 5D);
    atomStructure.addCentralRelation(electron, 2D);
    assertEquals(1, atomStructure.getCentralRelations().size());
    assertEquals(Double.valueOf(2), atomStructure.getCentralRelations().get(electron));
  }

}
