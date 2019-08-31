package APIs;

import centralObject.AtomicNucleus;
import centralObject.Stellar;
import centralObject.User;
import circularOrbit.AtomStructure;
import circularOrbit.PersonalAppEcosystem;
import circularOrbit.StellarSystem;
import factory.ElectronFactory;
import factory.OrbitWithPositionFactory;
import factory.OrbitWithoutPositionFactory;
import org.junit.Test;

import physicalObject.App;
import physicalObject.Electron;
import physicalObject.Planet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CircularOrbitAPIsTest {

	@Test
	public void getObjectDistributionEntropyTest() throws IOException {
		AtomStructure atomStructure = new OrbitWithoutPositionFactory()
				.buildAtomStructure(new File("src/applications/configurations/AtomicStructure.txt"));
		CircularOrbitAPIs<AtomicNucleus, Electron> circularOrbitAPIs = new CircularOrbitAPIs<>();
		assertEquals(1.2681057323704885, circularOrbitAPIs.getObjectDistributionEntropy(atomStructure), 0.01);
	}

	@Test
	public void getLogicalDistanceTest() {
		AtomStructure atomStructure = new OrbitWithoutPositionFactory().buildAtomStructure("Te");
		ElectronFactory electronFactory = new ElectronFactory();
		Electron electron1 = electronFactory.build();
		Electron electron2 = electronFactory.build();
		Electron electron3 = electronFactory.build();
		Electron electron4 = electronFactory.build();
		Electron electron5 = electronFactory.build();
		Electron electron6 = electronFactory.build();
		atomStructure.addTrack(1D);
		atomStructure.addTrack(2D);
		atomStructure.addTrack(3D);
		atomStructure.addTrack(4D);
		atomStructure.addTrack(5D);
		atomStructure.addPhysicalObject(electron1, 1D);
		atomStructure.addPhysicalObject(electron2, 2D);
		atomStructure.addPhysicalObject(electron3, 3D);
		atomStructure.addPhysicalObject(electron4, 4D);
		atomStructure.addPhysicalObject(electron5, 5D);
		atomStructure.addPhysicalObject(electron6, 5D);
		atomStructure.addRelation(electron1, electron2, 1D);
		atomStructure.addRelation(electron2, electron3, 1D);
		atomStructure.addRelation(electron3, electron4, 1D);
		atomStructure.addRelation(electron1, electron4, 1D);
		atomStructure.addRelation(electron1, electron3, 1D);
		atomStructure.addRelation(electron4, electron5, 1D);
		CircularOrbitAPIs<AtomicNucleus, Electron> circularOrbitAPIs = new CircularOrbitAPIs<>();
		assertEquals(2, circularOrbitAPIs.getLogicalDistance(atomStructure, electron4, electron2));
		assertEquals(1, circularOrbitAPIs.getLogicalDistance(atomStructure, electron1, electron2));
		assertEquals(1, circularOrbitAPIs.getLogicalDistance(atomStructure, electron1, electron3));
		assertEquals(3, circularOrbitAPIs.getLogicalDistance(atomStructure, electron2, electron5));
		assertEquals(Integer.MAX_VALUE, circularOrbitAPIs.getLogicalDistance(atomStructure, electron1, electron6));
		assertEquals(Integer.MAX_VALUE, circularOrbitAPIs.getLogicalDistance(atomStructure, electron6, electron2));
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
		CircularOrbitAPIs<AtomicNucleus, Electron> circularOrbitAPIs = new CircularOrbitAPIs<>();
		assertEquals(Double.valueOf(4), circularOrbitAPIs.getPhysicalDistance(atomStructure, electron1, electron2));
		StellarSystem stellarSystem = null;
		try {
			stellarSystem = new OrbitWithPositionFactory()
					.buildStellarSystem(new File("src/applications/configurations/StellarSystem.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Planet earth = stellarSystem.getObject("Earth");
		Planet mars = stellarSystem.getObject("Mars");
		CircularOrbitAPIs<Stellar, Planet> circularOrbitAPIs1 = new CircularOrbitAPIs<>();
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
		AtomStructure atomStructure2 = new OrbitWithoutPositionFactory()
				.buildAtomStructure(new File("src/applications/configurations/AtomicStructure.txt"));
		CircularOrbitAPIs<AtomicNucleus, Electron> circularOrbitAPIs = new CircularOrbitAPIs<>();
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
		new OrbitWithoutPositionFactory()
				.buildPersonalAppEcosystem(new File("src/applications/configurations/PersonalAppEcosystem.txt"));
		CircularOrbitAPIs<User, App> circularOrbitAPIs1 = new CircularOrbitAPIs<>();
		DifferenceWithObject differenceWithObject = (DifferenceWithObject) circularOrbitAPIs1
				.getDifference(PersonalAppEcosystem.ecosystems.get(0), PersonalAppEcosystem.ecosystems.get(1));
		assertEquals(0, differenceWithObject.getTrackNumberDifference());
		assertEquals(10, differenceWithObject.getPhysicalObjectDifference().size());
		assertEquals(Integer.valueOf(-1), differenceWithObject.getPhysicalObjectNumberDifference().get(9.0));
		assertEquals(Integer.valueOf(1), differenceWithObject.getPhysicalObjectNumberDifference().get(5.0));
		differenceWithObject.toString();
	}
}
