package configuration;

import java.util.HashSet;
import java.util.Set;

import centralObject.Stellar;
import physicalObject.Planet;

/**
 * the configuration of a stellar system
 * 
 * @author Guo Ziyang
 */
public class StellarConfiguration implements Configuration {
	
	private Stellar stellar;
	private Set<Planet> planets;
	
	public StellarConfiguration() {
		this.planets = new HashSet<>();
	}
	
	/**
	 * get the stellar
	 * 
	 * @return stellar
	 */
	public Stellar getStellar() {
		return stellar;
	}
	
	/**
	 * set the stellar
	 * 
	 * @param stellar the stellar
	 */
	public void setStellar(Stellar stellar) {
		this.stellar = stellar;
	}
	
	/**
	 * get the set of the planets
	 * 
	 * @return the set of the planets
	 */
	public Set<Planet> getPlanets() {
		return planets;
	}
	
}
