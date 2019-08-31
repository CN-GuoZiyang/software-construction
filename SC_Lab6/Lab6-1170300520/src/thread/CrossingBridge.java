package thread;

import adts.Ladder;
import adts.Monkey;
import application.Application;

import java.util.*;

/**
 * @author Guo Ziyang
 */
public class CrossingBridge implements Runnable {

  private Monkey monkey;
  private boolean suspend = false;
  private boolean stop = false;
  private boolean onLadder = false;
  private Ladder currentLadder;

  public int strategy;

  public CrossingBridge(Monkey monkey) {
    this.monkey = monkey;
    //strategy = new Random().nextInt(3);
    //strategy = 2;
    strategy = Application.commonStrategy;
  }

  private void suspendThread() {
    suspend = true;
  }

  public synchronized void continueThread() {
    suspend = false;
    notify();
  }

  @Override
  public void run() {
    while (true) {
      suspendThread();
      try {
        synchronized (this) {
          if(suspend) {
            wait();
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      synchronized (Application.LADDERS) {
        if (!onLadder) {
          switch (strategy) {
            case 0:
              strategy1();
              break;
            case 1:
              strategy2();
              break;
            case 2:
              strategy3();
              break;
            default:
              break;
          }
        }
        move();
        Application.LADDERS.notifyAll();
        if (stop) {
          currentLadder.removeMonkey(monkey);
          Application.threadsToRemove.add(this);
          Application.resultMap.put(monkey.getId(), Application.time);
          break;
        }
      }
    }
  }

  private void move() {
    if (!onLadder) {
      return;
    }
    Integer[] ladderSnapshot = Application.SNAPSHOT.get(currentLadder.getId());
    int tempIndex = -1;
    for (int i = 0; i < ladderSnapshot.length; i++) {
      if (monkey == currentLadder.get(i)) {
        tempIndex = i;
        break;
      }
    }
    int grid = -1;
    switch (monkey.getDirection()) {
      case "L->R":
        for (int i = 1; i <= monkey.getSpeed(); i++) {
          if (tempIndex + i >= ladderSnapshot.length) {
            stop = true;
            return;
          }
          if (ladderSnapshot[tempIndex + i] != null) {
            grid = i - 1;
            break;
          }
          if (currentLadder.get(tempIndex + i) != null) {
            grid = i - 1;
            break;
          }
        }
        if (grid == -1) {
          grid = monkey.getSpeed();
        }
        System.out.printf("Monkey %-3d on ladder " + currentLadder.getId() + " current grid=" + tempIndex + " move to right=" + grid + "\n", monkey.getId());
        currentLadder.moveToRight(monkey, grid);
        break;
      case "R->L":
        for (int i = 1; i <= monkey.getSpeed(); i++) {
          if (tempIndex - i < 0) {
            stop = true;
            return;
          }
          if (ladderSnapshot[tempIndex - i] != null) {
            grid = i - 1;
            break;
          }
          if (currentLadder.get(tempIndex - i) != null) {
            grid = i - 1;
            break;
          }
        }
        if (grid == -1) {
          grid = monkey.getSpeed();
        }
        System.out.printf("Monkey %-3d on ladder " + currentLadder.getId() + " current grid=" + tempIndex + " move to left=" + grid + "\n", monkey.getId());
        currentLadder.moveToLeft(monkey, grid);
        break;
      default:
        break;
    }
  }

  private void strategy1() {
    List<Ladder> candidates = new ArrayList<>();
    for (Ladder ladder : Application.LADDERS) {
      if (!ladder.occupied() && !Application.occupationRecord.get(ladder.getId())) {
        candidates.add(ladder);
      }
    }
    if (!candidates.isEmpty()) {
      currentLadder = candidates.get(new Random().nextInt(candidates.size()));
      addToLadder();
      return;
    }
    candidates = filterOpposite();
    if (!candidates.isEmpty()) {
      currentLadder = candidates.get(new Random().nextInt(candidates.size()));
      addToLadder();
    }

  }

  private void strategy2() {
    List<Ladder> candidates = filterOpposite();
    if(!candidates.isEmpty()) {
      if(candidates.size() == 1) {
        currentLadder = candidates.get(0);
        addToLadder();
        return;
      }
      int maxSpeed = -1;
      int maxIndex = -1;
      for (int i = 0; i < candidates.size(); i++) {
        Ladder ladder = candidates.get(i);
        if (!ladder.occupied()) {
          currentLadder = ladder;
          addToLadder();
          return;
        }
        if (maxSpeed <= ladder.getRealSpeed(monkey.getDirection())) {
          maxSpeed = ladder.getRealSpeed(monkey.getDirection());
          maxIndex = i;
        }
      }
      currentLadder = candidates.get(maxIndex);
      addToLadder();
    }
  }

  /**
   * 选择速度大于猴子速度的梯子中速度最小的梯子
   */
  private void strategy3() {
    List<Ladder> candidates = filterOpposite();
    if (!candidates.isEmpty()) {
      if(candidates.size() == 1) {
        currentLadder = candidates.get(0);
        addToLadder();
        return;
      }
      Set<Ladder> laddersToRemove = new HashSet<>();
      for(Ladder ladder : candidates) {
        if(ladder.getRealSpeed(monkey.getDirection()) < monkey.getSpeed()) {
          laddersToRemove.add(ladder);
        }
      }
      if(laddersToRemove.size() == candidates.size()) {
        int maxSpeed = -1;
        int maxIndex = -1;
        for (int i = 0; i < candidates.size(); i++) {
          Ladder ladder = candidates.get(i);
          if (!ladder.occupied()) {
            currentLadder = ladder;
            addToLadder();
            return;
          }
          if (maxSpeed <= ladder.getRealSpeed(monkey.getDirection())) {
            maxSpeed = ladder.getRealSpeed(monkey.getDirection());
            maxIndex = i;
          }
        }
        currentLadder = candidates.get(maxIndex);
        addToLadder();
      } else {
        candidates.removeAll(laddersToRemove);
        if(candidates.size() == 1) {
          currentLadder = candidates.get(0);
          addToLadder();
          return;
        }
        int minSpeed = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < candidates.size(); i++) {
          Ladder ladder = candidates.get(i);
          if (!ladder.occupied()) {
            currentLadder = ladder;
            addToLadder();
            return;
          }
          if (minSpeed >= ladder.getRealSpeed(monkey.getDirection())) {
            minSpeed = ladder.getRealSpeed(monkey.getDirection());
            minIndex = i;
          }
        }
        currentLadder = candidates.get(minIndex);
        addToLadder();
      }
    }
  }

  private List<Ladder> filterOpposite() {
    List<Ladder> candidates = new ArrayList<>();
    String direction = monkey.getDirection();
    for (Ladder ladder : Application.LADDERS) {
      switch (direction) {
        case "L->R":
          if (ladder.canAddToLeft() && !ladder.hasMonkeyToLeft() && !Application.occupationRecord.get(ladder.getId())) {
            candidates.add(ladder);
          }
          break;
        case "R->L":
          if (ladder.canAddToRight() && !ladder.hasMonkeyToRight() && !Application.occupationRecord.get(ladder.getId())) {
            candidates.add(ladder);
          }
          break;
        default:
          break;
      }
    }
    return candidates;
  }

  private void addToLadder() {
    System.out.printf("monkey %-3d is on ladder " + currentLadder.getId() + "\n", monkey.getId());
    Application.occupationRecord.put(currentLadder.getId(), true);
    switch (monkey.getDirection()) {
      case "L->R":
        currentLadder.addToLeft(monkey);
        onLadder = true;
        break;
      case "R->L":
        currentLadder.addToRight(monkey);
        onLadder = true;
        break;
      default:
        break;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CrossingBridge that = (CrossingBridge) o;
    return strategy == that.strategy &&
            monkey.equals(that.monkey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(monkey, strategy);
  }
}
