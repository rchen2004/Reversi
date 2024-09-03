package cs3500.reversi.provider.model;

/**
 * Intrinsically defines what a disc is and what information it knows about itself.
 */
public interface GameDisc {
  /**
   * Respobsible for fetching the current color of a disc.
   * @return returns the color of the disc, black or white.
   */
  DiscColor getColor();

  /**
   * Responsible for changing the color of a singular disc on a Reversi board.
   */
  void flipDisc();
}