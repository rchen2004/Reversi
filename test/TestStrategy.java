import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import cs3500.reversi.model.AnyOpenCornerStrategy;
import cs3500.reversi.model.AxialCoordinate;
import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.FallibleReversiStrategy;
import cs3500.reversi.model.MostPointsCapturedStrategy;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.TryTwo;
import cs3500.reversi.view.ReversiTextualView;
import cs3500.reversi.view.TextualView;

/**
 * A test class for evaluating the behavior of the MostPointsCapturedStrategy and TryTwo strategies
 * in the context of a Reversi game.
 */
public class TestStrategy {

  @Test
  public void testCornerStrategyWorks() {
    Reversi model = new BasicReversi(7);
    TextualView view = new ReversiTextualView(model);
    model.startGame();
    TryTwo strat = new TryTwo(new AnyOpenCornerStrategy(), new MostPointsCapturedStrategy());
    Optional<AxialCoordinate> move = strat.chooseMove(model, model.getCurrentPlayer());
    model.movePiece(move.get().getQ(), move.get().getR());
    System.out.println(view);
    Optional<AxialCoordinate> move1 = strat.chooseMove(model, model.getCurrentPlayer());
    model.movePiece(move1.get().getQ(), move1.get().getR());
    System.out.println(view);
    Optional<AxialCoordinate> move2 = strat.chooseMove(model, model.getCurrentPlayer());
    model.movePiece(move2.get().getQ(), move2.get().getR());
    System.out.println(view);
    Optional<AxialCoordinate> move3 = strat.chooseMove(model, model.getCurrentPlayer());
    model.movePiece(move3.get().getQ(), move3.get().getR());
    System.out.println(view);
    Optional<AxialCoordinate> move4 = strat.chooseMove(model, model.getCurrentPlayer());
    model.movePiece(move4.get().getQ(), move4.get().getR());
    System.out.println(view);
    Optional<AxialCoordinate> move5 = strat.chooseMove(model, model.getCurrentPlayer());
    model.movePiece(move5.get().getQ(), move5.get().getR());
    System.out.println(view);
    Optional<AxialCoordinate> move6 = strat.chooseMove(model, model.getCurrentPlayer());
    model.movePiece(move6.get().getQ(), move6.get().getR());
    System.out.println(view);
    Optional<AxialCoordinate> move7 = strat.chooseMove(model, model.getCurrentPlayer());
    model.movePiece(move7.get().getQ(), move7.get().getR());
    System.out.println(view);
    Optional<AxialCoordinate> move8 = strat.chooseMove(model, model.getCurrentPlayer());
    model.movePiece(move8.get().getQ(), move8.get().getR());
    System.out.println(view);
    Optional<AxialCoordinate> move9 = strat.chooseMove(model, model.getCurrentPlayer());
    model.movePiece(move9.get().getQ(), move9.get().getR());
    System.out.println(view);
    model.movePiece(1, -3);
    System.out.println(view);
    model.movePiece(2, 1);
    System.out.println(view);
    Optional<AxialCoordinate> corner = strat.chooseMove(model, model.getCurrentPlayer());
    model.movePiece(corner.get().getQ(), corner.get().getR());
    // checking if it plats the corner rather than the most points move
    Assert.assertEquals(corner.get().getQ(), 3);
    Assert.assertEquals(corner.get().getR(), -3);
    System.out.println(view);
  }

  @Test
  public void testTieInMostPointsStrategy() {
    Reversi rev = new BasicReversi(7);
    FallibleReversiStrategy strat = new MostPointsCapturedStrategy();
    rev.startGame();
    Optional<AxialCoordinate> best = strat.chooseMove(rev, rev.getCurrentPlayer());
    Assert.assertEquals(best.get().getQ(), -1);
    Assert.assertEquals(best.get().getR(), -1);
    rev.movePiece(-1, -1); //uppermost picked
    Optional<AxialCoordinate> best2 = strat.chooseMove(rev, rev.getCurrentPlayer());
    Assert.assertEquals(best2.get().getQ(), -1);
    Assert.assertEquals(best2.get().getR(), -2);
    rev.movePiece(-1, -2);
    Optional<AxialCoordinate> best3 = strat.chooseMove(rev, rev.getCurrentPlayer());
    Assert.assertEquals(best3.get().getQ(), -2);
    Assert.assertEquals(best3.get().getR(), 1);
    rev.movePiece(-2, -1); //uppermost picked
    Optional<AxialCoordinate> best4 = strat.chooseMove(rev, rev.getCurrentPlayer());
    Assert.assertEquals(best4.get().getQ(), 1);
    Assert.assertEquals(best4.get().getR(), -2);
    rev.movePiece(1, -2); //uppermost picked
  }

  @Test
  public void blah() {
    Reversi rev = new BasicReversi(7);
    TextualView view = new ReversiTextualView(rev);
    FallibleReversiStrategy strat = new MostPointsCapturedStrategy();
    rev.startGame();
    Optional<AxialCoordinate> best = strat.chooseMove(rev, rev.getCurrentPlayer());
    Assert.assertEquals(best.get().getQ(), -1);
    Assert.assertEquals(best.get().getR(), -1);
    rev.movePiece(1, 1); //uppermost picked
    Optional<AxialCoordinate> best2 = strat.chooseMove(rev, rev.getCurrentPlayer());
    System.out.println(view.toString());
    rev.movePiece(best2.get().getQ(), best2.get().getR());
    System.out.println(view.toString());
  }
}