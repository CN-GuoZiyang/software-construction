package apis;

/**.
 * the coordinate of a point
 *
 * @author Guo Ziyang
 */
public class Coordinate {
  double horizontal;
  double vertical;

  public Coordinate(double x, double y) {
    this.horizontal = x;
    this.vertical = y;
  }

  /**.
   * get the horizontal of the coordinate
   *
   * @return the horizontal of the coordinate
   */
  public double getHorizontal() {
    return horizontal;
  }

  /**.
   * get the vertical of the coordinate
   *
   * @return the vertical of the coordinate
   */
  public double getVertical() {
    return vertical;
  }

  /**.
   * set the horizontal of the coordinate
   *
   * @param horizontal the horizontal
   */
  public void setHorizontal(double horizontal) {
    this.horizontal = horizontal;
  }

  /**.
   * set the vertical of the coordinate
   *
   * @param vertical the vertical
   */
  public void setVertical(double vertical) {
    this.vertical = vertical;
  }

}

