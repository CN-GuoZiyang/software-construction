package circularorbit;

import centralobject.AtomicNucleus;
import configuration.AtomConfiguration;
import configuration.Configuration;
import factory.AtomicNucleusFactory;
import factory.ElectronFactory;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import physicalobject.Electron;

/**.
 * the implement of an atom structure consisting of an atomic nucleus and some
 * electrons around the core
 *
 * @author Guo Ziyang
 */
public class AtomStructure extends AbstractOrbit<AtomicNucleus, Electron> {

  private String elementName;

  private AtomicNucleusFactory centralObjectFactory;

  private ElectronFactory electronFactory;

  private Logger logger = LoggerFactory.getLogger(AtomStructure.class);

  /**.
   * build an atom with a name
   *
   * @param elementName element name
   */
  public AtomStructure(String elementName) {
    super();
    this.elementName = elementName;
    centralObjectFactory = new AtomicNucleusFactory();
    addCentralObject(centralObjectFactory.build(elementName));
    electronFactory = new ElectronFactory();
  }

  /**.
   * build an atom from a file
   *
   * @param configuration the config
   * @throws IOException config file exception
   */
  public AtomStructure(Configuration configuration) {
    super();
    centralObjectFactory = new AtomicNucleusFactory();
    electronFactory = new ElectronFactory();
    buildFromConfiguration(configuration);
  }

  @Override
  public void buildFromConfiguration(Configuration configuration) {
    super.checkRep();
    AtomConfiguration atomConfiguration = (AtomConfiguration) configuration;
    elementName = atomConfiguration.getElementName();
    addCentralObject(centralObjectFactory.build(elementName));
    Integer numberOfTracks = atomConfiguration.getNumberOfTracks();
    for (int i = 1; i <= numberOfTracks; i++) {
      addTrack((double) i);
    }
    Map<Integer, Integer> numberOfElectron = atomConfiguration.getNumberOfElectron();
    for (Map.Entry<Integer, Integer> entry : numberOfElectron.entrySet()) {
      for (int i = 0; i < entry.getValue(); i++) {
        addPhysicalObject(electronFactory.build(), Double.valueOf(entry.getKey()));
      }
    }
    logger.info("successfully build from configuration");

  }

  /**.
   * get the name of the element
   *
   * @return the element's name
   */
  public String getElementName() {
    super.checkRep();
    return elementName;
  }

  @Override
  public Electron getObject(String name) {
    super.checkRep();
    logger.warn("Cannot invoke method getObject in Atomic Structure");
    return null;
  }

}
