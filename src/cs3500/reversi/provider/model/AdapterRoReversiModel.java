package cs3500.reversi.provider.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.AxialCoordinate;
import cs3500.reversi.model.HexCell;
import cs3500.reversi.model.Reversi;

/**
 * The adapter class for the providers ROReversiModel class.
 */
public class AdapterRoReversiModel implements ROReversiModel {

  private final Reversi model;

  /**
   * Constructs an adapter for the providers ROReversiModel class.
   * @param model The model to be adapted.
   */
  public AdapterRoReversiModel(Reversi model) {
    this.model = model;
  }

  /**
   * Gets the number of hexes along one edge of the board.
   * @return the number of hexes
   */
  @Override
  public int getSize() {
    return (int) Math.ceil(model.getRows() / 2) + 1;
  }

  /**
   * Gets the score for a player.
   * @param color the color that the player is playing as
   * @return the number of discs that have the player's color
   */
  @Override
  public int getScore(DiscColor color) {
    return 0;
  }

  /**
   * Gets the turn number, indicating the current player's turn.
   * @return The current turn number
   */
  @Override
  public int getTurn() {
    return 0;
  }

  /**
   * Retrieves a list of tiles that contain discs of the specified color.
   * @param color The color of the discs to retrieve
   * @return A list of tiles with discs of DiscColor
   */
  @Override
  public List<Tile> getHexesOfColor(DiscColor color) {
    return null;
  }

  /**
   * Gets the color of the disc located at the specified Q, R, and S coordinates on the board.
   * @param q The Q-coordinate of the tile
   * @param r The R-coordinate of the tile
   * @return The color of the disc at the specified coordinates
   */
  @Override
  public GameDisc getDiscAt(int q, int r) {
    return new AdapterGameDisc(model.findCell(q, r).getPiece());
  }

  /**
   * Retrieves the tile at the specified Q, R, and S coordinates on the board.
   * @param q The Q-coordinate of the tile
   * @param r The R-coordinate of the tile
   * @return The tile at the specified coordinates
   */
  @Override
  public Tile getTileAt(int q, int r) {
    HexCell cell = model.findCell(q, r);
    return new AdapterTile(cell);
  }

  /**
   * Creates and returns a copy of the game board.
   * @return a list of tiles representing the entire game board
   */
  @Override
  public List<Tile> getBoard() {
    return null;
  }

  /**
   * Checks if a move for color is valid at coordinates q and r.
   * @param q     the q coordinate
   * @param r     the r coordinate
   * @param color the current player's color
   * @return true if the move is valid, false otherwise
   */
  @Override
  public boolean isValidMove(int q, int r, DiscColor color) {
    return model.isValidMoves(q,r);
  }

  /**
   * Checks if a valid move exists anywhere on the board for color.
   * @param color the current player's color
   * @return true if a move exists, false otherwise
   */
  @Override
  public boolean doesValidMoveExist(DiscColor color) {
    return !model.getValidMoves().isEmpty();
  }

  /**
   * Determines if the game is over.
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean gameOver() {
    return model.isGameOver();
  }

  /**
   * Gets the all possible moves for the current player.
   * @param color The color of the player
   * @return A list of tiles representing possible moves for the player
   */
  @Override
  public List<Tile> getPossibleMoves(DiscColor color) {
    List<AxialCoordinate> coordinates = model.getValidMoves();
    List<Tile> tiles = new ArrayList<>();
    for (AxialCoordinate coord : coordinates) {
      tiles.add(new AdapterTile(model.findCell(coord.getQ(), coord.getR())));
    }
    return tiles;
  }

  /**
   * Gets the discs that would be flipped if a disc of the specified color were placed at the
   * specified coordinates.
   * @param q     The Q-coordinate of the tile
   * @param r     The R-coordinate of the tile
   * @param color The color of the disc to be placed
   * @return A list of tiles representing discs that would be flipped
   */
  @Override
  public List<Tile> getDiscsToFlip(int q, int r, DiscColor color) {
    List<HexCell> collected = model.collectFlips(q, r);
    List<Tile> tiles = new ArrayList<>();
    for (HexCell cell : collected) {
      tiles.add(new AdapterTile(cell));
    }
    return tiles;
  }

  /**
   * Gets the color of the current player.
   * @return The color of the current player
   */
  @Override
  public DiscColor colorBasedOnTurn() {
    return null;
  }
}
