package centralobject;

/**
 * .
 * the abstraction of the central object at the central point
 *
 * @author Guo Ziyang
 */
public abstract class AbstractCentralObject {

  /**
   * .
   * the name of the central object
   */
  protected String name;

  public AbstractCentralObject(String name) {
    this.name = name;
  }

  public void checkRep() {
    assert name != null;
  }

  /**
   * .
   * get the name of the central object
   *
   * @return the name of the central object
   */
  public String getName() {
    checkRep();
    return name;
  }

}
