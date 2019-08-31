package centralObject;

/**
 * the abstraction of a user in the personal app ecosystem
 * 
 * @author Guo Ziyang
 */
public class User extends CentralObject {

	public User(String name) {
		super(name);
	}
	
	@Override
	public String toString() {
		return name;
	}

}
