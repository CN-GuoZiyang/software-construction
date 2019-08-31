package configuration;

public class MonkeyConf {

  private int time;
  private int id;
  private String direction;
  private int speed;

  public MonkeyConf(int time, int id, String direction, int speed) {
    this.time = time;
    this.id = id;
    this.direction = direction;
    this.speed = speed;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }
}
