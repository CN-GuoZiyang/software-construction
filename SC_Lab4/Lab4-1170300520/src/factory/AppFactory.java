package factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import physicalObject.App;

/**
 * the factory of building apps
 *
 * @author Guo Ziyang
 */
public class AppFactory {

	private Logger logger = LoggerFactory.getLogger(AppFactory.class);

	/**
	 * build a app
	 *
	 * @param name        name
	 * @param cooperation cooperation
	 * @param version     version
	 * @param description description
	 * @param domain      domain
	 * @return a app
	 */
	public App build(String name, String cooperation, String version, String description, String domain) {
		logger.info("build an app with name {}", name);
		return new App(name, cooperation, version, description, domain);
	}

}
