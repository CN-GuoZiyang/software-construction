package adts;

public class Ladder {

  private int id;
  private int length;
  private Monkey[] monkeys;

  public Ladder(int id, int length) {
    this.id = id;
    this.length = length;
    monkeys = new Monkey[length];
  }

  public boolean occupied() {
    for(int i = 0; i < monkeys.length; i ++) {
      if (monkeys[i] != null) {
        return true;
      }
    }
    return false;
  }

  public void addToLeft(Monkey monkey) {
    monkeys[0] = monkey;
  }

  public void addToRight(Monkey monkey) {
    monkeys[length - 1] = monkey;
  }

  public void moveToRight(Monkey monkey, int grid) {
    for (int i = 0; i < length; i ++) {
      if (monkey == monkeys[i]) {
        monkeys[i] = null;
        monkeys[i + grid] = monkey;
        break;
      }
    }
  }

  public void moveToLeft(Monkey monkey, int grid) {
    for (int i = 0; i < length; i ++) {
      if (monkey == monkeys[i]) {
        monkeys[i] = null;
        monkeys[i - grid] = monkey;
        break;
      }
    }
  }

  public void removeMonkey(Monkey monkey) {
    for (int i = 0; i < length; i ++) {
      if (monkey == monkeys[i]) {
        monkeys[i] = null;
        break;
      }
    }
  }

  public boolean hasMonkeyToLeft() {
    for (int i = 0; i < length; i ++) {
      if (monkeys[i] != null) {
        if (monkeys[i].getDirection().equals("R->L")) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean hasMonkeyToRight() {
    for (int i = 0; i < length; i ++) {
      if (monkeys[i] != null) {
        if (monkeys[i].getDirection().equals("L->R")) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean canAddToLeft() {
    return monkeys[0] == null;
  }

  public boolean canAddToRight() {
    return monkeys[length - 1] == null;
  }

  public int getMonkeyNumber() {
    int num = 0;
    for (int i = 0; i < length; i++) {
      if (monkeys[i] != null) {
        num++;
      }
    }
    return num;
  }

  public Monkey get(int i) {
    return monkeys[i];
  }

  public int getId() {
    return id;
  }

  public int get(Monkey monkey) {
    for(int i = 0; i < length; i ++) {
      if(monkey == monkeys[i]) {
        return i;
      }
    }
    return -1;
  }

  public int getRealSpeed(String direction) {
    if(!occupied()) {
      return Integer.MAX_VALUE;
    }
    if(getMonkeyNumber() == 1) {
      for(int i = 0; i < length; i ++) {
        if(monkeys[i] != null) {
          return monkeys[i].getSpeed();
        }
      }
    }
    Monkey temp1 = null;
    Monkey temp2 = null;
    switch (direction) {
      case "L->R":
        for(int i = 0; i < length; i++) {
          if(monkeys[i] != null) {
            if(temp1 != null) {
              temp2 = monkeys[i];
              break;
            }
            temp1 = monkeys[i];
          }
        }
        return get(temp2) - get(temp1) > temp1.getSpeed() ? temp1.getSpeed() : get(temp2) - get(temp1);
      case "R->L":
        for(int i = length - 1; i >= 0; i--) {
          if(monkeys[i] != null) {
            if(temp1 != null) {
              temp2 = monkeys[i];
              break;
            }
            temp1 = monkeys[i];
          }
        }
        return get(temp1) - get(temp2) > temp1.getSpeed() ? temp1.getSpeed() : get(temp1) - get(temp2);
      default:
        return -1;
    }
  }

}
