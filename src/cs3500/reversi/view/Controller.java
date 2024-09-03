package cs3500.reversi.view;

import java.awt.Component;

import javax.swing.JOptionPane;

import cs3500.reversi.model.HexCell;
import cs3500.reversi.model.Observer;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.ReversiPlayer;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.view.Hexagon;

/**
 * Represents a controller for the Reversi game.
 * The controller is responsible for handling user input and updating the view.
 * The controller is also responsible for notifying the player when it is their turn.
 *
 * @field model The Reversi model.
 * @field view The Reversi view.
 * @field player The Reversi player.
 */
public class Controller implements ViewFeatures, Observer,
        cs3500.reversi.provider.view.ViewFeatures {
  private final Reversi model;
  private final ReversiViews view;
  private final ReversiPlayer player;

  /**
   * Constructs a Controller for the Reversi game.
   *
   * @param model  The Reversi model.
   * @param player The Reversi player.
   * @param view   The Reversi view.
   */
  public Controller(Reversi model, ReversiPlayer player, ReversiViews view) {
    this.model = model;
    this.model.addFeatureListener(this);
    this.view = view;
    this.view.addFeatureListener(this);
    this.view.addFeatureListeners(this);
    this.player = player;
  }


  /**
   * Stores the hexagon that was clicked.
   * @param hexagon The Hexagon object representing the hexagon that was clicked.
   */
  @Override
  public void clickHex(Hexagon hexagon) {
    return;
  }

  /**
   * Moves the piece to the given coordinates.
   * @param coordinates The Coordinate object representing the coordinates of the move.
   */
  @Override
  public void move(Coordinate coordinates) {
    model.movePiece(coordinates.getQ(), coordinates.getR());
  }

  /**
   * Passes the move to the next player.
   */
  @Override
  public void pass() {
    model.passMove();
  }

  /**
   * Quits the game.
   */
  @Override
  public void quit() {
    System.exit(0);
  }

  /**
   * Moves the piece to the given cell.
   *
   * @param cell The cell to move the piece to.
   */
  @Override
  public void movePiece(HexCell cell) {
    if (model.getCurrentPlayer() == player.getColor() && !model.isGameOver()) {
      player.chooseMove(cell);
      try {
        model.movePiece(player.pieceToMove().getQ(), player.pieceToMove().getR());
      } catch (IllegalStateException e) {
        JOptionPane.showMessageDialog((Component) this.view, "Invalid move",
                "Invalid move", JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog((Component) this.view, "Not your turn",
              "Not your turn", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Passes the move to the next player.
   */
  @Override
  public void passMove() {
    try {
      player.pass();
    }
    catch (IllegalStateException e) {
      JOptionPane.showMessageDialog((Component) this.view, "Game over",
              "Game is over", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Notifies the player that it is their turn, updates the view, and updates the scoreboard.
   */
  @Override
  public void notice() {
    if (model.getCurrentPlayer() == player.getColor()) {
      player.myTurn();
    }
    this.view.advance();
    if (model.getCurrentPlayer().toString().equals("O")) {
      this.view.setTitle("Reversi: White's Turn |\n"
              + " White: " + model.getScore(Player.O) + "   Black: " + model.getScore(Player.X));
    } else {
      this.view.setTitle("Reversi: Black's Turn |\n"
              + " White: " + model.getScore(Player.O) + "   Black: " + model.getScore(Player.X));
    }
  }
}
