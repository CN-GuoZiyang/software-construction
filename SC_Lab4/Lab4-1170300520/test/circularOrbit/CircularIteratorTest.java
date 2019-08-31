package circularOrbit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import factory.OrbitWithPositionFactory;
import physicalObject.Planet;

public class CircularIteratorTest {
	
	@Test
	public void testIterator() throws IOException {
		StellarSystem stellarSystem = new OrbitWithPositionFactory().buildStellarSystem(new File("src/applications/configurations/StellarSystem.txt"));
		CircularOrbitIterator<Planet> iterator = stellarSystem.iterator();
		List<Planet> planets = new ArrayList<>();
		while(iterator.hasNext()) {
			planets.add(iterator.next());
		}
		assertEquals(8, planets.size());
	}
	
	
}
