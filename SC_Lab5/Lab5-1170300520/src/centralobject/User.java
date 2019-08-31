package centralobject;

/**.
 * the abstraction of a user in the personal app ecosystem
 *
 * @author Guo Ziyang
 */
public class User extends AbstractCentralObject {

  public User(String name) {
    super(name);
  }

  @Override
  public String toString() {
    return "User (" + name + ")";
  }

}
