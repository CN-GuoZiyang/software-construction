package physicalObject;

/**
 * the physical object owning speed
 *
 * @author Guo Ziyang
 */
public class PhysicalObjectWithSpeed extends PhysicalObject {

	protected Double speed;
	protected Double trackRadius;
	protected Boolean clockwise;
	protected Double startAngle;

	public PhysicalObjectWithSpeed(Double speed, Double trackRadius, Boolean clockwise, Double startAngle) {
		this.speed = speed;
		this.trackRadius = trackRadius;
		this.clockwise = clockwise;
		this.startAngle = startAngle;
	}

	/**
	 * get the speed of the object
	 *
	 * @return the speed of the object
	 */
	public Double getSpeed() {
		return new Double(speed);
	}

	/**
	 * get the radius of the track
	 *
	 * @return the radius of the track
	 */
	public Double getTrackRadius() {
		return new Double(trackRadius);
	}

	/**
	 * whether the object moves clockwise
	 *
	 * @return whether the object moves clockwise
	 */
	public Boolean getClockwise() {
		return Boolean.valueOf(clockwise);
	}

	/**
	 * get the start angle of the object
	 *
	 * @return the start angle of the object
	 */
	public Double getStartAngle() {
		return new Double(startAngle);
	}

}
