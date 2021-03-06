package factory;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import circularOrbit.StellarSystem;

/**
 * the factory building the non-position-sensitive circular orbit
 * 
 * @author Guo Ziyang
 */
public class OrbitWithPositionFactory {
	
	private Logger logger = LoggerFactory.getLogger(OrbitWithPositionFactory.class);
	
	/**
	 * build a stellar system via the configuration file
	 * 
	 * @param file the configuration file
	 * @return the stellar file
	 */
	public StellarSystem buildStellarSystem(File file) {
		logger.info("Build a stellar system with file {}", file.getAbsolutePath());
		return new StellarSystem(file);
	}
	
}
