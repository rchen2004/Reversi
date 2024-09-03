package cs3500.reversi.provider.view;

import cs3500.reversi.view.ReversiViews;
import cs3500.reversi.view.ViewFeatures;

/**
 * The AdapterView class adapts the providers ReversiView class to our ReversiViews interface.
 */
public class AdapterView implements ReversiViews {
  private final ReversiView view;

  /**
   * Constructs an adapter for the providers ReversiView class.
   * @param view the providers ReversiView class
   */
  public AdapterView(ReversiView view) {
    this.view = view;
  }

  /**
   * Adds the given listener to the view's list of listeners.
   * @param features The listener for view features.
   */
  @Override
  public void addFeatureListener(ViewFeatures features) {
    // not being used
  }

  /**
   * Adds the given listener to the view's list of listeners.
   * @param features The listener for view features.
   */
  @Override
  public void addFeatureListeners(cs3500.reversi.provider.view.ViewFeatures features) {
    this.view.addFeatureListener(features);
  }

  /**
   * Displays the game board.
   * @param show If true, displays the game board; if false, hides it.
   */
  @Override
  public void display(boolean show) {
    // not being used
  }

  /**
   * Error message.
   */
  @Override
  public void error() {
    // not being used
  }

  /**
   * Updates the view.
   */
  @Override
  public void advance() {
    this.view.updateView();
  }

  /**
   * Sets the title of the frame.
   * @param title The title of the frame.
   */
  @Override
  public void setTitle(String title) {
    // not being used
  }

  /**
   * Gets the title of the frame.
   * @return The title of the frame.
   */
  @Override
  public String getTitle() {
    return null;
  }
}
