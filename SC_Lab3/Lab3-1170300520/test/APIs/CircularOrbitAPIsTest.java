package APIs;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import centralObject.AtomicNucleus;
import circularOrbit.AtomStructure;
import factory.ElectronFactory;
import factory.OrbitWithoutPositionFactory;
import physicalObject.Electron;

public class CircularOrbitAPIsTest {
	
	@Test
	public void getObjectDistributionEntropyTest() {
		AtomStructure atomStructure = new OrbitWithoutPositionFactory().buildAtomStructure(new File("src/applications/configurations/AtomicStructure.txt"));
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
		atomStructure.addTrack(1D);
		atomStructure.addTrack(2D);
		atomStructure.addTrack(3D);
		atomStructure.addTrack(4D);
		atomStructure.addPhysicalObject(electron1, 1D);
		atomStructure.addPhysicalObject(electron2, 2D);
		atomStructure.addPhysicalObject(electron3, 3D);
		atomStructure.addPhysicalObject(electron4, 4D);
		atomStructure.addRelation(electron1, electron2, 1D);
		atomStructure.addRelation(electron2, electron3, 1D);
		atomStructure.addRelation(electron3, electron4, 1D);
		atomStructure.addRelation(electron1, electron4, 1D);
		atomStructure.addRelation(electron1, electron3, 1D);
		CircularOrbitAPIs<AtomicNucleus, Electron> circularOrbitAPIs = new CircularOrbitAPIs<>();
		assertEquals(2, circularOrbitAPIs.getLogicalDistance(atomStructure, electron4, electron2));
		assertEquals(1, circularOrbitAPIs.getLogicalDistance(atomStructure, electron1, electron2));
		assertEquals(1, circularOrbitAPIs.getLogicalDistance(atomStructure, electron1, electron3));
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
	}
	
	@Test
	public void getDifferenceTest() {
		AtomStructure atomStructure1 = new OrbitWithoutPositionFactory().buildAtomStructure("Te");
		ElectronFactory electronFactory = new ElectronFactory();
		Electron electron1 = electronFactory.build();
		Electron electron2 = electronFactory.build();
		atomStructure1.addTrack(1D);
		atomStructure1.addTrack(2D);
		atomStructure1.addPhysicalObject(electron1, 1D);
		atomStructure1.addPhysicalObject(electron2, 2D);
		AtomStructure atomStructure2 = new OrbitWithoutPositionFactory().buildAtomStructure(new File("src/applications/configurations/AtomicStructure.txt"));
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
	}
	
}
