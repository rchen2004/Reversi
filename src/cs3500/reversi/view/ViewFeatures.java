package cs3500.reversi.view;

import cs3500.reversi.model.HexCell;

/**
 * Represents a set of features that can be triggered by the Reversi view.
 * SETUP IN PREPARATION FOR CONTROLLER.
 */
public interface ViewFeatures {

  /**
   * Signals the intention to quit the Reversi game.
   */
  void quit();

  /**
   * Signals the intention to make a move on the Reversi game board.
   */
  void movePiece(HexCell cell);

  /**
   * Signals the intention to pass the current move in the Reversi game.
   */
  void passMove();
}
