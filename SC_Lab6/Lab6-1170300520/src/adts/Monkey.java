package adts;

public class Monkey {

  private int id;
  private String direction;
  private int speed;

  public Monkey(int id, String direction, int speed) {
    this.id = id;
    this.direction = direction;
    this.speed = speed;
  }

  public int getId() {
    return id;
  }

  public String getDirection() {
    return direction;
  }

  public int getSpeed() {
    return speed;
  }

}
