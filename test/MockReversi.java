import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.AxialCoordinate;
import cs3500.reversi.model.BasicReversi;

/**
 * A mock implementation of the {@link BasicReversi} class for testing purposes.
 * This mock class overrides specific methods and introduces additional fields to simulate behavior.
 */
public class MockReversi extends BasicReversi {

  /** Ensures that the controller is speaking to the model correctly. */
  private String log;

  /** The score associated with the mock implementation. */
  private int score;

  /** A list of Optional AxialCoordinates that were iterated in the mock implementation. */
  private List<Optional<AxialCoordinate>> iteratedList = new ArrayList<>();

  /**
   * Constructs a MockReversi instance with the specified number of rows.
   *
   * @param rows The number of rows for the mock reversi board.
   */
  public MockReversi(int rows) {
    super(rows);
    this.score = 0;
  }

  /**
   * Overrides the method to always return true, simulating a valid move.
   *
   * @param q The q coordinate of the move.
   * @param r The r coordinate of the move.
   * @return Always true.
   */
  @Override
  public boolean isValidMove(int q, int r) {
    return true;
  }

  /**
   * Adds an Optional AxialCoordinate to the iterated list.
   * Always returns false, simulating an invalid move.
   *
   * @param q The q coordinate of the move.
   * @param r The r coordinate of the move.
   * @return Always false.
   */
  @Override
  public boolean isValidMoves(int q, int r) {
    Optional<AxialCoordinate> points = Optional.of(new AxialCoordinate(q, r));
    iteratedList.add(points);
    return false;
  }

  /**
   * Overrides the method to set the score to 10 if the specified coordinates are (0, -1).
   * Sets the log to the move made.
   *
   * @param q The q coordinate of the move.
   * @param r The r coordinate of the move.
   */
  @Override
  public void movePiece(int q, int r) {
    if (q == 0 && r == -1) {
      this.score = 10;
    }
    this.log = "moved piece to (" + q + ", " + r + ")";
  }

  /**
   * Overrides the method for pass move to log that
   * the controller is communicating to model correctly.
   */
  @Override
  public void passMove() {
    this.log = "passed move";
  }

  /**
   * Gets the list of Optional AxialCoordinates that were iterated in the mock implementation.
   *
   * @return The list of Optional AxialCoordinates.
   */
  public List<Optional<AxialCoordinate>> getIterated() {
    return iteratedList;
  }

  /**
   * Returns the log of the move made.
   * @return the log of the move made
   */
  public String getLog() {
    return log;
  }
}
