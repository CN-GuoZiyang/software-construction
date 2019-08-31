package circularOrbit;

import factory.ElectronFactory;
import factory.OrbitWithoutPositionFactory;
import org.junit.Before;
import org.junit.Test;
import physicalObject.Electron;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CircularOrbitTest {

	private AtomStructure atomStructure;

	@Before
	public void before() throws IOException {
		atomStructure = new OrbitWithoutPositionFactory()
				.buildAtomStructure(new File("src/applications/configurations/AtomicStructure.txt"));
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

	@Test
	public void addRelationTest() {
		ElectronFactory electronFactory = new ElectronFactory();
		Electron electron1 = electronFactory.build();
		Electron electron2 = electronFactory.build();
		atomStructure.addPhysicalObject(electron1, 3D);
		atomStructure.addPhysicalObject(electron2, 5D);
		atomStructure.addRelation(electron1, electron2, 6D);
		assertTrue(atomStructure.getRelations().get(electron1).containsKey(electron2));
		assertTrue(atomStructure.getRelations().get(electron2).containsKey(electron1));
		assertEquals(Double.valueOf(6), atomStructure.getRelations().get(electron1).get(electron2));
		assertEquals(Double.valueOf(6), atomStructure.getRelations().get(electron2).get(electron1));
	}

}
