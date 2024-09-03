package cs3500.reversi.provider.player;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.DiscColor;
import cs3500.reversi.provider.model.ROReversiModel;
import cs3500.reversi.provider.model.Tile;

/**
 * The MaxCaptures class implements the ReversiStrategy interface
 * and represents a strategy for making moves in a Reversi game by maximizing the number
 * of captures.
 */
public class MaxCaptures implements ReversiStrategy {
  private final DiscColor playerColor;

  /**
   * Constructs a new instance of a strategy that picks a move that flips the maximum number
   * of the opponent's discs.
   * @param playerColor the DiscColor given to this player
   */
  public MaxCaptures(DiscColor playerColor) {
    this.playerColor = playerColor;
  }

  @Override
  public Optional<Coordinate> chooseMove(ROReversiModel model) {
    List<Tile> possibleValidMoves = getValidMoves(model, playerColor);
    Map<Tile, List<Tile>> discsToFlipForMove = new HashMap<>();
    for (Tile tile : possibleValidMoves) {
      discsToFlipForMove.put(tile, model.getDiscsToFlip(tile.getQ(), tile.getR(), playerColor));
    }
    Tile maxFlipsTile = getMaxFlipsTile(discsToFlipForMove);
    if (maxFlipsTile != null) {
      return Optional.of(maxFlipsTile.getCoordinate());
    }
    return Optional.empty();
  }

  @Override
  public List<Tile> getValidMoves(ROReversiModel model, DiscColor playerColor) {
    List<Tile> possibleMoves = model.getPossibleMoves(playerColor);
    possibleMoves.removeIf(tile -> !model.isValidMove(tile.getQ(), tile.getR(), playerColor));
    return possibleMoves;
  }

  private Tile getMaxFlipsTile(Map<Tile, List<Tile>> discsToFlipForMove) {
    int maxFlips = 0;
    Tile maxFlipsTile = null;
    for (Tile tile : discsToFlipForMove.keySet()) {
      List<Tile> tilesToFlip = discsToFlipForMove.get(tile);
      if (tilesToFlip.size() == maxFlips) {
        if (maxFlipsTile == null) {
          maxFlipsTile = tile;
        } else {
          int tileQ = tile.getQ();
          int tileR = tile.getR();
          int maxFlipsQ = maxFlipsTile.getQ();
          int maxFlipsR = maxFlipsTile.getR();
          if (tileQ < maxFlipsQ) {
            maxFlipsTile = tile;
          } else {
            if (tileR < maxFlipsR) {
              maxFlipsTile = tile;
            }
          }
        }
      }
      else if (tilesToFlip.size() > maxFlips) {
        maxFlips = tilesToFlip.size();
        maxFlipsTile = tile;
      }
    }
    return maxFlipsTile;
  }
}