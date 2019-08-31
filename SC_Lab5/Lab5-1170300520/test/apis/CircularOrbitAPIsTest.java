package apis;

import centralobject.AtomicNucleus;
import centralobject.Stellar;
import centralobject.User;
import circularorbit.AtomStructure;
import circularorbit.PersonalAppEcosystem;
import circularorbit.StellarSystem;
import configuration.AppParser;
import configuration.AtomParser;
import configuration.Parser;
import configuration.StellarParser;
import factory.ElectronFactory;
import factory.OrbitWithPositionFactory;
import factory.OrbitWithoutPositionFactory;
import ioperformance.BufferedIoReader;
import ioperformance.Reader;
import org.junit.Test;
import physicalobject.App;
import physicalobject.Electron;
import physicalobject.Planet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CircularOrbitAPIsTest {

  @Test
  public void getObjectDistributionEntropyTest() throws IOException {
    Parser parser = new AtomParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/AtomicStructure.txt"), parser);
    AtomStructure atomStructure = new OrbitWithoutPositionFactory()
            .buildAtomStructure(reader);
    CircularOrbitApis<AtomicNucleus, Electron> circularOrbitAPIs = new CircularOrbitApis<>();
    assertEquals(1.2681057323704885, circularOrbitAPIs.getObjectDistributionEntropy(atomStructure), 0.01);
  }

  @Test
  public void getPhysicalDistanceTest() {
    AtomStructure atomStructure = new OrbitWithoutPositionFactory().buildAtomStructure("Te");
    ElectronFactory electronFactory = new ElectronFactory();
    Electron electron1 = electronFactory.build();
    Electron electron2 = electronFactory.build();
    atomStructure.addTrack(1D);
    atomStructure.addTrack(5D);
    atomStructure.addPhysicalObject(electron1, 1D);
    atomStructure.addPhysicalObject(electron2, 5D);
    CircularOrbitApis<AtomicNucleus, Electron> circularOrbitAPIs = new CircularOrbitApis<>();
    assertEquals(Double.valueOf(4), circularOrbitAPIs.getPhysicalDistance(atomStructure, electron1, electron2));
    StellarSystem stellarSystem = null;
    try {
      Parser parser = new StellarParser();
      Reader reader = new BufferedIoReader(new File("src/applications/configurations/StellarSystem.txt"), parser);
      stellarSystem = new OrbitWithPositionFactory()
              .buildStellarSystem(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Planet earth = stellarSystem.getObject("Earth");
    Planet mars = stellarSystem.getObject("Mars");
    CircularOrbitApis<Stellar, Planet> circularOrbitAPIs1 = new CircularOrbitApis<>();
    assertEquals(new Double("9.995105906928067E10"),
            circularOrbitAPIs1.getPhysicalDistance(stellarSystem, earth, mars));
  }

  @Test
  public void getDifferenceTest() throws IOException {
    AtomStructure atomStructure1 = new OrbitWithoutPositionFactory().buildAtomStructure("Te");
    ElectronFactory electronFactory = new ElectronFactory();
    Electron electron1 = electronFactory.build();
    Electron electron2 = electronFactory.build();
    atomStructure1.addTrack(1D);
    atomStructure1.addTrack(2D);
    atomStructure1.addPhysicalObject(electron1, 1D);
    atomStructure1.addPhysicalObject(electron2, 2D);
    Parser parser = new AtomParser();
    Reader reader = new BufferedIoReader(new File("src/applications/configurations/AtomicStructure.txt"), parser);
    AtomStructure atomStructure2 = new OrbitWithoutPositionFactory()
            .buildAtomStructure(reader);
    CircularOrbitApis<AtomicNucleus, Electron> circularOrbitAPIs = new CircularOrbitApis<>();
    Difference difference = circularOrbitAPIs.getDifference(atomStructure1, atomStructure2);
    assertEquals(-3, difference.getTrackNumberDifference());
    Map<Double, Integer> map = difference.getPhysicalObjectNumberDifference();
    assertEquals(5, map.size());
    assertEquals(Integer.valueOf(-1), map.get(1D));
    assertEquals(Integer.valueOf(-7), map.get(2D));
    assertEquals(Integer.valueOf(-18), map.get(3D));
    assertEquals(Integer.valueOf(-8), map.get(4D));
    assertEquals(Integer.valueOf(-1), map.get(5D));
    difference.toString();
    Parser parser1 = new AppParser();
    Reader reader1 = new BufferedIoReader(new File("src/applications/configurations/PersonalAppEcosystem.txt"), parser1);
    new OrbitWithoutPositionFactory()
            .buildPersonalAppEcosystem(reader1);
    CircularOrbitApis<User, App> circularOrbitAPIs1 = new CircularOrbitApis<>();
    DifferenceWithObject differenceWithObject = (DifferenceWithObject) circularOrbitAPIs1
            .getDifference(PersonalAppEcosystem.ecosystems.get(0), PersonalAppEcosystem.ecosystems.get(1));
    assertEquals(0, differenceWithObject.getTrackNumberDifference());
    assertEquals(10, differenceWithObject.getPhysicalObjectDifference().size());
    assertEquals(Integer.valueOf(-1), differenceWithObject.getPhysicalObjectNumberDifference().get(9.0));
    assertEquals(Integer.valueOf(1), differenceWithObject.getPhysicalObjectNumberDifference().get(5.0));
    differenceWithObject.toString();
  }
}
