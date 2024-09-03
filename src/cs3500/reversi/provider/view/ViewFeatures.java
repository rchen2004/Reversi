package cs3500.reversi.provider.view;

import cs3500.reversi.provider.model.Coordinate;

/**
 * The ViewFeatures interface defines the actions that a user can perform in the game view.
 * Implementing classes or interfaces should handle these actions to interact with the game
 * state or perform game-related actions.
 */
public interface ViewFeatures {

  /**
   * Handles the action of clicking on a hexagon in the game view.
   *
   * @param hexagon The Hexagon object representing the hexagon that was clicked.
   */
  void clickHex(Hexagon hexagon);

  /**
   * Handles the action of making a move on the game board.
   *
   * @param coordinates The Coordinate object representing the coordinates of the move.
   */
  void move(Coordinate coordinates);

  /**
   * Handles the action of passing the turn in the game.
   * This method is called when a player chooses to pass their turn.
   */
  void pass();

  /**
   * Handles the action of quitting the game.
   * This method is called when a user chooses to exit or quit the game.
   */
  void quit();
}
