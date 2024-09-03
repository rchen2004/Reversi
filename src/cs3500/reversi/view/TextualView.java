package cs3500.reversi.view;

/**
 * A marker interface for all text-based views, to be used in the Reversi game.
 */
public interface TextualView {

  /**
   * Displays the Reversi Model as a formatted string.
   * Overrides built-in toString.
   */
  String toString();
}