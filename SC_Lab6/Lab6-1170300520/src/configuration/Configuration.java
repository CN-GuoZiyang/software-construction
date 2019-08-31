package configuration;

public class Configuration {

  private int ladderNumber;
  private int ladderLength;
  private int interval;
  private int monkeyNumber;
  private int monkeyPerTime;
  private int maxSpeed;

  public Configuration() {}

  public int getLadderNumber() {
    return ladderNumber;
  }

  public int getLadderLength() {
    return ladderLength;
  }

  public int getInterval() {
    return interval;
  }

  public int getMonkeyNumber() {
    return monkeyNumber;
  }

  public int getMonkeyPerTime() {
    return monkeyPerTime;
  }

  public int getMaxSpeed() {
    return maxSpeed;
  }

  public void setLadderNumber(int ladderNumber) {
    this.ladderNumber = ladderNumber;
  }

  public void setLadderLength(int ladderLength) {
    this.ladderLength = ladderLength;
  }

  public void setInterval(int interval) {
    this.interval = interval;
  }

  public void setMonkeyNumber(int monkeyNumber) {
    this.monkeyNumber = monkeyNumber;
  }

  public void setMonkeyPerTime(int monkeyPerTime) {
    this.monkeyPerTime = monkeyPerTime;
  }

  public void setMaxSpeed(int maxSpeed) {
    this.maxSpeed = maxSpeed;
  }

  @Override
  public String toString() {
    return "Configuration{" +
            "ladderNumber=" + ladderNumber +
            ", ladderLength=" + ladderLength +
            ", interval=" + interval +
            ", monkeyNumber=" + monkeyNumber +
            ", monkeyPerTime=" + monkeyPerTime +
            ", maxSpeed=" + maxSpeed +
            '}';
  }
}
