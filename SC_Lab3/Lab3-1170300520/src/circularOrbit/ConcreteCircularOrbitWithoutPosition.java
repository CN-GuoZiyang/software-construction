package circularOrbit;

import java.io.File;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import position.CommonPosition;
import track.Track;

/**
 * the abstraction of the non-position-sensitive circular orbit
 * 
 * @author Guo Ziyang
 *
 * @param <L> the type of the central object
 * @param <E> the type of the physical object around the central object
 */
public abstract class ConcreteCircularOrbitWithoutPosition<L, E> extends ConcreteCircularOrbit<L, E> {
		
	protected Logger logger = LoggerFactory.getLogger(ConcreteCircularOrbitWithoutPosition.class);
	
	public ConcreteCircularOrbitWithoutPosition() {
		super();
	}
	
	/**
	 * add a physical object to an existent object
	 * 
	 * @param object the physical object
	 * @param radius the radius of the target track
	 */
	public void addPhysicalObject(E object, Double radius) {
		Track track = getTrack(radius);
		if(track == null) {
			logger.error("Trying to add object to a non-existent track!");
			return;
		}
		positions.put(object, new CommonPosition(track));
		physicalRelations.put(object, new HashMap<>());
		logger.info("add a object {} to the track {}", object, track);
	}
	
	@Override
	public abstract void readFromFile(File file);
	
	/**
	 * transit a physical to one track to another 
	 * 
	 * @param object the physical object
	 * @param radius the radius of the target track
	 */
	public void transit(E object, Double radius) {
		Track oldTrack = getTrack(object);
		if(oldTrack == null) {
			logger.error("Trying to transit a object of which track cannot be found!");
			return;
		}
		Track newTrack = getTrack(radius);
		if(newTrack == null) {
			logger.error("Trying to transit a object to a non-existent track!");
			return;
		}
		positions.get(object).setTrack(newTrack);
		logger.info("transit object {} from track {} to track {}", object, oldTrack, newTrack);
	}
	
	@Override
	public abstract E getObject(String name);
	
}
