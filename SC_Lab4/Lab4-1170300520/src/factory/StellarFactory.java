package factory;

import centralObject.Stellar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the factory building a stellar
 *
 * @author Guo Ziyang
 */
public class StellarFactory {

	private Logger logger = LoggerFactory.getLogger(StellarFactory.class);

	/**
	 * build a stellar
	 *
	 * @param name   name
	 * @param radius radius
	 * @param weight weight
	 * @return the stellar
	 */
	public Stellar build(String name, Double radius, Double weight) {
		logger.info("Create a stellar with name {}, radius {}, weight {}", name, radius, weight);
		return new Stellar(name, radius, weight);
	}

}
