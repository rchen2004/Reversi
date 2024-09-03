import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.AiPlayer;
import cs3500.reversi.model.AxialCoordinate;
import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.HexCell;
import cs3500.reversi.model.HumanPlayer;
import cs3500.reversi.model.MostPointsCapturedStrategy;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.ReversiPlayer;

/**
 * Tests for the ReversiPlayer interface.
 */
public class TestPlayer {

  @Test
  public void testPlayer() {
    Reversi model = new BasicReversi(7);
    model.startGame();
    ReversiPlayer player1 = new HumanPlayer(model, Player.O);
    ReversiPlayer player2 = new AiPlayer(model, Player.X, new MostPointsCapturedStrategy());
    Assert.assertEquals(model.getCurrentPlayer(), player1.getColor());
    HexCell cell = new HexCell(-1, -1);
    player1.chooseMove(cell);
    Assert.assertEquals(player1.pieceToMove(), cell.coord);
    model.movePiece(-1, -1);
    player2.chooseMove(null); // AI Player doesnt need input
    AxialCoordinate coord = new AxialCoordinate(-1, -2);
    Assert.assertEquals(player2.pieceToMove(), coord);
    model.movePiece(-1, -2);
    Assert.assertEquals(model.getCurrentPlayer(), player1.getColor());
    player1.pass();
    Assert.assertEquals(model.getCurrentPlayer(), player2.getColor());
  }
}