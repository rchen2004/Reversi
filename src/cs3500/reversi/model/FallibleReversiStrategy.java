package cs3500.reversi.model;

import java.util.Optional;

/**
 * An interface representing a strategy for making moves in a game of Reversi (Othello),
 * with the ability to handle failure cases through the use of the Optional type.
 * Implementing classes provide logic for choosing a move based on the current state of the game.
 * The strategy may return an empty Optional if no valid move is available or if no move for
 * that specific strategy could be found.
 * The chooseMove method is responsible for determining the next move to be played by a specific
 * player in the Reversi game.
 *
 * @return An Optional containing the chosen AxialCoordinate for the next move, or empty if no
 *         valid move is available or an error occurs.
 */
public interface FallibleReversiStrategy {
  /**
   * Determines the next move to be played by a specific player in the Reversi game.
   * The strategy analyzes the current state of the game represented by the provided
   * ReadOnlyReversi model and decides on the optimal AxialCoordinate for the player's move.
   *
   * @param model  The ReadOnlyReversi model representing the current state of the game.
   * @param player The player for whom the strategy is choosing a move.
   * @return An Optional containing the chosen AxialCoordinate for the next move, or empty if no
   *         valid move is available or an error occurs.
   */
  Optional<AxialCoordinate> chooseMove(ReadOnlyReversi model, Player player);
}
