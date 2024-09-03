package cs3500.reversi.provider.player;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.DiscColor;
import cs3500.reversi.provider.model.ROReversiModel;
import cs3500.reversi.provider.model.Tile;

/**
 * The ReversiStrategy interface defines the contract for implementing different strategies
 * for making moves in a Reversi game.
 */
public interface ReversiStrategy {

  /**
   * Chooses the best move based on a series of parameters.
   * @param model a mutable reversi model
   * @return a coordinate for the best available move
   */
  Optional<Coordinate> chooseMove(ROReversiModel model);

  /**
   * Gets available moves for the current board and player color.
   * @param model a mutable reversi model
   * @param playerColor the current player color
   * @return a list of available moves on empty tiles
   */
  List<Tile> getValidMoves(ROReversiModel model, DiscColor playerColor);
}