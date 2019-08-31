package configuration;

import adts.Monkey;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class V3Configurtion {

  private int ladderNumber;
  private int ladderLength;
  private Map<Integer, Set<Monkey>> monkeys = new HashMap<>();

  public void addMonkey(int time, int id, String direction, int speed) {
    if(!monkeys.containsKey(time)) {
      monkeys.put(time, new HashSet<>());
    }
    monkeys.get(time).add(new Monkey(id, direction, speed));
  }

  public int getLadderNumber() {
    return ladderNumber;
  }

  public void setLadderNumber(int ladderNumber) {
    this.ladderNumber = ladderNumber;
  }

  public int getLadderLength() {
    return ladderLength;
  }

  public void setLadderLength(int ladderLength) {
    this.ladderLength = ladderLength;
  }

  public Map<Integer, Set<Monkey>> getMonkeys() {
    return monkeys;
  }

  public void setMonkeys(Map<Integer, Set<Monkey>> monkeys) {
    this.monkeys = monkeys;
  }
}
