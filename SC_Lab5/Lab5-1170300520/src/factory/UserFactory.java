package factory;

import centralobject.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**.
 * the factory building a user
 *
 * @author Guo Ziyang
 */
public class UserFactory {

  /**.
   * build a user
   *
   * @param name the name of the user
   * @return the user
   */
  public User build(String name) {
    return new User(name);
  }

}
