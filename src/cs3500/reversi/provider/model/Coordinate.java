package cs3500.reversi.provider.model;

/**
 * The Coordinate class represents a set of coordinates in a three-dimensional space.
 * In the context of hexagonal grids, these coordinates are often denoted as (q, r, s),
 * where q, r, and s are the three axial coordinates.
 */
public class Coordinate {
  private int q;
  private int r;

  /**
   * Constructs a new Coordinate instance with the specified axial coordinates (q, r).
   *
   * @param q The q-coordinate.
   * @param r The r-coordinate.
   */
  public Coordinate(int q, int r) {
    this.q = q;
    this.r = r;
  }

  /**
   * Returns the q-coordinate of this coordinate.
   *
   * @return The q-coordinate.
   */
  public int getQ() {
    return q;
  }

  /**
   * Returns the r-coordinate of this coordinate.
   *
   * @return The r-coordinate.
   */
  public int getR() {
    return r;
  }

  @Override
  public String toString() {
    return "(q = " + q + ", r = " + r + ")";
  }
}
