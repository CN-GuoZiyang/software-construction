package circularOrbit;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import position.Position;
import track.Track;

/**
 * the original circular orbit interface that specify some common methods 
 * 
 * @author Guo Ziyang
 *
 * @param <L> the type of central object
 * @param <E> the type of the physical object around the central object
 */
public interface CircularOrbit<L, E> {
	
	/**
	 * add a new track with specified radius
	 * 
	 * @param radius the radius of the new track
	 */
	public void addTrack(Double radius);
	
	/**
	 * remove a existent track with given radius
	 * 
	 * @param radius the radius of the track to be removed
	 */
	public void removeTrack(Double radius);
	
	/**
	 * add a central object to the central point
	 * 
	 * @param object the central object to be added
	 */
	public void addCentralObject(L object);
	
	/**
	 * add a relation between a physical object and the central object
	 * 
	 * @param object the given physical object
	 */
	public void addCentralRelation(E object, Double value);
	
	/**
	 * add a relation between two physical objects
	 * 
	 * @param object1 one of two objects
	 * @param object2 one of two objects
	 */
	public void addRelation(E object1, E object2, Double value);
	
	/**
	 * read configuration from a config file
	 * 
	 * @param file the configuration file
	 */
	public void readFromFile(File file);
	
	/**
	 * get the track on which the given object is
	 * 
	 * @param object the given object
	 * @return the target track or null if not found
	 */
	public Track getTrack(E object);
	
	/**
	 * get the track with given radius
	 * 
	 * @param radius the given radius
	 * @return the target track or null if not found
	 */
	public Track getTrack(Double radius);
	
	/**
	 * get the iterator tool of the system
	 * 
	 * @return the iterator object
	 */
	public CircularOrbitIterator<E> iterator();
	
	/**
	 * get the set of tracks
	 * 
	 * @return the set of tracks
	 */
	public Set<Track> getTracks();
	
	/**
	 * get the central object on the central point
	 * 
	 * @return the central object
	 */
	public L getCentralObject();
	
	/**
	 * remove a specified physical object
	 * 
	 * @param physicalObject the target physical object
	 */
	public void removePhysicalObject(E physicalObject);
	
	/**
	 * get all of the physical objects on the target track
	 * 
	 * @param track the target track
	 * @return the list of the physical objects on the track
	 */
	public List<E> getPhysicalObjects(Track track);

	/**
	 * get the map of the physical object and its position
	 * 
	 * @return the map of the physical object and its position
	 */
	public Map<E, Position> getPositions();
	
	/**
	 * get the map of the physical relations
	 * 
	 * @return the map of the physical relations
	 */
	public Map<E, Map<E, Double>> getRelations();
	
	/**
	 * get all of the physical objects
	 * 
	 * @return the set containing all physical objects
	 */
	public Set<E> getPhysicalObjects();
	
	/**
	 * get the physical object with specified name
	 * 
	 * @param name the specified name
	 * @return the physical object with specified name 
	 */
	public E getObject(String name);
	
	/**
	 * get the map of the central relations
	 * 
	 * @return the map of the central relations
	 */
	public Map<E, Double> getCentralRelations();
}
