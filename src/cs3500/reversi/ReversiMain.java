package cs3500.reversi;

import cs3500.reversi.model.AiPlayer;
import cs3500.reversi.model.AnyOpenCornerStrategy;
import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.HumanPlayer;
import cs3500.reversi.model.MostPointsCapturedStrategy;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.ReversiPlayer;
import cs3500.reversi.model.TryTwo;
import cs3500.reversi.provider.model.AdapterGameDisc;
import cs3500.reversi.provider.model.AdapterRoReversiModel;
import cs3500.reversi.provider.player.AdapterStrategy;
import cs3500.reversi.provider.player.GoForCorners;
import cs3500.reversi.provider.player.MaxCaptures;
import cs3500.reversi.provider.view.HexagonalReversiView;
import cs3500.reversi.provider.view.ReversiView;
import cs3500.reversi.provider.view.AdapterView;
import cs3500.reversi.view.Controller;
import cs3500.reversi.view.ReversiViews;
import cs3500.reversi.view.SimpleReversiView;
import cs3500.reversi.view.ViewFeatures;

/**
 * The main class for the Reversi game application. Initializes the game model, view, and runs a
 * sequence of moves to demonstrate the Reversi game.
 */
public class ReversiMain {

  /**
   * The main method for the Reversi game application.
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Usage: ReversiMain <player1Type>"
              + " <player2Type> where playerType is one of:");
      System.out.println("  human - human player");
      System.out.println("  easyai - easy AI player");
      System.out.println("  hardai - hard AI player");
      System.out.println("  providerstrategy1 - provider strategy 1");
      System.out.println("  providerstrategy2 - provider strategy 2");
      System.exit(1);
    }

    String player1Type = args[0];
    String player2Type = args[1];

    Reversi model = new BasicReversi(7);
    AdapterRoReversiModel adapter = new AdapterRoReversiModel(model);
    ReversiViews view = new SimpleReversiView(model, "White Controller");
    ReversiView view2 = new HexagonalReversiView(adapter);
    ReversiViews view3 = new AdapterView(view2);

    ReversiPlayer player1 = createPlayer(model, Player.O, player1Type);
    ReversiPlayer player2 = createPlayer(model, Player.X, player2Type);

    ViewFeatures controller = new Controller(model, player1, view);
    ViewFeatures controller2 = new Controller(model, player2, view3);

    model.startGame();
    view.display(true);
    view2.display(true);
  }

  /**
   * Creates a player of the given type and color.
   * @param model The model to use.
   * @param color The color of the player.
   * @param type The type of player to create.
   * @return The created player.
   */
  private static ReversiPlayer createPlayer(Reversi model, Player color, String type) {
    type = type.toLowerCase();
    switch (type) {
      case "human":
        return new HumanPlayer(model, color);
      case "easyai":
        return new AiPlayer(model, color, new TryTwo(new MostPointsCapturedStrategy(), null));
      case "hardai":
        return new AiPlayer(model, color, new TryTwo(
                new AnyOpenCornerStrategy(), new MostPointsCapturedStrategy()));
      case "providerstrategy1":
        return new AiPlayer(model, color, new AdapterStrategy(
                new MaxCaptures(new AdapterGameDisc(color).getColor()), model));
      case "providerstrategy2":
        return new AiPlayer(model, color, new AdapterStrategy(
                new GoForCorners(new AdapterGameDisc(color).getColor()), model));
      default:
        throw new IllegalArgumentException("Invalid player type: " + type);
    }
  }
}
