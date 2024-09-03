import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import cs3500.reversi.model.AxialCoordinate;
import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.FallibleReversiStrategy;
import cs3500.reversi.model.MostPointsCapturedStrategy;
import cs3500.reversi.model.Player;
import cs3500.reversi.view.ReversiTextualView;
import cs3500.reversi.view.TextualView;

/**
 * This class contains JUnit tests for the `BasicReversi` model implementation, covering various
 * aspects of the Reversi game, including valid and invalid moves, game state, and player actions.
 */
public class TestReversiExamples {

  private BasicReversi rev;

  @Before
  public void init() {
    this.rev = new BasicReversi(7);
  }

  @Test
  public void testValidMoves() {
    rev.startGame();
    rev.movePiece(-1, -1);
    rev.movePiece(-1, -2);
    rev.movePiece(-2, -1);
    rev.movePiece(1, -2);
    rev.movePiece(2, -1);
    rev.movePiece(1, 1);
    rev.movePiece(-1, 2);
    rev.movePiece(-2, 1);
    Assert.assertThrows(IllegalStateException.class, () -> rev.movePiece(-2, 1));
  }

  @Test
  public void testInvalidMoves() {
    rev.startGame();
    Assert.assertThrows(IllegalArgumentException.class, () -> rev.movePiece(3, 1));
    Assert.assertThrows(IllegalArgumentException.class, () -> rev.movePiece(1, 3));
    Assert.assertThrows(IllegalStateException.class, () -> rev.movePiece(0, 0));
    Assert.assertThrows(IllegalStateException.class, () -> rev.movePiece(1, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> rev.movePiece(100, 1));
    Assert.assertThrows(IllegalArgumentException.class, () -> rev.movePiece(-100, 1));
  }

  @Test
  public void testMovesBeforeGameStart() {
    Assert.assertEquals(rev.getCurrentPlayer(), Player.O);
    Assert.assertEquals(rev.getRows(), 7);
    Assert.assertEquals(rev.getBoard().keySet().size(), 0);
    Assert.assertThrows(IllegalStateException.class, () -> rev.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () -> rev.movePiece(0, 0));
    Assert.assertThrows(IllegalStateException.class, () -> rev.passMove());
  }

  @Test
  public void testMovesAfterGameStart() {
    rev.startGame();
    Assert.assertEquals(rev.getRows(), 7);
    Assert.assertEquals(rev.getBoard().keySet().size(), 7);
    Assert.assertEquals(rev.getCurrentPlayer(), Player.O);
    Assert.assertFalse(rev.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () -> rev.startGame());
    rev.movePiece(-1, -1);
    rev.passMove();
    rev.passMove();
    Assert.assertTrue(rev.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () -> rev.movePiece(0, 0));
    Assert.assertThrows(IllegalStateException.class, () -> rev.passMove());
  }

  @Test
  public void testStartPositionGetsTopLeft() {
    TextualView view = new ReversiTextualView(rev);
    FallibleReversiStrategy strat = new MostPointsCapturedStrategy();
    rev.startGame();
    Assert.assertEquals(rev.getScore(Player.O), 3);
    Assert.assertEquals(rev.getScore(Player.X), 3);
    Optional<AxialCoordinate> best = strat.chooseMove(rev, Player.O);
    Assert.assertEquals(best.get().getQ(), -1);
    Assert.assertEquals(best.get().getR(), -1);

  }

  @Test
  public void testMostPointsStrategyBreaksTiesCorrectly() {
    TextualView view = new ReversiTextualView(rev);
    FallibleReversiStrategy strat = new MostPointsCapturedStrategy();
    rev.startGame();
    Optional<AxialCoordinate> best = strat.chooseMove(rev, rev.getCurrentPlayer());
    Assert.assertEquals(best.get().getQ(), -1);
    Assert.assertEquals(best.get().getR(), -1);
    System.out.println(view);
    rev.movePiece(-1, -1); //uppermost left picked when tie
    System.out.println(view);
    Optional<AxialCoordinate> best2 = strat.chooseMove(rev, rev.getCurrentPlayer());
    Assert.assertEquals(best2.get().getQ(), -1);
    Assert.assertEquals(best2.get().getR(), -2);
    rev.movePiece(-1, -2); // most points was picked
    System.out.println(view);
    Optional<AxialCoordinate> best3 = strat.chooseMove(rev, rev.getCurrentPlayer());
    Assert.assertEquals(best3.get().getQ(), -2);
    Assert.assertEquals(best3.get().getR(), 1);
    rev.movePiece(-2, -1); //uppermost left picked when tie
    System.out.println(view);
    Optional<AxialCoordinate> best4 = strat.chooseMove(rev, rev.getCurrentPlayer());
    Assert.assertEquals(best4.get().getQ(), 1);
    Assert.assertEquals(best4.get().getR(), -2);
    rev.movePiece(1, -2); //uppermost picked
    System.out.println(view);
    Optional<AxialCoordinate> best5 = strat.chooseMove(rev, rev.getCurrentPlayer());
    Assert.assertEquals(best5.get().getQ(), 2);
    Assert.assertEquals(best5.get().getR(), -1);
    rev.movePiece(2, -1);
    System.out.println(view);
  }
}
