package circularorbit;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import configuration.Configuration;
import position.AbstractPosition;
import track.Track;

/**.
 * the original circular orbit interface that specify some common methods
 *
 * @param <L> the type of central object
 * @param <E> the type of the physical object around the central object
 * @author Guo Ziyang
 */
public interface CircularOrbit<L, E> {

  /**.
   * add a new track with specified radius
   *
   * @param radius the radius of the new track
   */
  void addTrack(Double radius);

  /**.
   * remove a existent track with given radius
   *
   * @param radius the radius of the track to be removed
   */
  void removeTrack(Double radius);

  /**.
   * add a central object to the central point
   *
   * @param object the central object to be added
   */
  void addCentralObject(L object);

  /**.
   * add a relation between a physical object and the central object
   *
   * @param object the given physical object
   * @param value  the value of the relation
   */
  void addCentralRelation(E object, Double value);

  /**.
   * add a relation between two physical objects
   *
   * @param object1 one of two objects
   * @param object2 one of two objects
   * @param value   the value of the relation
   */
  void addRelation(E object1, E object2, Double value);

  /**.
   * read configuration from a config
   *
   * @param configuration the configuration
   */
  void buildFromConfiguration(Configuration configuration);

  /**.
   * get the track on which the given object is
   *
   * @param object the given object
   * @return the target track or null if not found
   */
  Track getTrack(E object);

  /**.
   * get the track with given radius
   *
   * @param radius the given radius
   * @return the target track or null if not found
   */
  Track getTrack(Double radius);

  /**.
   * get the iterator tool of the system
   *
   * @return the iterator object
   */
  CircularOrbitIterator<E> iterator();

  /**.
   * get the set of tracks
   *
   * @return the set of tracks
   */
  Set<Track> getTracks();

  /**.
   * get the central object on the central point
   *
   * @return the central object
   */
  L getCentralObject();

  /**.
   * remove a specified physical object
   *
   * @param physicalObject the target physical object
   */
  void removePhysicalObject(E physicalObject);

  /**.
   * get all of the physical objects on the target track
   *
   * @param track the target track
   * @return the list of the physical objects on the track
   */
  List<E> getPhysicalObjects(Track track);

  /**.
   * get all of the physical objects
   *
   * @return the set containing all physical objects
   */
  Set<E> getPhysicalObjects();

  /**.
   * get the map of the physical object and its position
   *
   * @return the map of the physical object and its position
   */
  Map<E, AbstractPosition> getPositions();

  /**.
   * get the map of the physical relations
   *
   * @return the map of the physical relations
   */
  Map<E, Map<E, Double>> getRelations();

  /**.
   * get the physical object with specified name
   *
   * @param name the specified name
   * @return the physical object with specified name
   */
  E getObject(String name);

  /**.
   * get the map of the central relations
   *
   * @return the map of the central relations
   */
  Map<E, Double> getCentralRelations();

  Map<Double, Set<E>> getObjectOnTracks();
}
