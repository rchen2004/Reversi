package cs3500.reversi.model;

import java.util.List;

/**
 * This interface represents a game of Reversi, extending the ReadOnlyReversi interface.
 * It provides methods for starting the game, making moves, and passing the
 * turn to the next player.
 * This actually modifies the game state, and allows playing of the game.
 */
public interface Reversi extends ReadOnlyReversi {

  /**
   * Starts our reversi game and sets gameStarted to true.
   * Initializes the board, put in the cells, and sets the starting
   * pieces in the desired cells.
   *
   * @throws IllegalStateException if the game has already started
   */
  void startGame();

  /**
   * Sets the piece of the desired cell based of the given coordinates to the current player.
   * Valid moves will flip opponents players pieces to the current players pieces if the move is
   * valid in that direction.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalStateException    if the game is already over
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   * @throws IllegalStateException    if the move is not allowable (i.e. the move is not
   *                                  logically possible) or a piece already exists in
   *                                  that position.
   */
  void movePiece(int q, int r);

  /**
   * Passes the move to the next player.
   * If passMove is called consecutively it will end the game.
   *
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the game is already over
   */
  void passMove();

  /**
   * Adds a feature listener to the view.
   *
   * @param features the feature listener
   */
  void addFeatureListener(Observer features);

  /**
   * Collects the flips for the given coordinates.
   * @param q the q coordinate
   * @param r the r coordinate
   * @return a list of the flips for the given coordinates
   */
  List<HexCell> collectFlips(int q, int r);


}