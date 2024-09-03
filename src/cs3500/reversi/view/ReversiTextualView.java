package cs3500.reversi.view;

import cs3500.reversi.model.HexCell;
import cs3500.reversi.model.Reversi;

/**
 * A simple text-based rendering of the game board for a BasicReversi game.
 */
public class ReversiTextualView implements TextualView {
  private final Reversi reversi;

  public ReversiTextualView(Reversi reversi) {
    this.reversi = reversi;
  }

  /**
   * Displays the Reversi Model as a formatted string.
   * Overrides built-in toString.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < reversi.getRows(); i++) {
      HexCell[] array = reversi.getBoard().get(i);
      for (int j = 0; j < reversi.getRows() - array.length; j++) {
        sb.append(" ");
      }
      for (int k = 0; k < array.length; k++) {
        if (array[k].getPiece() != null) {
          sb.append(array[k].getPiece().toString());
          sb.append(" ");
          continue;
        }
        sb.append("_");
        sb.append(" ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}