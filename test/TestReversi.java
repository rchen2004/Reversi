import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.Player;

/**
 * This class contains JUnit tests for the ReversiExamples class, which provides example
 * scenarios and setups for testing the Reversi model and game logic.
 */
public class TestReversi {
  BasicReversi rev;

  @Before
  public void init() {
    this.rev = new BasicReversi(7);
  }

  @Test
  public void testStartGame() {
    BasicReversi game1 = new BasicReversi(7);
    BasicReversi game2 = new BasicReversi(9);
    game1.startGame();
    game2.startGame();
    Assert.assertEquals(game1.getBoard().keySet().size(), 7);
    Assert.assertEquals(game2.getBoard().keySet().size(), 9);
    Assert.assertThrows(IllegalStateException.class, () -> game1.startGame());
    Assert.assertThrows(IllegalStateException.class, () -> game2.startGame());
    Assert.assertThrows(IllegalArgumentException.class, () -> new BasicReversi(0));
    Assert.assertThrows(IllegalArgumentException.class, () -> new BasicReversi(-1));
    Assert.assertThrows(IllegalArgumentException.class, () -> new BasicReversi(6));
    Assert.assertThrows(IllegalArgumentException.class, () -> new BasicReversi(4));
    Assert.assertThrows(IllegalArgumentException.class, () -> new BasicReversi(100));
    Assert.assertThrows(IllegalArgumentException.class, () -> new BasicReversi(1));
  }

  @Test
  public void testMovePiece() {
    rev.startGame();
    rev.movePiece(-1, -1);
    rev.movePiece(-1, -2);
    rev.movePiece(-2, -1);
    rev.movePiece(1, -2);
    rev.movePiece(2, -1);
    rev.movePiece(1, 1);
    rev.movePiece(-1, 2);
    rev.movePiece(-2, 1);
    Assert.assertThrows(IllegalArgumentException.class, () -> rev.movePiece(3, 1));
    Assert.assertThrows(IllegalArgumentException.class, () -> rev.movePiece(1, 3));
    Assert.assertThrows(IllegalStateException.class, () -> rev.movePiece(0, 0));
    Assert.assertThrows(IllegalStateException.class, () -> rev.movePiece(1, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> rev.movePiece(100, 1));
    Assert.assertThrows(IllegalArgumentException.class, () -> rev.movePiece(-100, 1));
  }

  @Test
  public void testPassMove() {
    Assert.assertThrows(IllegalStateException.class, () -> rev.passMove());
    rev.startGame();
    rev.movePiece(-1, -1);
    rev.movePiece(-1, -2);
    rev.passMove();
    rev.passMove();
    Assert.assertTrue(rev.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () -> rev.passMove());
  }

  @Test
  public void testGetRows() {
    BasicReversi game1 = new BasicReversi(11);
    rev.startGame();
    game1.startGame();
    Assert.assertEquals(rev.getRows(), 7);
    Assert.assertEquals(game1.getRows(), 11);
  }

  @Test
  public void testGetBoard() {
    BasicReversi game1 = new BasicReversi(11);
    rev.startGame();
    game1.startGame();
    Assert.assertEquals(rev.getBoard().keySet().size(), 7);
    Assert.assertEquals(game1.getBoard().keySet().size(), 11);
  }

  @Test
  public void testGetCurrentPlayer() {
    rev.startGame();
    Assert.assertEquals(rev.getCurrentPlayer(), Player.O);
    rev.movePiece(-1, -1);
    Assert.assertEquals(rev.getCurrentPlayer(), Player.X);
    rev.movePiece(-1, -2);
    Assert.assertEquals(rev.getCurrentPlayer(), Player.O);
    rev.movePiece(-2, -1);
    Assert.assertEquals(rev.getCurrentPlayer(), Player.X);
    rev.movePiece(1, -2);
    Assert.assertEquals(rev.getCurrentPlayer(), Player.O);
    rev.movePiece(2, -1);
    Assert.assertEquals(rev.getCurrentPlayer(), Player.X);
    rev.movePiece(1, 1);
    Assert.assertEquals(rev.getCurrentPlayer(), Player.O);
    rev.movePiece(-1, 2);
    Assert.assertEquals(rev.getCurrentPlayer(), Player.X);
    rev.movePiece(-2, 1);
    Assert.assertEquals(rev.getCurrentPlayer(), Player.O);
  }

  @Test
  public void testGameOver() {
    BasicReversi game1 = new BasicReversi(5);
    rev.startGame();
    game1.startGame();
    Assert.assertFalse(rev.isGameOver());
    Assert.assertFalse(game1.isGameOver());
    rev.passMove();
    rev.passMove();
    game1.passMove();
    game1.passMove();
    Assert.assertTrue(rev.isGameOver());
    Assert.assertTrue(game1.isGameOver());
    //You can add a full game where there are no moves left to make
  }

  @Test
  public void testGetScore() {
    BasicReversi game1 = new BasicReversi(5);
    rev.startGame();
    game1.startGame();
    Assert.assertEquals(rev.getScore(Player.O), 3);
    Assert.assertEquals(rev.getScore(Player.X), 3);
    Assert.assertEquals(game1.getScore(Player.O), 3);
    Assert.assertEquals(game1.getScore(Player.X), 3);
    rev.movePiece(-1, -1);
    rev.movePiece(-1, -2);
    rev.movePiece(-2, -1);
    rev.movePiece(1, -2);
    rev.movePiece(2, -1);
    rev.movePiece(1, 1);
    rev.movePiece(-1, 2);
    rev.movePiece(-2, 1);
    Assert.assertEquals(rev.getScore(Player.O), 5);
    Assert.assertEquals(rev.getScore(Player.X), 9);
  }

  @Test
  public void testGetValidMoves() {
    rev.startGame();
    Assert.assertEquals(rev.getValidMoves().size(), 6);
    Assert.assertTrue(rev.isValidMoves(-1, -1));
    rev.movePiece(-1, -1);
    Assert.assertTrue(rev.isValidMoves(-1, -2));
    rev.movePiece(-1, -2);
    Assert.assertTrue(rev.isValidMoves(-2, -1));
    rev.movePiece(-2, -1);
    rev.movePiece(1, -2);
    rev.movePiece(2, -1);
    rev.movePiece(1, 1);
    rev.movePiece(-1, 2);
    rev.movePiece(-2, 1);
    Assert.assertEquals(rev.getValidMoves().size(), 3);
  }
}