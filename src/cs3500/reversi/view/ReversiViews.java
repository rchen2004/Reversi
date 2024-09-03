package cs3500.reversi.view;

/**
 * Represents the GUI for the game of Reversi.
 */
public interface ReversiViews {

  /**
   * Adds a listener for view-related features.
   *
   * @param features The listener for view features.
   */
  void addFeatureListener(ViewFeatures features);

  /**
   * Adds a listener for provided views.
   */
  void addFeatureListeners(cs3500.reversi.provider.view.ViewFeatures features);


  /**
   * Displays or hides the Reversi game board.
   *
   * @param show If true, displays the game board; if false, hides it.
   */
  void display(boolean show);

  /**
   * Notifies the user that the last move was invalid.
   */
  void error();

  /**
   * Refreshes the game board.
   */
  void advance();

  /**
   * Sets the title frame of the game.
   * @param title The title of the frame.
   */
  void setTitle(String title);

  /**
   * Returns the title of the frame.
   */
  String getTitle();
}

