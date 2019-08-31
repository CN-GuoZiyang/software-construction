package position;

import track.Track;

/**.
 * the position with a track
 *
 * @author Guo Ziyang
 */
public abstract class AbstractPosition {

  private Track track;

  public AbstractPosition(Track track) {
    this.setTrack(track);
  }

  public void checkRep() {
    assert track != null;
  }

  /**.
   * get the track
   *
   * @return the track
   */
  public Track getTrack() {
    checkRep();
    return track;
  }

  /**.
   * set the track of the position
   *
   * @param track the track of the position
   */
  public void setTrack(Track track) {
    this.track = track;
    checkRep();
  }

}
