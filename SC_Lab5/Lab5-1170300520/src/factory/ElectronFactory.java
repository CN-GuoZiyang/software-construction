package factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import physicalobject.Electron;

/**.
 * an electron factory
 *
 * @author Guo Ziyang
 */
public class ElectronFactory {

  /**.
   * build an electron
   *
   * @return an electron
   */
  public Electron build() {
    return new Electron();
  }

}
