package circularOrbit;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import centralObject.Stellar;
import configuration.StellarConfiguration;
import configuration.StellarConfigurationReader;
import physicalObject.Planet;

/**
 * the implement of a stellar system consisting of a stellar and many planets
 * 
 * @author Guo Ziyang
 */
public class StellarSystem extends ConcreteCircularOrbitWithPosition<Stellar, Planet>{
	
	public StellarSystem(File file) {
		super();
		readFromFile(file);
	}
	
	@Override
	public void readFromFile(File file) {
		StellarConfigurationReader reader = new StellarConfigurationReader(file);
		StellarConfiguration configuration = reader.readFile();
		addCentralObject(configuration.getStellar());
		Set<Planet> planets = configuration.getPlanets();
		Iterator<Planet> iterator = planets.iterator();
		while(iterator.hasNext()) {
			Planet planet = iterator.next();
			if(getTrack(planet.getTrackRadius()) == null) {
				addTrack(planet.getTrackRadius());
			}
			addPhysicalObject(planet, planet.getTrackRadius(), planet.getStartAngle());
		}
	}
	
	@Override
	public Planet getObject(String name) {
		for(Planet planet : getPhysicalObjects()) {
			if(name.equals(planet.getName())) {
				return planet;
			}
		}
		return null;
	}

}
