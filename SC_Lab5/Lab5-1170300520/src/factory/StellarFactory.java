package factory;

import centralobject.Stellar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**.
 * the factory building a stellar
 *
 * @author Guo Ziyang
 */
public class StellarFactory {

  /**.
   * build a stellar
   *
   * @param name   name
   * @param radius radius
   * @param weight weight
   * @return the stellar
   */
  public Stellar build(String name, Double radius, Double weight) {
    return new Stellar(name, radius, weight);
  }

}
