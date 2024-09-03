package cs3500.reversi.model;

/**
 * Represents a player in the game of Reversi.
 */
public enum Player {
  X("X"), O("O");

  /**
   * Enum made cannot be changed and is private.
   */
  private final String disp;

  /**
   * Constructor for a PLayer enum object.
   *
   * @param disp represnts a X or O for player piece
   */
  Player(String disp) {
    this.disp = disp;
  }

  /**
   * Used to represent a Player/Player piece.
   *
   * @return String representation of this enum or Player
   */
  @Override
  public String toString() {
    return disp;
  }

}