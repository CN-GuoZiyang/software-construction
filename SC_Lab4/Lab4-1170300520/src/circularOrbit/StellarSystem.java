package circularOrbit;

import centralObject.Stellar;
import configuration.StellarConfiguration;
import configuration.StellarConfigurationReader;
import physicalObject.Planet;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * the implement of a stellar system consisting of a stellar and many planets
 *
 * @author Guo Ziyang
 */
public class StellarSystem extends OrbitWithPosition<Stellar, Planet> {

	public StellarSystem(File file) throws IOException {
		super();
		readFromFile(file);
	}

	@Override
	public void readFromFile(File file) throws IOException {
		super.checkRep();
		StellarConfigurationReader reader = new StellarConfigurationReader(file);
		StellarConfiguration configuration = (StellarConfiguration) reader.readFile();
		addCentralObject(configuration.getStellar());
		Set<Planet> planets = configuration.getPlanets();
		for (Planet planet : planets) {
			if (getTrack(planet.getTrackRadius()) == null) {
				addTrack(planet.getTrackRadius());
			}
			addPhysicalObject(planet, planet.getTrackRadius(), planet.getStartAngle());
		}
	}

	@Override
	public Planet getObject(String name) {
		super.checkRep();
		for (Planet planet : getPhysicalObjects()) {
			if (name.equals(planet.getName())) {
				return planet;
			}
		}
		return null;
	}

}
