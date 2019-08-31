package centralObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the abstraction of the central object at the central point
 * 
 * @author Guo Ziyang
 */
public abstract class CentralObject {
	
	/**
	 * the name of the central object
	 */
	protected String name;
	
	/**
	 * logger
	 */
	protected Logger logger = LoggerFactory.getLogger(CentralObject.class);
	
	public CentralObject(String name) {
		this.name = name;
	}
	
	/**
	 * get the name of the central object
	 * 
	 * @return the name of the central object
	 */
	public String getName() {
		return name;
	}
	
}
