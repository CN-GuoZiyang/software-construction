package factory;

import centralobject.AtomicNucleus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**.
 * the atomic nucleus factory
 *
 * @author Guo Ziyang
 */
public class AtomicNucleusFactory {

  /**.
   * build an atomic nucleus
   *
   * @param name the name of the atomic nucleus
   * @return an atomic nucleus
   */
  public AtomicNucleus build(String name) {
    return new AtomicNucleus(name);
  }

}
