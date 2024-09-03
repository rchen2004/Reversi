package cs3500.reversi.provider.model;

import java.util.List;

/**
 * The ROReversiModel interface defines methods to access the state and information about a
 * Reversi game, without the ability to modify the game state.
 */
public interface ROReversiModel {

  /**
   * Gets the number of hexes along one edge of the board.
   *
   * @return the number of hexes
   */
  int getSize();

  /**
   * Gets the score for a player.
   * @param color the color that the player is playing as
   * @return the number of discs that have the player's color
   */
  int getScore(DiscColor color);

  /**
   * Gets the turn number, indicating the current player's turn. Black player's turn is
   * represented as 1, and white player's turn is represented as 2.
   *
   * @return The current turn number
   */
  int getTurn();

  /**
   * Retrieves a list of tiles that contain discs of the specified color.
   *
   * @return A list of tiles with discs of DiscColor
   */
  List<Tile> getHexesOfColor(DiscColor color);

  /**
   * Gets the color of the disc located at the specified Q, R, and S coordinates on the board.
   *
   * @param q The Q-coordinate of the tile
   * @param r The R-coordinate of the tile
   * @return The color of the disc at the specified coordinates
   */
  GameDisc getDiscAt(int q, int r);

  /**
   * Retrieves the tile at the specified Q, R, and S coordinates on the board.
   *
   * @param q The Q-coordinate of the tile
   * @param r The R-coordinate of the tile
   * @return The tile at the specified coordinates
   */
  Tile getTileAt(int q, int r);

  /**
   * Creates and returns a copy of the game board.
   *
   * @return a list of tiles representing the entire game board
   */
  List<Tile> getBoard();

  /**
   * Checks if a move for color is valid at coordinates q and r.
   *
   * @param q     the q coordinate
   * @param r     the r coordinate
   * @param color the current player's color
   * @return true if the move is valid, false otherwise
   */
  boolean isValidMove(int q, int r, DiscColor color);

  /**
   * Checks if a valid move exists anywhere on the board for color.
   *
   * @param color the current player's color
   * @return true if a move exists, false otherwise
   */
  boolean doesValidMoveExist(DiscColor color);

  /**
   * Checks if the game is over, indicating whether the game has reached a ending condition.
   *
   * @return `true` if the game is over, `false` otherwise
   * @throws IllegalStateException If the game has not started
   */
  boolean gameOver();

  /**
   * Retrieves a list of possible moves for a player of the specified color.
   *
   * @param color The color of the player
   * @return A list of tiles representing possible moves for the player
   */
  List<Tile> getPossibleMoves(DiscColor color);

  /**
   * Retrieves a list of tiles that would be flipped if a disc of the specified color
   * is placed at the given coordinates (q, r).
   *
   * @param q     The Q-coordinate of the tile
   * @param r     The R-coordinate of the tile
   * @param color The color of the disc to be placed
   * @return A list of tiles representing discs that would be flipped
   */
  List<Tile> getDiscsToFlip(int q, int r, DiscColor color);

  /**
   * Retrieves the color of the current turn which is represented by an integer.
   * @return a disc of either black or white (0 = Black | 1 = White)
   */
  DiscColor colorBasedOnTurn();
}