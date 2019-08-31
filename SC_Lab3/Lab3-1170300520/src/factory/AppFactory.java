package factory;

import physicalObject.App;

/**
 * the factory of building apps
 * 
 * @author Guo Ziyang
 */
public class AppFactory {
	
	/**
	 * build a app
	 * 
	 * @param name name
	 * @param cooperation cooperation
	 * @param version version
	 * @param description description
	 * @param domain domain
	 * @return a app
	 */
	public App build(String name, String cooperation, String version, String description, String domain) {
		return new App(name, cooperation, version, description, domain);
	}
	
}
