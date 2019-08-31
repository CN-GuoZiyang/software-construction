package track;

/**.
 * a track with physical objects on it
 *
 * @author Guo Ziyang
 */
public class Track {
  protected final Double radius;

  public Track(final Double radius) {
    this.radius = radius;
  }

  public void checkRep() {
    assert radius != null;
  }

  /**.
   * get the radius of the track
   *
   * @return the radius of the track
   */
  public Double getRadius() {
    checkRep();
    return new Double(radius);
  }

  @Override
  public String toString() {
    return "Track (" + radius + ")";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result;
    result = prime * result + ((radius == null) ? 0 : radius.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Track other = (Track) obj;
    if (radius == null) {
      if (other.radius != null) {
        return false;
      }
    } else if (!radius.equals(other.radius)) {
      return false;
    }
    return true;
  }

}
