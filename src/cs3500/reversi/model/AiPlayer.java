package cs3500.reversi.model;

import java.util.Optional;

/**
 * Represents an AI player for the Reversi game.
 *
 * @field model The Reversi model to which this player belongs.
 * @field piece The piece color of this player.
 * @field strat The strategy used by this player to choose moves.
 * @field coords The coordinates of the move chosen by this player.
 */
public class AiPlayer implements ReversiPlayer {
  private final Reversi model;
  private final Player piece;
  private final FallibleReversiStrategy strat;
  private Optional<AxialCoordinate> coords;

  /**
   * Constructs an AI player with the given model, piece color, and strategy.
   *
   * @param model The Reversi model to which this player belongs.
   * @param piece The piece color of this player.
   * @param strat The strategy used by this player to choose moves.
   */
  public AiPlayer(Reversi model, Player piece, FallibleReversiStrategy strat) {
    this.model = model;
    this.piece = piece;
    this.strat = strat;
  }

  /**
   * Chooses a move for this player.
   *
   * @param cell The cell on which the move is made.
   */
  @Override
  public void chooseMove(HexCell cell) {
    coords = strat.chooseMove(model, piece);
  }

  /**
   * Passes this player's turn.
   */
  @Override
  public void pass() {
    model.passMove();
  }

  /**
   * Returns the coordinates of the move chosen by this player.
   *
   * @return The coordinates of the move chosen by this player.
   */
  @Override
  public AxialCoordinate pieceToMove() {
    return coords.orElseThrow(IllegalStateException::new); // Return the value or throw
  }

  /**
   * Returns the piece color of this player.
   *
   * @return The piece color of this player.
   */
  @Override
  public Player getColor() {
    return this.piece;
  }

  /**
   * Performs this player's turn whether it is a move or pass.
   */
  @Override
  public void myTurn() {
    if (!model.getValidMoves().isEmpty()) {
      Optional<AxialCoordinate> best = strat.chooseMove(model, piece);
      if (!(best.get().getQ() == 0 && best.get().getR() == 0)) {
        model.movePiece(best.get().getQ(), best.get().getR());
      } else {
        model.passMove();
      }
    }
  }
}
