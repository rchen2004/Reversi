package cs3500.reversi.provider.model;

import cs3500.reversi.model.HexCell;
import cs3500.reversi.model.Player;

/**
 * The adapter class for the providers Tile class.
 */
public class AdapterTile implements Tile {

  private final HexCell cell;

  /**
   * Constructs an adapter for the providers Tile class.
   * @param cell The cell to be adapted.
   */
  public AdapterTile(HexCell cell) {
    this.cell = cell;
  }

  /**
   * Checks if the tile contains a disc.
   * @return true if the tile contains a disc, false otherwise
   */
  @Override
  public boolean containsDisc() {
    return cell.getPiece() != null;
  }

  /**
   * Places a disc on the tile.
   * @param color the color of the GameDisc to be placed
   */
  @Override
  public void placeDisc(DiscColor color) {
    if (color == DiscColor.BLACK) {
      cell.setPiece(Player.X);
    }
    if (color == DiscColor.WHITE) {
      cell.setPiece(Player.O);
    }
  }

  /**
   * Returns the disc on the tile.
   * @return the disc on the tile
   */
  @Override
  public GameDisc getDisc() {
    return new AdapterGameDisc(cell.getPiece());
  }

  /**
   * Gets the R coordinate of the tile.
   * @return the R coordinate of the tile
   */
  @Override
  public int getR() {
    return cell.getR();
  }

  /**
   * Gets the Q coordinate of the tile.
   * @return the Q coordinate of the tile
   */
  @Override
  public int getQ() {
    return cell.getQ();
  }

  /**
   * Gets the axial coordinate of the tile.
   * @return the axial coordinate of the tile
   */
  @Override
  public Coordinate getCoordinate() {
    return new Coordinate(this.getQ(), this.getR());
  }
}
