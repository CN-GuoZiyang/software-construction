package circularorbit;

import centralobject.Stellar;
import configuration.Configuration;
import configuration.StellarConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import physicalobject.Planet;

/**.
 * the implement of a stellar system consisting of a stellar and many planets
 *
 * @author Guo Ziyang
 */
public class StellarSystem extends AbstractOrbitWithPosition<Stellar, Planet> {

  private Map<String, Planet> nameMap;

  /**.
   * build a stellar system using a configuration
   *
   * @param configuration the specified configuration
   */
  public StellarSystem(Configuration configuration) {
    super();
    nameMap = new HashMap<>();
    buildFromConfiguration(configuration);
  }

  @Override
  public void addPhysicalObject(Planet object, Double radius, Double position) {
    super.addPhysicalObject(object, radius, position);
    nameMap.put(object.getName(), object);
  }

  @Override
  public void removePhysicalObject(Planet physicalObject) {
    super.removePhysicalObject(physicalObject);
    nameMap.remove(physicalObject.getName());
  }

  @Override
  public void buildFromConfiguration(Configuration configuration) {
    super.checkRep();
    StellarConfiguration stellarConfiguration = (StellarConfiguration) configuration;
    addCentralObject(stellarConfiguration.getStellar());
    Set<Planet> planets = stellarConfiguration.getPlanets();
    for (Planet planet : planets) {
      if (getTrack(planet.getTrackRadius()) == null) {
        addTrack(planet.getTrackRadius());
      }
      addPhysicalObject(planet, planet.getTrackRadius(), planet.getStartAngle());
    }
    logger.info("successfully build from configuration");
  }

  @Override
  public Planet getObject(String name) {
    super.checkRep();
    return nameMap.get(name);
  }

}
