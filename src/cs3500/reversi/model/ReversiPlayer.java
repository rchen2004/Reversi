package cs3500.reversi.model;

/**
 * Represents a player whether it is human or ai in the game of Reversi.
 */
public interface ReversiPlayer {

  /**
   * Chooses a move for this player.
   * @param cell The cell on which the move is made.
   */
  void chooseMove(HexCell cell);

  /**
   * Passes the turn for this player.
   */
  void pass();

  /**
   * Returns the coordinates of the move chosen by this player.
   * @return The coordinates of the move chosen by this player.
   */
  AxialCoordinate pieceToMove();

  /**
   * Returns the piece color of this player.
   * @return  The piece color of this player.
   */
  Player getColor();

  /**
   * Used only for AI, it does the moves for the AI.
   */
  void myTurn();
}