package position;

import track.Track;

/**
 * the position with angle
 * 
 * @author Guo Ziyang
 */
public class AnglePosition extends Position {
	
	private Double angle;
	
	public AnglePosition(Track track, Double angle) {
		super(track);
		this.setAngle(angle);
	}

	/**
	 * @return the angle
	 */
	public Double getAngle() {
		return angle;
	}

	/**
	 * set the angle of the position
	 * 
	 * @param angle the angle
	 */
	public void setAngle(Double angle) {
		this.angle = angle;
	}

}
