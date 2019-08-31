package circularOrbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import position.CommonPosition;
import track.Track;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * the abstraction of the non-position-sensitive circular orbit
 *
 * @param <L> the type of the central object
 * @param <E> the type of the physical object around the central object
 * @author Guo Ziyang
 */
public abstract class OrbitWithoutPosition<L, E> extends ConcreteCircularOrbit<L, E> {

	protected Logger logger = LoggerFactory.getLogger(OrbitWithoutPosition.class);

	/**
	 * add a physical object to an existent object
	 *
	 * @param object the physical object
	 * @param radius the radius of the target track
	 */
	public void addPhysicalObject(E object, Double radius) {
		super.checkRep();
		Track track = getTrack(radius);
		if (track == null) {
			logger.error("Trying to add object to a non-existent track!");
			return;
		}
		positions.put(object, new CommonPosition(track));
		physicalRelations.put(object, new HashMap<>());
		logger.info("add a object {} to the track {}", object, track);
	}

	@Override
	public abstract void readFromFile(File file) throws IOException;

	/**
	 * transit a physical to one track to another
	 *
	 * @param object the physical object
	 * @param radius the radius of the target track
	 */
	public void transit(E object, Double radius) {
		super.checkRep();
		Track oldTrack = getTrack(object);
		if (oldTrack == null) {
			logger.error("Trying to transit a object of which track cannot be found!");
			return;
		}
		Track newTrack = getTrack(radius);
		if (newTrack == null) {
			logger.error("Trying to transit a object to a non-existent track!");
			return;
		}
		positions.get(object).setTrack(newTrack);
		logger.info("transit object {} from track {} to track {}", object, oldTrack, newTrack);
	}

	@Override
	public abstract E getObject(String name);

}
