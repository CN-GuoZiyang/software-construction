package application;

import adts.Ladder;
import adts.Monkey;
import configuration.V3ConfReader;
import configuration.V3Configurtion;
import gui.GuiFrame;
import gui.GuiPanel;
import thread.CrossingBridge;

import javax.swing.*;
import java.util.*;

/**
 * @author Guo Ziyang
 */
public class Application {

  private static JFrame frame;

  public static V3Configurtion configuration;
  public static final List<Ladder> LADDERS = new ArrayList<>();
  public static Snapshot SNAPSHOT = null;
  static Set<CrossingBridge> threads = new HashSet<>();
  public static Set<CrossingBridge> threadsToRemove = new HashSet<>();
  public static Map<Integer, Boolean> occupationRecord = new HashMap<>();
  public static int time = 0;
  public static Map<Integer, Integer> startMap = new HashMap<>();
  public static Map<Integer, Integer> resultMap = new HashMap<>();
  public static int commonStrategy = -1;

  public static void main(String[] args) {
    System.out.println("请输入文件路径: ");
    String fileStr = new Scanner(System.in).nextLine();
    configuration = new V3ConfReader(fileStr).getConfiguration();
    initLadders();
    MonkeyGenerator generator = new MonkeyGenerator();
    System.out.println(configuration);
    System.out.println("请输入选择的策略：");
    commonStrategy = new Scanner(System.in).nextInt() - 1;
    GuiPanel panel = new GuiPanel();
    frame = new GuiFrame(panel);
    while (true) {
      saveSnapshot();
      generator.generateMonkeys();
      for(Ladder ladder : LADDERS) {
        occupationRecord.put(ladder.getId(), false);
      }
      for (CrossingBridge thread : threads) {
        thread.continueThread();
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      panel.repaint();
      threads.removeAll(threadsToRemove);
      threadsToRemove.clear();
      if (generator.isFinished() && threads.isEmpty()) {
        break;
      }
      time++;
    }
    showResult();
  }

  private static void initLadders() {
    int ladderLength = configuration.getLadderLength();
    for (int i = 0; i < configuration.getLadderNumber(); i ++) {
      LADDERS.add(new Ladder(i, ladderLength));
    }
    SNAPSHOT = new Snapshot(configuration.getLadderNumber(), configuration.getLadderLength());
  }

  private static void saveSnapshot() {
    for (int i = 0; i < LADDERS.size(); i ++) {
      Ladder ladder = LADDERS.get(i);
      for (int j = 0; j < configuration.getLadderLength(); j ++) {
        Monkey monkey = ladder.get(j);
        if(monkey != null) {
          SNAPSHOT.set(monkey.getId(), i, j);
          System.out.printf("+%-3d ", monkey.getId());
        } else {
          SNAPSHOT.set(null, i, j);
          System.out.print("-1   ");
        }
      }
      System.out.println();
    }
  }

  private static void showResult() {
    double io = (double) configuration.getMonkeys().size() / time;
    double fairness = 0;
    int pair = 0;
    for (int i = 1; i < configuration.getMonkeys().size(); i++) {
      int za = resultMap.get(i);
      int starta = startMap.get(i);
      for (int j = i + 1; j <= configuration.getMonkeys().size(); j++) {
        int zb = resultMap.get(j);
        int startb = startMap.get(j);
        fairness += ((startb - starta) * (zb - za)) >= 0 ? 1 : -1;
        pair++;
      }
    }
    fairness = fairness / pair;
    System.out.println("吞吐率" + io + ", 公平性" + fairness);
    JOptionPane.showMessageDialog(frame, "总用时" + time + "\n吞吐率" + io + "\n公平性" + fairness, "过河结束", JOptionPane.INFORMATION_MESSAGE);
  }

}
