package cs3500.reversi.provider.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.provider.model.DiscColor;
import cs3500.reversi.provider.model.ROReversiModel;
import cs3500.reversi.provider.model.Tile;

/**
 * The HumanPlayer class represents a human player in the Reversi game.
 * It allows human interaction for making moves on the game board.
 */
public class HumanPlayer implements Player {
  private final DiscColor color;

  /**
   * Constructs a new instance of a Human player with a specified color.
   *
   * @param color The DiscColor representing the player's color (BLACK or WHITE).
   */
  public HumanPlayer(DiscColor color) {
    this.color = color;
  }

  @Override
  public DiscColor getPlayerColor() {
    return color;
  }

  @Override
  public List<Tile> getValidMoves(ROReversiModel model, DiscColor playerColor) {
    List<Tile> validMoves = new ArrayList<>();
    for (Tile tile : model.getPossibleMoves(playerColor)) {
      if (model.isValidMove(tile.getQ(), tile.getR(), playerColor)) {
        validMoves.add(tile);
      }
    }
    return validMoves;
  }
}
