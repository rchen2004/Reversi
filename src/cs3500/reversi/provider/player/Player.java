package cs3500.reversi.provider.player;

import java.util.List;

import cs3500.reversi.provider.model.DiscColor;
import cs3500.reversi.provider.model.ROReversiModel;
import cs3500.reversi.provider.model.Tile;

/**
 * The Player interface represents a player in a game.
 * It defines methods to retrieve the player's name and color of game pieces (discs).
 */
public interface Player {

  /**
   * Retrieves the color of the player's game pieces (discs).
   *
   * @return The DiscColor representing the color of the player's game pieces.
   */
  DiscColor getPlayerColor();

  /**
   * Gets valid moves on the current Reversi board based on the player's color.
   * @param model the mutable reversi model
   * @param playerColor the players color
   * @return valid moves for the specified player color
   */
  List<Tile> getValidMoves(ROReversiModel model, DiscColor playerColor);
}