package cs3500.reversi.provider.player;


import java.util.List;
import java.util.Optional;

import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.DiscColor;
import cs3500.reversi.provider.model.ROReversiModel;
import cs3500.reversi.provider.model.Tile;

/**
 * The AIPlayer class represents an AI-controlled player in a Reversi game.
 * It implements the NonHumanPlayer interface and provides AI-based moves.
 */
public class AIPlayer implements NonHumanPlayer {
  private ReversiStrategy strategy;
  private final DiscColor playerColor;

  /**
   * Constructs a new instance of an AI player.
   * @param playerColor the player's given DiscColor
   */
  public AIPlayer(DiscColor playerColor) {
    this.playerColor = playerColor;
  }

  @Override
  public DiscColor getPlayerColor() {
    return playerColor;
  }

  @Override
  public List<Tile> getValidMoves(ROReversiModel model, DiscColor playerColor) {
    return strategy.getValidMoves(model, playerColor);
  }

  @Override
  public void setStrategy(ReversiStrategy strategy) {
    this.strategy = strategy;
  }

  @Override
  public Optional<Coordinate> chooseMove(ROReversiModel model) {
    return strategy.chooseMove(model);
  }
}