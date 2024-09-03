package cs3500.reversi.provider.model;

/**
 * The MutableReversiModel interface extends the ROReversiModel interface to provide additional
 * methods for modifying the state of a Reversi game.
 */
public interface MutableReversiModel extends ROReversiModel {

  /**
   * Starts the game once the board is created.
   */
  void startGame();

  /**
   * Places a disc of the current player's color at the specified Q and R coordinates on the board.
   *
   * @param q The Q-coordinate of the tile where the disc should be placed
   * @param r The R-coordinate of the tile where the disc should be placed
   * @throws IllegalStateException If the game has not started or the move is invalid
   */
  void placeDisc(int q, int r);

  /**
   * Passes the turn to the next player, jf no moves are available or if the player so chooses.
   *
   * @throws IllegalStateException If the game has not started
   */
  void pass();

  /**
   * Adds a listener to the model to receive notifications about changes in the game state.
   *
   * @param listener The ModelFeatures listener to be added
   */
  void addListener(ModelFeatures listener);
}