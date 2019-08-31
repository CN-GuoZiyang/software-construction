package physicalobject;

/**.
 * the planet in the stellar system
 *
 * @author Guo Ziyang
 */
public class Planet extends PhysicalObjectWithSpeed {

  private final String name;
  private final String state;
  private final String color;
  private final Double radius;

  /**.
   * build a planet
   *
   * @param name name
   * @param state state
   * @param color color
   * @param radius radius
   * @param trackRadius track radius
   * @param speed speed
   * @param clockwise clockwise
   * @param startAngle startAngle
   */
  public Planet(final String name, final String state,
                final String color, final Double radius,
                final Double trackRadius, final Double speed,
                final Boolean clockwise, final Double startAngle) {
    super(speed, trackRadius, clockwise, startAngle);
    this.name = name;
    this.state = state;
    this.color = color;
    this.radius = radius;
  }

  /**.
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**.
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**.
   * @return the color
   */
  public String getColor() {
    return color;
  }

  /**.
   * @return the radius
   */
  public Double getRadius() {
    return new Double(radius);
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result;
    result = prime * result + ((name == null) ? 0 : name.hashCode())
            + ((state == null) ? 0 : state.hashCode())
            + ((color == null) ? 0 : color.hashCode())
            + ((radius == null) ? 0 : radius.hashCode())
            + ((trackRadius == null) ? 0 : trackRadius.hashCode())
            + ((speed == null) ? 0 : speed.hashCode())
            + ((clockwise == null) ? 0 : clockwise.hashCode())
            + ((startAngle == null) ? 0 : startAngle.hashCode());
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
    Planet other = (Planet) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (state == null) {
      if (other.state != null) {
        return false;
      }
    } else if (!state.equals(other.state)) {
      return false;
    }
    if (color == null) {
      if (other.color != null) {
        return false;
      }
    } else if (!color.equals(other.color)) {
      return false;
    }
    if (radius == null) {
      if (other.radius != null) {
        return false;
      }
    } else if (!radius.equals(other.radius)) {
      return false;
    }
    if (trackRadius == null) {
      if (other.trackRadius != null) {
        return false;
      }
    } else if (!trackRadius.equals(other.trackRadius)) {
      return false;
    }
    if (speed == null) {
      if (other.speed != null) {
        return false;
      }
    } else if (!speed.equals(other.speed)) {
      return false;
    }
    if (clockwise == null) {
      if (other.clockwise != null) {
        return false;
      }
    } else if (!clockwise.equals(other.clockwise)) {
      return false;
    }
    if (startAngle == null) {
      if (other.startAngle != null) {
        return false;
      }
    } else if (!startAngle.equals(other.startAngle)) {
      return false;
    }
    return true;
  }

}
