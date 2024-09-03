package cs3500.reversi.model;

import java.util.List;
import java.util.Map;

/**
 * Represents a read-only view of a Reversi game, providing access
 * to information about the game state.
 * This interface allows clients to query details such as the number of rows, the game board,
 * the current player, the game-over status, player scores, valid moves, and more.
 */
public interface ReadOnlyReversi {
  /**
   * Returns the number of rows for the current game.
   *
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the game is already over
   */
  int getRows();

  /**
   * Returns the game board for the current game.
   *
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the game is already over
   */
  Map<Integer, HexCell[]> getBoard();

  /**
   * Returns the current player enum representing a player or their piece.
   *
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the game is already over
   */
  Player getCurrentPlayer();

  /**
   * Determines if the board is filled, with no more moves.
   *
   * @return whether the game is over
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the game is already over
   */
  boolean isGameOver();

  /**
   * Returns the score (amount of pieces belonging to the given player).
   *
   * @param p the player
   * @return the score of the player
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the game is already over
   */
  int getScore(Player p);

  /**
   * Returns a list of valid moves for the current player.
   *
   * @return a list of valid moves for the current player
   */
  List<AxialCoordinate> getValidMoves();

  /**
   * For a given cell, uses all the previous helpers to check all the different directions for
   * a possible move.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @return valid move for given cell
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  boolean isValidMoves(int q, int r);

  /**
   * Finds the cell at the given coordinate.
   * @param q the q coordinate
   * @param r the r coordinate
   * @return the hex cell at the given coordinates
   */
  HexCell findCell(int q, int r);
}
