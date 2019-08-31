package factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import physicalObject.Planet;

/**
 * the factory building planets
 *
 * @author Guo Ziyang
 */
public class PlanetFactory {

	private Logger logger = LoggerFactory.getLogger(PlanetFactory.class);

	/**
	 * build a planet
	 *
	 * @param name        name
	 * @param state       state
	 * @param color       color
	 * @param radius      radius
	 * @param trackRadius track radius
	 * @param speed       speed
	 * @param clockwise   clockwise
	 * @param startAngle  start angle
	 * @return a planet
	 */
	public Planet build(String name, String state, String color, Double radius, Double trackRadius, Double speed,
			Boolean clockwise, Double startAngle) {
		logger.info(
				"Build a planet with name {}, state {}, color {}, radius {}, trackRadius {}, speed {}, clockwise {}, startAngle {}",
				name, state, color, radius, trackRadius, speed, clockwise, startAngle);
		return new Planet(name, state, color, radius, trackRadius, speed, clockwise, startAngle);
	}

}
