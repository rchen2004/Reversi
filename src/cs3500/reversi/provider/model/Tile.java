package cs3500.reversi.provider.model;

/**
 * Represents a hexagonal tile on a Reversi game board.
 * Each tile can contain a game disc and has coordinates on the board.
 */
public interface Tile {

  /**
   * Checks to see if the tile contains a GameDisc.
   *
   * @return true if a GameDisc is present, false otherwise
   */
  boolean containsDisc();

  /**
   * Places a GameDisc of the specified color on the tile.
   *
   * @param color the color of the GameDisc to be placed
   */
  void placeDisc(DiscColor color);

  /**
   * Gets the color of the GameDisc on the tile.
   *
   * @return the color of the GameDisc
   */
  GameDisc getDisc();

  /**
   * Gets the "r" coordinate of the tile.
   *
   * @return the "r" coordinate
   */
  int getR();

  /**
   * Gets the "q" coordinate of the tile.
   *
   * @return the "q" coordinate
   */
  int getQ();

  /**
   * Gets the Coordinate object related to the tile.
   * @return the Coordinate object
   */
  Coordinate getCoordinate();
}