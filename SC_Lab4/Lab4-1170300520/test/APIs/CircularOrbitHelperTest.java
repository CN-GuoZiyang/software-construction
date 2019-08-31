package APIs;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import circularOrbit.AtomStructure;
import circularOrbit.StellarSystem;
import factory.OrbitWithPositionFactory;
import factory.OrbitWithoutPositionFactory;

public class CircularOrbitHelperTest {

	@Test
	public void atomicStructureTest() throws IOException, InterruptedException {
		AtomStructure atomStructure = new OrbitWithoutPositionFactory()
				.buildAtomStructure(new File("src/applications/configurations/AtomicStructure.txt"));
		CircularOrbitHelper.visualize(atomStructure);
		Thread.sleep(100);
	}

	@Test
	public void stellarSystemTest() throws IOException, InterruptedException {
		StellarSystem stellarSystem = new OrbitWithPositionFactory()
				.buildStellarSystem(new File("src/applications/configurations/StellarSystem.txt"));
		CircularOrbitHelper.visualize(stellarSystem);
		Thread.sleep(100);
	}

}
