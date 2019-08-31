package factory;

import centralObject.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the factory building a user
 *
 * @author Guo Ziyang
 */
public class UserFactory {

	private Logger logger = LoggerFactory.getLogger(UserFactory.class);

	/**
	 * build a user
	 *
	 * @param name the name of the user
	 * @return the user
	 */
	public User build(String name) {
		logger.info("Create a user with name {}", name);
		return new User(name);
	}

}
