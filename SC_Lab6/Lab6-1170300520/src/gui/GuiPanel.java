package gui;

import adts.Ladder;
import adts.Monkey;
import application.Application;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GuiPanel extends JPanel {

  private static final int LENGTH = 800;
  private static final int WIDTH = 600;

  private static final int LADDER_LENGTH = LENGTH - 80;
  private static final int GRID_LENGTH = LADDER_LENGTH / Application.configuration.getLadderLength();

  private List<Ladder> ladders;

  private JLabel timeLabel;

  public GuiPanel() {
    ladders = Application.LADDERS;
    timeLabel = new JLabel("当前时刻: 0");
    timeLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
    add(timeLabel);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2 = (Graphics2D) g;
    int inner = (WIDTH - 80) / (ladders.size() * 2);
    for (int i = 0; i < ladders.size(); i ++) {
      int upper = 20 + inner + i * (WIDTH - 80) / ladders.size();
      int lower = 20 + inner + GRID_LENGTH + i * (WIDTH - 80) / ladders.size();
      g2.drawLine(40, upper, LENGTH - 40, upper);
      g2.drawLine(40, lower, LENGTH - 40, lower);
      for(int j = 0; j < Application.configuration.getLadderLength() - 1; j ++) {
        g2.drawLine(40 + GRID_LENGTH * (j + 1), upper, 40 + GRID_LENGTH * (j + 1), lower);
      }
    }
    for(int i = 0; i < ladders.size(); i ++) {
      Ladder ladder = ladders.get(i);
      int stringy = 20 + inner + i * (WIDTH - 80) / ladders.size() + GRID_LENGTH * 2 / 3;
      for(int j = 0; j < Application.configuration.getLadderLength(); j ++) {
        Monkey monkey = ladder.get(j);
        if(monkey != null) {
          g2.drawString(String.valueOf(monkey.getId()),40 + GRID_LENGTH / 4 + GRID_LENGTH * j,stringy);
        }
      }
    }
    timeLabel.setText("当前时刻: " + Application.time);
  }

}
