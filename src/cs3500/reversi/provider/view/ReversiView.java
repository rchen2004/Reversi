package cs3500.reversi.provider.view;

import cs3500.reversi.provider.view.ViewFeatures;

/**
 * The ReversiView interface represents the view component in the Reversi game.
 * Implementations of this interface are responsible for presenting the game to the user.
 */
public interface ReversiView {

  /**
   * Sets the visibility of the Reversi view.
   *
   * @param visible {@code true} to make the view visible, {@code false} to hide it.
   */
  void display(boolean visible);

  /**
   * Updates the view based on the current state of the Reversi model.
   */
  void updateView();

  /**
   * Displays a message to the user, such as game over, winner, or error messages.
   *
   * @param message The message to display.
   */
  void showMessage(String message);

  /**
   * Adds a feature listener to the view.
   *
   * @param features The feature listener to add.
   */
  void addFeatureListener(ViewFeatures features);

  /**
   * Sets the title of the view panel.
   *
   * @param title The title to set for the panel.
   */
  void setPanelTitle(String title);

  /**
   * Sets the position of the view panel.
   *
   * @param x The x-coordinate for the panel position.
   * @param y The y-coordinate for the panel position.
   */
  void setPanelPosition(int x, int y);

  /**
   * Gets the width of the view panel.
   *
   * @return The width of the panel.
   */
  int getPanelWidth();

  /**
   * Sets the focusability of the view.
   *
   * @param focusable {@code true} to set the view as focusable, {@code false} otherwise.
   */
  void focus(boolean focusable);
}
