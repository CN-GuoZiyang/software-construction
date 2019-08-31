package circularOrbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import position.AnglePosition;
import track.Track;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * the abstraction of the position-sensitive circular orbit
 *
 * @param <L> the type of the central object
 * @param <E> the type of the physical object around the central object
 * @author Guo Ziyang
 */
public abstract class OrbitWithPosition<L, E> extends ConcreteCircularOrbit<L, E> {

	protected Logger logger = LoggerFactory.getLogger(OrbitWithPosition.class);

	/**
	 * add a physical to the specified position of the target track
	 *
	 * @param object   the physical object to be added
	 * @param radius   the radius of the target track
	 * @param position the polar angel of the target track
	 */
	public void addPhysicalObject(E object, Double radius, Double position) {
		super.checkRep();
		Track track = getTrack(radius);
		if (track == null) {
			logger.error("Trying to add object to a non-existent track!");
			return;
		}
		positions.put(object, new AnglePosition(track, position));
		physicalRelations.put(object, new HashMap<>());
		logger.info("add a object {} to the track {}", object, track);
	}

	@Override
	public abstract void readFromFile(File file) throws IOException;

	/**
	 * move a physical object from one position to another on the same track
	 *
	 * @param object the physical object to be moved
	 * @param sitha  the new polar angel
	 */
	public void move(E object, double sitha) {
		super.checkRep();
		Track track = getTrack(object);
		if (track == null) {
			logger.error("Trying to move a object of which track cannot be found!");
			return;
		}
		((AnglePosition) positions.get(object)).setAngle(sitha);
		logger.info("move the object {} on the track {} to {}", object, track, sitha);
	}

	@Override
	public abstract E getObject(String name);

}
