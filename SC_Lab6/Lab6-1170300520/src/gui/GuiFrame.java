package gui;

import javax.swing.*;

public class GuiFrame extends JFrame {

  public GuiFrame(JPanel mainPanel) {
    setTitle("Visualization");
    setSize(800, 600);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setContentPane(mainPanel);
    setVisible(true);
  }

}
