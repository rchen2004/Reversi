package cs3500.reversi.model;

/**
 * Represents a human player for the Reversi game.
 * @field model The Reversi model to which this player belongs.
 * @field piece The piece color of this player.
 * @field chosenMove The coordinates of the move chosen by this player.
 */
public class HumanPlayer implements ReversiPlayer {
  private final Reversi model;

  public final Player piece;

  private HexCell chosenMove;

  /**
   * Constructs a human player with the given model and piece color.
   * @param model The Reversi model to which this player belongs.
   * @param piece The piece color of this player.
   */
  public HumanPlayer(Reversi model, Player piece) {
    this.model = model;
    this.piece = piece;
  }

  /**
   * Chooses a move for this player.
   * @param cell The cell on which the move is made.
   */
  @Override
  public void chooseMove(HexCell cell) {
    chosenMove = cell;
  }

  /**
   * Returns the coordinates of the move chosen by this player.
   * @return The coordinates of the move chosen by this player.
   */
  @Override
  public AxialCoordinate pieceToMove() {
    return this.chosenMove.coord;
  }

  /**
   * Returns the piece color of this player.
   * @return The piece color of this player.
   */
  @Override
  public Player getColor() {
    return this.piece;
  }

  /**
   * Does nothing because it is a human player.
   */
  @Override
  public void myTurn() {
    // awaits view so does nothing
  }

  /**
   * Passes this player's turn.
   */
  @Override
  public void pass() {
    this.model.passMove();
  }
}
