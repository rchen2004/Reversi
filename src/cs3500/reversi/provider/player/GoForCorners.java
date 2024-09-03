package cs3500.reversi.provider.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.provider.model.ROReversiModel;
import cs3500.reversi.provider.model.DiscColor;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.Tile;
import cs3500.reversi.provider.player.ReversiStrategy;


/**
 * The GoForCorners class represents a Reversi strategy that aims to capture corners.
 * It implements the ReversiStrategy interface to provide a move based on corner capturing logic.
 */
public class GoForCorners implements ReversiStrategy {
  private final DiscColor playerColor;

  /**
   * Constructs a new instance of a strategy that prioritizes moving in a corner of the board,
   * and then moves wherever it can flip the maximum discs.
   * @param playerColor the DiscColor given to this player
   */
  public GoForCorners(DiscColor playerColor) {
    this.playerColor = playerColor;
  }

  @Override
  public Optional<Coordinate> chooseMove(ROReversiModel model) {
    List<Tile> possibleValidMoves = getValidMoves(model, playerColor);
    List<Tile> corners = getCorners(model);
    List<Tile> possibleCorners = new ArrayList<>();
    for (Tile possibleMove : possibleValidMoves) {
      for (Tile corner : corners) {
        if (possibleMove.equals(corner)) {
          possibleCorners.add(corner);
        }
      }
    }
    if (possibleCorners.isEmpty()) {
      return new MaxCaptures(playerColor).chooseMove(model);
    } else if (possibleCorners.size() == 1) {
      Tile cornerTile = possibleCorners.get(0);
      return Optional.of(cornerTile.getCoordinate());
    } else {
      // return corner that is upper-left most
      Tile bestCorner = getBestCorner(possibleCorners);
      if (bestCorner != null) {
        return Optional.of(bestCorner.getCoordinate());
      }
    }
    return Optional.empty();
  }

  @Override
  public List<Tile> getValidMoves(ROReversiModel model, DiscColor playerColor) {
    List<Tile> possibleMoves = model.getPossibleMoves(playerColor);
    possibleMoves.removeIf(tile -> !model.isValidMove(tile.getQ(), tile.getR(), playerColor));
    return possibleMoves;
  }

  private List<Tile> getCorners(ROReversiModel model) {
    List<Tile> corners = new ArrayList<>();
    int size = model.getSize();
    corners.add(model.getTileAt(size - 1, 0));
    corners.add(model.getTileAt(-size + 1, 0));
    corners.add(model.getTileAt(0, size - 1));
    corners.add(model.getTileAt(0, -size + 1));
    corners.add(model.getTileAt(size - 1, -size + 1));
    corners.add(model.getTileAt(-size + 1, size - 1));
    return corners;
  }

  private Tile getBestCorner(List<Tile> possibleCorners) {
    Tile bestCorner = null;
    int lowestQ = Integer.MAX_VALUE;
    int lowestR = Integer.MAX_VALUE;

    for (Tile corner : possibleCorners) {
      int q = corner.getCoordinate().getQ();
      int r = corner.getCoordinate().getR();

      if (q < lowestQ || (q == lowestQ && r < lowestR)) {
        lowestQ = q;
        lowestR = r;
        bestCorner = corner;
      }
    }
    return bestCorner;
  }
}