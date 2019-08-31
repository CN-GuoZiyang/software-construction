package factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.Track;

/**
 * the abstraction of the track factory
 *
 * @author Guo Ziyang
 */
public class TrackFactory {

	private Logger logger = LoggerFactory.getLogger(TrackFactory.class);

	/**
	 * build a track of given radius
	 *
	 * @param radius the radius of the target track
	 * @return the track with the radius
	 */
	public Track build(Double radius) {
		logger.info("build a track with radius {}", radius);
		return new Track(radius);
	}

}
