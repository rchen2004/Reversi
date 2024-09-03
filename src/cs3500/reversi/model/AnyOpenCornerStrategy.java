package cs3500.reversi.model;

import java.util.Optional;

/**
 * EXTRA CREDIT.
 * Strategy that aims place a piece in a corner spot on the board, which is a desirable
 * position.
 */
public class AnyOpenCornerStrategy implements FallibleReversiStrategy {
  /**
   * Chooses the next move for a player in a game of Reversi based on if a corner spot is playable.
   *
   * @param model  The current state of the Reversi game, represented as a ReadOnlyReversi instance.
   * @param player The player for whom the strategy is deciding the next move.
   * @return An Optional containing the chosen AxialCoordinate for the next move,
   *         or empty if no valid move is available or an error occurs.
   */
  @Override
  public Optional<AxialCoordinate> chooseMove(ReadOnlyReversi model, Player player) {
    if (model.isValidMoves(0, (int) Math.ceil(model.getRows() / 2))) { // top left
      return Optional.of(new AxialCoordinate(0, (int) Math.ceil(model.getRows() / 2)));
    } else if (model.isValidMoves((int) Math.ceil(model.getRows() / 2),
            (int) Math.ceil(model.getRows() / 2) * -1)) { // top right
      return Optional.of(new AxialCoordinate((int) Math.ceil(model.getRows() / 2),
              (int) Math.ceil(model.getRows() / 2) * -1));
    } else if (model.isValidMoves((int) Math.ceil(model.getRows() / 2) * -1, 0)) { // left
      return Optional.of(new AxialCoordinate((int) Math.ceil(model.getRows() / 2) * -1, 0));
    } else if (model.isValidMoves((int) Math.ceil(model.getRows() / 2), 0)) { // right
      return Optional.of(new AxialCoordinate((int) Math.ceil(model.getRows() / 2), 0));
    } else if (model.isValidMoves((int) Math.ceil(model.getRows() / 2) * -1,
            (int) Math.ceil(model.getRows() / 2))) { // bottom left
      return Optional.of(new AxialCoordinate((int) Math.ceil(model.getRows() / 2) * -1,
              (int) Math.ceil(model.getRows() / 2)));
    } else if (model.isValidMoves(0, (int) Math.ceil(model.getRows() / 2) * -1)) { // bottom right
      return Optional.of(new AxialCoordinate(0, (int) Math.ceil(model.getRows() / 2) * -1));
    } else {
      return Optional.empty();
    }
  }
}
