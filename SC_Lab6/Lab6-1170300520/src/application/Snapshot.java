package application;

public class Snapshot {

  private Integer[][] laddersSnapshot;

  public Snapshot(int number, int length) {
    laddersSnapshot = new Integer[number][length];
  }

  public void set(Integer value, int i, int j) {
    laddersSnapshot[i][j] = value;
  }

  public Integer[] get(int value) {
    return laddersSnapshot[value];
  }

}
