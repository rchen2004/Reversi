import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.AnyOpenCornerStrategy;
import cs3500.reversi.model.AxialCoordinate;
import cs3500.reversi.model.MostPointsCapturedStrategy;
import cs3500.reversi.model.Player;

/**
 * Tests for the MockReversi class, which provides a mock implementation of the Reversi game for
 * testing strategies.
 */
public class TestMockReversi {

  /**
   * Tests the MostPointsCapturedStrategy by applying the strategy on a MockReversi instance and
   * verifying the expected move and the number of iterations.
   */
  @Test
  public void testMultiplePointsCapturedStrategy() {
    MockReversi mock = new MockReversi(5);
    MostPointsCapturedStrategy strat = new MostPointsCapturedStrategy();
    mock.startGame();
    Optional<AxialCoordinate> move = strat.chooseMove(mock, Player.X);
    Assert.assertEquals(0, move.get().getQ());
    Assert.assertEquals(0, move.get().getR());
    List<Optional<AxialCoordinate>> iterated = mock.getIterated();
    Assert.assertFalse(iterated.isEmpty());
    //Mock states that every cell is a valid move, so expected size would be all open cells
    Assert.assertEquals(iterated.size(), 13); // 3 + 4 + 5 + 4 + 3 - 6
    // Check the choosemove was one of the iterated cells, further testing the iteration of
    // the method.
    Assert.assertTrue(iterated.contains(move));
  }

  /**
   * Tests the AnyOpenCornerStrategy by applying the strategy on a MockReversi instance and
   * verifying the number of iterations and that the chosen move is in the list of iterations.
   */
  @Test
  public void testAnyOpenCornerStrategy() {
    MockReversi mock = new MockReversi(5);
    AnyOpenCornerStrategy strat = new AnyOpenCornerStrategy();
    mock.startGame();
    Optional<AxialCoordinate> move = strat.chooseMove(mock, Player.X);
    List<Optional<AxialCoordinate>> iterated = mock.getIterated();
    Assert.assertFalse(iterated.isEmpty());
    Assert.assertEquals(iterated.size(), 6); // 3 + 4 + 5 + 4 + 3 - 6
    Assert.assertFalse(iterated.contains(move));
  }
}
