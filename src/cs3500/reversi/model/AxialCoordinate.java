package cs3500.reversi.model;

import java.util.Objects;

/**
 * Represents a coordinate in an axial coordinate system used in games like Hex-based games.
 * The axial coordinate system uses three coordinates: q, r, and s.
 * The relationship between q, r, and s is defined as q + r + s = 0.
 */
public class AxialCoordinate {

  /**
   * The horizontal coordinate representing the column in the axial coordinate system.
   */
  private final int q;

  /**
   * The vertical coordinate representing the row in the axial coordinate system.
   */
  private final int r;

  /**
   * The third coordinate in the axial coordinate system, derived from q and r.
   * In an axial coordinate system, s = -q - r.
   */
  private final int s;

  /**
   * Constructs an AxialCoordinate with the specified q and r coordinates.
   *
   * @param q The horizontal coordinate representing the column.
   * @param r The vertical coordinate representing the row.
   */
  public AxialCoordinate(int q, int r) {
    this.q = q;
    this.r = r;
    this.s = (q * -1) - r;
  }

  /**
   * Constructor used to avoid mutation and create a copy of an axial coordinate.
   *
   * @param original The original AxialCoordinate to create a copy from.
   */
  public AxialCoordinate(AxialCoordinate original) {
    this.q = original.q;
    this.r = original.r;
    this.s = original.s;
  }

  /**
   * Gets the horizontal coordinate representing the column.
   *
   * @return The q coordinate.
   */
  public int getQ() {
    return q;
  }

  /**
   * Gets the vertical coordinate representing the row.
   *
   * @return The r coordinate.
   */
  public int getR() {
    return r;
  }

  /**
   * Gets the third coordinate derived from q and r in the axial coordinate system.
   * In an axial coordinate system, s = -q - r.
   *
   * @return The s coordinate.
   */
  public int getS() {
    return s;
  }

  /**
   * Checks if this AxialCoordinate is equal to another object.
   * Two axial coordinates are considered equal if their q and r coordinates are equal.
   *
   * @param obj The object to compare with this AxialCoordinate.
   * @return True if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    return (q == ((AxialCoordinate) obj).getQ())
            &&
            (r == ((AxialCoordinate) obj).getR());
  }

  /**
   * Generates a hash code for this AxialCoordinate.
   * The hash code is based on the q and r coordinates.
   *
   * @return The hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(q, r);
  }
}
