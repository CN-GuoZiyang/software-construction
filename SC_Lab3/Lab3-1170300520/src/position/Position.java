package position;

import track.Track;

/**
 * the position with a track
 * 
 * @author Guo Ziyang
 */
public abstract class Position {
	
	private Track track;
	
	public Position(Track track) {
		this.setTrack(track);
	}

	/**
	 * @return the track
	 */
	public Track getTrack() {
		return track;
	}
	
	/**
	 * set the track of the position
	 * 
	 * @param track the track of the position
	 */
	public void setTrack(Track track) {
		this.track = track;
	}
	
}
