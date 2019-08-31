package P2;

import java.util.ArrayList;
import java.util.List;

/**
 * the abstraction of a person
 * 
 * @author Guo Ziyang
 */
public class Person {
	private final String name;
	public static List<String> nameList = new ArrayList<>();
	
	// Abstraction function:
    //   describe a PERSON with a unique name
    // Representation invariant:
    //   the person's name which is unique
	//   add the static List nameList to make sure all names are unique 
    // Safety from rep exposure:
    //   all fields are final and private with a getter
	
	public Person(String name) {
		if(Person.nameList.contains(name)) {
			throw new RuntimeException("same name already exists!");
		}
		this.name = name;
		nameList.add(name);
		checkRep();
	}
	
	/**
	 * check the representation invariant
	 */
	public void checkRep() {
		assert name != null;
		assert nameList != null;
		assert nameList.contains(name);
	}
	
	/**
	 * get the name of the person
	 * 
	 * @return the name of the person
	 */
	public String getName() {
		checkRep();
		return name;
	}
}
