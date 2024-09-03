package cs3500.reversi.model;

import java.util.List;
import java.util.Optional;

/**
 * A Reversi strategy that selects the move with the most points captured by the player.
 * This strategy implements the required behavior of selecting the move with
 * the most captured pieces
 * and breaking ties using the uppermost-leftmost coordinate.
 */
public class MostPointsCapturedStrategy implements FallibleReversiStrategy {

  /**
   * Chooses the next move for a player in a game of Reversi based on the most points captured.
   * In case of ties, the strategy breaks ties by selecting the move with the
   * uppermost-leftmost coordinate.
   *
   * @param model  The current state of the Reversi game, represented as a ReadOnlyReversi instance.
   * @param player The player for whom the strategy is deciding the next move.
   * @return An Optional containing the chosen AxialCoordinate for the next move,
   *         or empty if no valid move is available or an error occurs.
   */
  public Optional<AxialCoordinate> chooseMove(ReadOnlyReversi model, Player player) {
    int maxPoints = model.getScore(player);
    AxialCoordinate bestMove = new AxialCoordinate(0, 0);
    AxialCoordinate leftMove = new AxialCoordinate(0, 0);
    boolean tie = false;
    int maxS = Integer.MIN_VALUE;
    int minR = Integer.MAX_VALUE;
    int minQ = Integer.MAX_VALUE;
    List<AxialCoordinate> coords = model.getValidMoves();

    for (AxialCoordinate move : coords) {
      BasicReversi temp = new BasicReversi((BasicReversi) model);
      temp.movePiece(move.getQ(), move.getR());
      int points = temp.getScore(player);

      if (points > maxPoints) {
        maxPoints = points;
        bestMove = move;
        tie = false;
      }

      if (points == maxPoints && ((move.getQ() < minQ && move.getS() > maxS)
              || (move.getS() > maxS && move.getR() < minR))) {
        leftMove = move;
        tie = true;
        maxS = move.getS();
        minR = move.getR();
        minQ = move.getQ();
      }
    }
    if (tie) {
      return Optional.of(leftMove);
    } else {
      return Optional.of(bestMove);
    }
  }
}
