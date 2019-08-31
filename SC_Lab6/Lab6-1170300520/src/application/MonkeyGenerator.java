package application;

import adts.Monkey;
import thread.CrossingBridge;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MonkeyGenerator {

  private final int total;
  private int current = 1;
  private Map<Integer, Set<Monkey>> monkeyMap;

  public boolean isFinished() {
    return finished;
  }

  private boolean finished = false;

  public MonkeyGenerator() {
    this.total = Application.configuration.getMonkeys().size();
    monkeyMap = Application.configuration.getMonkeys();
  }

  public void generateMonkeys() {
    if (finished) {
      return;
    }
    int time = Application.time;
    if(monkeyMap.containsKey(time)) {
      for(Monkey monkey : monkeyMap.get(time)) {
        Application.startMap.put(monkey.getId(), time);
        CrossingBridge thread = new CrossingBridge(monkey);
        Application.threads.add(thread);
        new Thread(thread).start();
        current ++;
      }
    }
    if(current > total) {
      finished = true;
    }
  }

}
