import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.HexCell;
import cs3500.reversi.model.HumanPlayer;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.ReversiPlayer;
import cs3500.reversi.view.Controller;
import cs3500.reversi.view.ReversiViews;
import cs3500.reversi.view.SimpleReversiView;
import cs3500.reversi.view.ViewFeatures;

/**
 * Tests the controller for the Reversi game.
 */
public class TestController {
  Reversi model = new BasicReversi(7);
  MockReversi mock = new MockReversi(7);
  ReversiViews view = new SimpleReversiView(model, "White Controller");
  ReversiViews view2 = new SimpleReversiView(model, "Black Controller");

  ReversiPlayer player1 = new HumanPlayer(model, Player.O);
  ReversiPlayer player2 = new HumanPlayer(model, Player.X);
  ReversiPlayer mockplayer = new HumanPlayer(mock, Player.O);

  ViewFeatures controller = new Controller(model, player1, view);
  ViewFeatures controller2 = new Controller(model, player2, view2);
  ViewFeatures mockController1 = new Controller(mock, mockplayer, view);

  /**
   * Tests the controller for the Reversi game.
   * Uses all methods in the controller class such as movePiece, passMove, and quit.
   */
  @Test
  public void testController() {
    model.startGame();
    Assert.assertEquals(model.getCurrentPlayer(), Player.O);
    Assert.assertEquals(model.getScore(Player.O), 3);
    Assert.assertEquals(view.getTitle(), "White Controller");
    Assert.assertEquals(view2.getTitle(), "Black Controller");
    controller.movePiece(new HexCell(-1, -1));
    Assert.assertEquals(model.getScore(Player.O), 5);
    Assert.assertEquals(model.getCurrentPlayer(), Player.X);
    Assert.assertEquals(view.getTitle(), "Reversi: Black's Turn |\n"
            + " White: 5   Black: 2");
    controller2.movePiece(new HexCell(-1, -2));
    Assert.assertEquals(model.getScore(Player.X), 5);
    Assert.assertEquals(model.getScore(Player.O), 3);
    Assert.assertEquals(model.getCurrentPlayer(), Player.O);
    controller.movePiece(new HexCell(-2, -1));
    controller2.passMove();
    Assert.assertEquals(view.getTitle(), "Reversi: White's Turn |\n"
            + " White: 5   Black: 4");
    controller.passMove();
    Assert.assertThrows(IllegalStateException.class, () -> controller.movePiece(new HexCell(0, 0)));
  }

  @Test
  public void testMock() {
    mock.startGame();
    Assert.assertEquals(mock.getCurrentPlayer(), Player.O);
    Assert.assertEquals(mock.getScore(Player.O), 3);
    Assert.assertEquals(mock.getScore(Player.X), 3);
    mockController1.movePiece(new HexCell(-1, -1));
    Assert.assertEquals(mock.getLog(), "moved piece to (-1, -1)");
    mockController1.movePiece(new HexCell(-2, -1));
    Assert.assertEquals(mock.getLog(), "moved piece to (-2, -1)");
    mockController1.movePiece(new HexCell(-1, -2));
    Assert.assertEquals(mock.getLog(), "moved piece to (-1, -2)");
    mockController1.passMove();
    Assert.assertEquals(mock.getLog(), "passed move");
  }
}
