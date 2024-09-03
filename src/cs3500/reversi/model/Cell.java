package cs3500.reversi.model;

/**
 * The Cell interface represents a cell on the game board of a Reversi game.
 * Cells are identified by their q, r, and s coordinates in a hexagonal grid.
 * Each cell can also contain a game piece belonging to a player.
 */
public interface Cell {

  /**
   * Used to retrieve the q coordinate of a cell.
   *
   * @return int coordinate q
   */
  int getQ();

  /**
   * Used to retrieve the r coordinate of a cell.
   *
   * @return int coordinate r
   */
  int getR();

  /**
   * Used to retrieve the s coordinate of a cell.
   *
   * @return int coordinate s
   */
  int getS();

  /**
   * Sets the piece of this cell to a give piece.
   *
   * @param piece Represents a Player
   */
  void setPiece(Player piece);

  /**
   * Returns the current residing piece of this cell.
   *
   * @return Player residing piece.
   */
  Player getPiece();


}
