package cs3500.reversi.provider.model;

import cs3500.reversi.model.Player;


/**
 * The adapter class for the providers GameDisc class.
 */
public class AdapterGameDisc implements GameDisc {
  private final Player cell;

  /**
   * Constructs an adapter for the providers GameDisc class.
   * @param cell The cell to be adapted.
   */
  public AdapterGameDisc(Player cell) {
    this.cell = cell;
  }

  /**
   * Gets the color of the disc.
   * @return the color of the disc
   */
  @Override
  public DiscColor getColor() {
    if (cell == Player.O) {
      return DiscColor.WHITE;
    }
    else {
      return DiscColor.BLACK;
    }
  }

  /**
   * Flips the disc to the other color.
   */
  @Override
  public void flipDisc() {
    return;
  }
}
