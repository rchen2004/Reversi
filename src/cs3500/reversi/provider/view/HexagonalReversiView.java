package cs3500.reversi.provider.view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.reversi.provider.model.ROReversiModel;

/**
 * The HexagonalReversiView class represents the main view for the Hexagonal Reversi game.
 * It extends JFrame and implements the {@code ReversiView} interface.
 */
public class HexagonalReversiView extends JFrame implements ReversiView {
  private final HexagonalReversiPanel panel;

  /**
   * Constructs a HexagonalReversiView with the specified Reversi model.
   *
   * @param model The Reversi model to be displayed.
   */
  public HexagonalReversiView(ROReversiModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Reversi");
    this.setBackground(Color.DARK_GRAY);

    this.panel = new HexagonalReversiPanel(model);
    this.add(panel);

    this.pack();
    this.setMinimumSize(new Dimension(150, 150));
    this.setVisible(true);
  }

  @Override
  public void display(boolean visible) {
    this.setVisible(visible);
  }

  @Override
  public void updateView() {
    panel.repaint();
  }

  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.panel.addFeaturesListener(features);
  }

  @Override
  public void setPanelTitle(String title) {
    this.setTitle(title);
  }

  @Override
  public void setPanelPosition(int x, int y) {
    this.setLocation(x, y);
  }

  @Override
  public int getPanelWidth() {
    return this.getWidth();
  }

  @Override
  public void focus(boolean focusable) {
    this.setFocusable(focusable);
  }
}
