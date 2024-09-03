package cs3500.reversi.model;

import java.util.Objects;

/**
 * Represents a cell within a hexagonal grid.
 * Each cell is identified by its axial coordinates (q, r, and s) and may contain a player's piece.
 */
public class HexCell implements Cell {

  /** The axial coordinates of the cell. */
  public final AxialCoordinate coord;

  /** The player's piece residing in this cell. */
  private Player piece;

  /**
   * Constructs a HexCell with specified axial coordinates.
   *
   * @param q The q coordinate in an axial coordinate system.
   * @param r The r coordinate in an axial coordinate system.
   */
  public HexCell(int q, int r) {
    this.coord = new AxialCoordinate(q, r);
    this.piece = null;
  }

  /**
   * Copy constructor to create a new HexCell by copying another HexCell's state.
   *
   * @param original The original HexCell to be copied.
   */
  public HexCell(HexCell original) {
    this.coord = new AxialCoordinate(original.coord);
    this.piece = original.piece; // Assuming Player has a copy constructor
  }

  /**
   * Retrieves the q coordinate of the cell.
   *
   * @return The q coordinate.
   */
  @Override
  public int getQ() {
    return coord.getQ();
  }

  /**
   * Retrieves the r coordinate of the cell.
   *
   * @return The r coordinate.
   */
  @Override
  public int getR() {
    return coord.getR();
  }

  /**
   * Retrieves the s coordinate of the cell.
   *
   * @return The s coordinate.
   */
  @Override
  public int getS() {
    return coord.getS();
  }

  /**
   * Retrieves the player's piece residing in this cell.
   *
   * @return The player's piece, or null if the cell is empty.
   */
  @Override
  public Player getPiece() {
    return this.piece;
  }

  /**
   * Sets the piece of this cell to the given player's piece.
   *
   * @param piece The player's piece to be set in this cell.
   */
  @Override
  public void setPiece(Player piece) {
    this.piece = piece;
  }

  /**
   * Compares two cells based on their axial coordinates (q, r, s) and the player's piece.
   *
   * @param obj The object to compare with this HexCell, typically another cell.
   * @return {@code true} if the cells are equal, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    HexCell otherCell = (HexCell) obj;
    return coord.equals(otherCell.coord) && Objects.equals(piece, otherCell.piece);
  }

  /**
   * Generates a hash code for this cell based on its axial coordinates
   * (q, r, s) and the player's piece.
   *
   * @return The hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(coord, piece);
  }

}
