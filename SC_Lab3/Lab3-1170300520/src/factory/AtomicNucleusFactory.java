package factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import centralObject.AtomicNucleus;

/**
 * the atomic nucleus factory
 * 
 * @author Guo Ziyang
 */
public class AtomicNucleusFactory {
	
	private Logger logger = LoggerFactory.getLogger(AtomicNucleusFactory.class);
	
	/**
	 * build an atomic nucleus
	 * 
	 * @param name the name of the atomic nucleus
	 * @return an atomic nucleus
	 */
	public AtomicNucleus build(String name) {
		logger.info("build the AtomicNucleus with name {}", name);
		return new AtomicNucleus(name);
	}

}
