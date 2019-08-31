package circularOrbit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import factory.OrbitWithPositionFactory;
import physicalObject.Planet;
import position.AnglePosition;

public class OrbitWithPositionTest {
	
	@Test
	public void moveTest() throws IOException {
		StellarSystem stellarSystem = new OrbitWithPositionFactory().buildStellarSystem(new File("src/applications/configurations/StellarSystem.txt"));
		Planet earth = stellarSystem.getObject("Earth");
		stellarSystem.move(earth, 120D);
		assertEquals(Double.valueOf(120), ((AnglePosition)stellarSystem.getPositions().get(earth)).getAngle());
	}
	
}
