package cs3500.reversi.provider.view.textual;

import java.io.IOException;
import java.util.List;

import cs3500.reversi.provider.model.DiscColor;
import cs3500.reversi.provider.model.ROReversiModel;
import cs3500.reversi.provider.model.Tile;

/**
 * Responsible for rendering the current Read Only game board to a string.
 */
public class ReversiTextualView implements TextualView {
  private final ROReversiModel model;
  private final Appendable out;

  /**
   * Constructs a ReversiTextualView for the given Reversi model and output destination.
   *
   * @param model the Reversi model to render
   * @param out   the output destination
   */
  public ReversiTextualView(ROReversiModel model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  /**
   * Renders the Reversi game board and displays it to the output destination.
   */
  @Override
  public void render() {
    try {
      out.append(toString());
    } catch (IOException e) {
      System.out.println("Board cannot print");
    }
  }

  /**
   * Generates a textual representation of the Reversi game board.
   *
   * @return a string representation of the game board
   */
  @Override
  public String toString() {
    List<Tile> board = model.getBoard();
    int size = model.getSize();
    return renderTopHalf(board, size) + renderBottomHalf(board, size);
  }

  private String renderTopHalf(List<Tile> board, int size) {
    StringBuilder result = new StringBuilder();
    for (int r = -size + 1; r <= 0; r++) {
      int offset = Math.max(0, size - 1 + Math.abs(r) - size + 1);
      result.append(" ".repeat(offset));

      for (int q = -size + 1; q <= size - 1; q++) {
        for (int s = -size + 1; s <= size - 1; s++) {
          if (q + r + s == 0) {
            Tile tile = model.getTileAt(q, r);
            result.append(getCharBasedOnTile(tile)).append(" ");
          }
        }
      }
      result.append("\n");
    }
    return result.toString();
  }

  private String renderBottomHalf(List<Tile> board, int size) {
    StringBuilder result = new StringBuilder();
    for (int r = 1; r <= size - 1; r++) {
      int offset = Math.max(0, size - 1 + r) - size + 1;
      result.append(" ".repeat(offset));

      for (int q = -size + 1; q <= size - 1; q++) {
        for (int s = -size + 1; s <= size - 1; s++) {
          if (q + r + s == 0) {
            Tile tile = model.getTileAt(q, r);
            result.append(getCharBasedOnTile(tile)).append(" ");
          }
        }
      }
      result.append("\n");
    }
    return result.toString();
  }

  private char getCharBasedOnTile(Tile tile) {
    char charForTile = '_';

    if (tile.containsDisc()) {
      DiscColor color = tile.getDisc().getColor();
      if (color == DiscColor.WHITE) {
        charForTile = 'O';
      } else if (color == DiscColor.BLACK) {
        charForTile = 'X';
      }
    }
    return charForTile;
  }
}