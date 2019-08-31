package circularOrbit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import factory.ElectronFactory;
import factory.OrbitWithoutPositionFactory;
import physicalObject.Electron;

public class OrbitWithoutPositionTest {
	
	@Test
	public void testTransit() {
		AtomStructure atomStructure = new OrbitWithoutPositionFactory().buildAtomStructure("Te");
		ElectronFactory factory = new ElectronFactory();
		Electron electron1 = factory.build();
		Electron electron2 = factory.build();
		atomStructure.addTrack(1D);
		atomStructure.addTrack(2D);
		atomStructure.addPhysicalObject(electron1, 1D);
		atomStructure.addPhysicalObject(electron2, 2D);
		atomStructure.transit(electron2, 1D);
		assertEquals(Double.valueOf(1), atomStructure.getPositions().get(electron2).getTrack().getRadius());
	}
	
}
