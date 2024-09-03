package cs3500.reversi.model;

import java.util.Optional;

/**
 * This class represents a combination of two FallibleReversiStrategy instances.
 * It attempts to use the first strategy, and if it fails (returns no move),
 * it falls back to the second strategy.
 */
public class TryTwo implements FallibleReversiStrategy {

  private FallibleReversiStrategy first;
  private FallibleReversiStrategy second;

  /**
   * Constructs a TryTwo strategy with two specified strategies.
   *
   * @param first  The first strategy to attempt.
   * @param second The second strategy to use if the first one fails.
   */
  public TryTwo(FallibleReversiStrategy first, FallibleReversiStrategy second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Chooses a move using the first strategy, and if it fails, falls back to the second strategy.
   *
   * @param model   The ReadOnlyReversi model representing the game state.
   * @param forWhom The player for whom the move is chosen.
   * @return An Optional containing the chosen move, or empty if no move is chosen.
   */
  @Override
  public Optional<AxialCoordinate> chooseMove(ReadOnlyReversi model, Player forWhom) {
    Optional<AxialCoordinate> ans = this.first.chooseMove(model, forWhom);
    if (ans.isPresent()) {
      return ans; // the first strategy succeeded
    }
    return this.second.chooseMove(model, forWhom);
  }
}
