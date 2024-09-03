package cs3500.reversi.provider.player;

import java.util.Optional;

import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.ROReversiModel;

/**
 * The NonHumanPlayer interface represents a non-human player in a Reversi game.
 * It extends the Player interface and defines methods for setting strategy and choosing moves.
 */
public interface NonHumanPlayer extends Player {

  /**
   * Sets the strategy for the non-human player.
   *
   * @param strategy The ReversiStrategy to be set for the player.
   */
  void setStrategy(ReversiStrategy strategy);

  /**
   * Chooses a move for the non-human player based on the given game model.
   *
   * @param model The ROReversiModel representing the current state of the game.
   * @return An Optional containing the chosen Coordinate for the player's move, if available.
   */
  Optional<Coordinate> chooseMove(ROReversiModel model);
}