package cs3500.reversi.view;

import javax.swing.JFrame;

import cs3500.reversi.model.ReadOnlyReversi;

/**
 * Represents a simple view for the game of Reversi using Swing.
 */
public class SimpleReversiView extends JFrame implements ReversiViews {

  private final JReversiPanel panel;

  /**
   * Constructs a SimpleReversiView for the given ReadOnlyReversi model.
   * Defaults the coordinate width and height to 80 multiplied by the size of the
   * hexagon board based on row size.
   *
   * @param reversi The ReadOnlyReversi model to be displayed.
   */
  public SimpleReversiView(ReadOnlyReversi reversi, String title) {
    this.panel = new JReversiPanel(reversi);
    this.add(panel);
    this.setTitle(title);
    this.setSize(80 * reversi.getRows(), 80 * reversi.getRows());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Adds a listener for view-related features.
   *
   * @param features The listener for view features.
   */
  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.panel.addFeaturesListener(features);
  }

  /**
   * Adds a listener for provided views.
   * @param features The listener for view features.
   */
  @Override
  public void addFeatureListeners(cs3500.reversi.provider.view.ViewFeatures features) {
    // not being used
  }

  /**
   * Displays or hides the Reversi game board.
   *
   * @param show If true, displays the game board; if false, hides it.
   */
  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  /**
   * Notifies the user that the last move was invalid.
   */
  @Override
  public void error() {
    // waiting for controller
  }

  /**
   * Refreshes the game board.
   */
  @Override
  public void advance() {
    this.panel.advance();
  }

  /**
   * Sets the title frame of the game.
   * @param title The title of the frame.
   */
  @Override
  public void setTitle(String title) {
    super.setTitle(title);
  }
}

