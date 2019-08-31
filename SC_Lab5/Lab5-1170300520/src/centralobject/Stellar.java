package centralobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**.
 * the abstraction of the star in the stellar system
 *
 * @author Guo Ziyang
 */
public class Stellar extends AbstractCentralObject {

  protected Logger logger = LoggerFactory.getLogger(Stellar.class);

  private final Double radius;
  private final Double weight;

  /**.
   * build a stellar
   *
   * @param name name
   * @param radius radius
   * @param weight weight
   */
  public Stellar(final String name, final Double radius, final Double weight) {
    super(name);
    this.radius = radius;
    this.weight = weight;
  }

  @Override
  public void checkRep() {
    super.checkRep();
    assert radius != null;
    assert weight != null;
  }

  /**.
   * get the radius of the stellar
   *
   * @return radius
   */
  public Double getRadius() {
    checkRep();
    return new Double(radius);
  }

  /**.
   * get the weight of the stellar
   *
   * @return weight
   */
  public Double getWeight() {
    checkRep();
    return new Double(weight);
  }

  @Override
  public String toString() {
    return "Stellar (" + name + ")";
  }

}
