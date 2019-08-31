package track;

/**
 * a track with physical objects on it
 * 
 * @author Guo Ziyang
 */
public class Track {
	protected final Double radius;
	
	public Track(final Double radius) {
		this.radius = radius;
	}
	
	/**
	 * get the radius of the track
	 * 
	 * @return the radius of the track
	 */
	public Double getRadius() {
		return radius;
	}
}
