package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a basic implmeentation of HexVersi.
 * A version of Othello or Reversi played on a Hexagonal board.
 *
 * @field rows the number of rows in the board
 * @field gameStarted whether the game has started
 * @field hexCellMap a map of the cells in the board
 * @field currentPlayer the current player
 * @field consecutivePasses the number of consecutive passes
 * @field gameOver whether the game is over
 */
public class BasicReversi implements Reversi, Observer {
  private final int rows;

  private boolean gameStarted;

  private final Map<Integer, HexCell[]> hexCellMap;

  private List<Observer> listeners = new ArrayList<>();

  private Player currentPlayer = Player.O;

  private int consecutivePasses;

  private boolean gameOver;


  /**
   * Constructs a game of Reversi with 5 rows.
   */
  public BasicReversi() {
    this.rows = 5;
    this.hexCellMap = new HashMap<>();
  }

  /**
   * Constructs a BasicReversi object with final variables of its # of rows and board type.
   *
   * @param i the number of rows in the board
   * @throws IllegalArgumentException if the desired row amount is less than 5 or is not an odd
   *                                  number. 5 is the minimum amount or rows to have a playable
   *                                  game.
   */
  // INVARIANT: 'rows' is a positive odd integer greater than or equal to 5,
  // representing the number of rows in the Reversi game board.
  public BasicReversi(int i) throws IllegalArgumentException {
    if (i >= 5) {
      if (i % 2 == 1) {
        this.rows = i;
      } else {
        throw new IllegalArgumentException("Invalid size");
      }
    } else {
      throw new IllegalArgumentException("Invalid size");
    }
    this.hexCellMap = new HashMap<>();
  }

  /**
   * Creates a new instance of BasicReversi by copying the state of another BasicReversi model.
   * This constructor performs a deep copy of the hexagonal grid, ensuring
   * that each HexCell is duplicated
   * to prevent shared references between the original and the new instance.
   *
   * @param model The BasicReversi model to be copied.
   */
  public BasicReversi(BasicReversi model) {
    this.rows = model.rows;
    this.gameStarted = model.gameStarted;
    this.hexCellMap = new HashMap<>();
    for (Map.Entry<Integer, HexCell[]> entry : model.hexCellMap.entrySet()) {
      int key = entry.getKey();
      HexCell[] originalArray = entry.getValue();
      HexCell[] newArray = new HexCell[originalArray.length];

      for (int i = 0; i < originalArray.length; i++) {
        newArray[i] = new HexCell(originalArray[i]);
      }

      this.hexCellMap.put(key, newArray);
    }
    this.currentPlayer = model.currentPlayer;
    this.consecutivePasses = model.consecutivePasses;
    this.gameOver = model.gameOver;
  }

  /**
   * Initializes the board which is a hashmap with the key representing the row, and array
   * for the amount of HexCells.
   * First for loop initializes the top half of the hexagon, adding one more cell until it reaches
   * the middle row. The middle row is than sent as a size equal to the amount of rows, this will
   * create a valid geometrical hexagon.
   * Second loop will then initialize the bottom half of the hexagon taking away one cell from the
   * middle row length until finished.
   */
  private void initBoard() {
    int length = (int) Math.ceil((double) rows / 2);
    for (int i = 0; i < length; i++) {
      HexCell[] array = new HexCell[length + i];
      hexCellMap.put(i, array);
      if (i == length - 1) {
        HexCell[] max = new HexCell[rows];
        hexCellMap.put(i, max);
        break;
      }
    }
    for (int i = 1; i <= length - 1; i++) {
      HexCell[] array = new HexCell[rows - i];
      hexCellMap.put(length + i - 1, array);
    }
  }


  /**
   * Loops through the Hashmap getting each array that represents a row.
   * It will create hexcell object with its expected
   * Q and R coordinates and place it in its correct
   * position in that row.
   */
  private void fillCells() {
    int length = (int) Math.floor((double) rows / 2);
    for (int i = 0; i < rows; i++) {
      HexCell[] array = this.hexCellMap.get(i);
      int count = -i;
      int negativeMid = -length;
      for (int j = 0; j < array.length; j++) {
        if (i >= length) {
          array[j] = new HexCell(negativeMid + j, (i - length));
          continue;
        }
        array[j] = new HexCell(count, (i - length));
        count++;
      }
    }
  }

  /**
   * Starts our reversi game and sets gameStarted to true.
   * Calls helper methods to initialize the board, put in the cells, and also set the starting
   * pieces in the desired cells.
   *
   * @throws IllegalStateException if the game has already started
   */
  public void startGame() throws IllegalStateException {
    if (gameStarted) {
      throw new IllegalStateException("Game already started");
    }
    this.initBoard();
    this.fillCells();
    this.setInitialPieces();
    gameStarted = true;
    consecutivePasses = 0;
    gameOver = false;
    notice();
  }

  /**
   * Sets the initial pieces on the board.
   * Creates a ring of alternating pieces around the middle cell of the hexagon.
   */
  private void setInitialPieces() {
    this.setCell(0, -1, Player.X);
    this.setCell(1, -1, Player.O);
    this.setCell(1, 0, Player.X);
    this.setCell(0, 1, Player.O);
    this.setCell(-1, 1, Player.X);
    this.setCell(-1, 0, Player.O);
  }

  /**
   * Helper that determines whether the r coordinate is a valid row on our hexagon.
   * Then determines if the q exists within the bounds of that specific row.
   *
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private void validInput(int q, int r) throws IllegalArgumentException {
    int length = (int) Math.floor((double) rows / 2);
    if (r > length || r < -length) {
      throw new IllegalArgumentException("Invalid R");
    }
    if (r <= 0) {
      if (q < (r * -1) - length || q > length) {
        throw new IllegalArgumentException("Invalid Q");
      }
    } else {
      if (q < -1 * length || q > (r * -1) + length) {
        throw new IllegalArgumentException("Invalid Q");
      }
    }
  }

  /**
   * Helper that simply takes in a coordinate and a player to change a cells current gamepiece to
   * the desired player.
   *
   * @param q      the q coordinate
   * @param r      the r coordinate
   * @param player the player
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private void setCell(int q, int r, Player player) {
    validInput(q, r);
    HexCell cell = findCell(q, r);
    cell.setPiece(player);
  }

  /**
   * Uses desired q and r coordinates to extract the cell from the game board.
   * Uses the r extract the row from the hashmap.
   * Loops through the row looking for the desired cell.
   * Returns the desired cell.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @return the cell with the matching axial coordinates
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   * @throws IllegalArgumentException if the given q and r did not yield a cell from the board.
   */
  public HexCell findCell(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int length = (int) Math.floor((double) rows / 2);
    HexCell[] cells = hexCellMap.get(r + length);
    for (HexCell cell : cells) {
      if (cell.getR() == r && cell.getQ() == q) {
        return cell; // Return the cell with the matching axial coordinates
      }
    }
    throw new IllegalArgumentException("No matching cell found."); // If no matching cell is found
  }

  /**
   * Finds a cell on the board using a desired s coordinate.
   * Similar to findcell but makes easier searching of cells by their s
   * by taking in s as an input.
   *
   * @param s the s coordinate
   * @param r the r coordinate
   * @return the cell with the matching axial coordinates
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private HexCell findCellByS(int s, int r) throws IllegalArgumentException {
    validInput(-r - s, r);
    int length = (int) Math.floor((double) rows / 2);
    HexCell[] cells = hexCellMap.get(r + length);
    for (HexCell cell : cells) {
      if (s == cell.getS()) {
        return cell; // Return the cell with the matching axial coordinates
      }
    }
    throw new IllegalArgumentException("No Matching Cell"); // If no matching cell is found
  }

  /**
   * Converts position in hashmap(key in map and index in array) to axial coordinates.
   *
   * @param column the index
   * @param row    the row
   * @return the axial coordinates
   */
  private int[] converter(int column, int row) {
    int q;
    int r;
    int length = (int) Math.floor((double) rows / 2);
    if (row >= length) {
      q = (-1 * length) + column;
    } else {
      q = -1 * row + column;
    }
    r = row - length;
    return new int[]{q, r};
  }

  /**
   * Determines if a move is valid to the left of a desired cell for the current player.
   * Loops backwards in the array checking if the first cell to the left is not empty or
   * is already filled by a piece from the same player making the move invalid.
   * It will keep looping if the cells aren't empty and are occupied by the opposing player.
   * If it finds a piece that belong to the current player the move is valid.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @return whether the move is valid
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private boolean validMovesBackwards(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int length = (int) Math.floor((double) rows / 2);
    int row = r + length;
    int column;
    boolean back = false;
    if (row >= length) {
      column = q + length;
    } else {
      column = q + row;
    }
    if (column > 0) {
      for (int i = column - 1; i >= 0; i--) {
        int[] nums = converter(i, row);
        HexCell curr = findCell(nums[0], nums[1]);
        if (curr.getPiece() == null) {
          break;
        }
        if (curr.getPiece().equals(currentPlayer)) {
          if (i != column - 1) {
            back = true;
          }
          break;
        } else {
          if (i == 0) {
            break;
          }
        }
      }
    }
    return back;
  }

  /**
   * Uses similar logic to the validMovesBackwards method.
   * Called only if move is valid in a certain direction.
   * Since move is valid, loops through and any pieces of the opposing players are set
   * to the current player's pieces.
   * Stops at the first instance that a piece belongs to the current player.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private void flipBackwards(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int length = (int) Math.floor((double) rows / 2);
    int row = r + length;
    int column;
    if (row >= length) {
      column = q + length;
    } else {
      column = q + row;
    }
    if (column > 0) {
      for (int i = column - 1; i >= 0; i--) {
        int[] nums = converter(i, row);
        HexCell curr = findCell(nums[0], nums[1]);
        if (curr.getPiece() == null) {
          break;
        }
        if (curr.getPiece().equals(currentPlayer)) {
          break;
        } else {
          curr.setPiece(currentPlayer);
        }
      }
    }
  }

  /**
   * Determines if a move is valid to the right of a desired cell for the current player.
   * Loops forwards in the array checking if the first cell to the right is not empty or
   * is already filled by a piece from the same player making the move invalid.
   * It will keep looping if the cells aren't empty and are occupied by the opposing player.
   * If it finds a piece that belong to the current player the move is valid.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @return whether the move is valid
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private boolean validMovesForward(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int length = (int) Math.floor((double) rows / 2);
    int row = r + length;
    int column;
    boolean forward = false;
    if (row >= length) {
      column = q + length;
    } else {
      column = q + row;
    }
    HexCell[] desiredRow = hexCellMap.get(row);
    if (column >= 0) {
      for (int i = column + 1; i < desiredRow.length; i++) {
        int[] nums = converter(i, row);
        HexCell curr = findCell(nums[0], nums[1]);
        if (curr.getPiece() == null) {
          break;
        }
        if (curr.getPiece().equals(currentPlayer)) {
          if (i != column + 1) {
            forward = true;
          }
          break;
        }
      }
    }
    return forward;
  }

  /**
   * Called only if move is valid in a certain direction.
   * Since move is valid, loops through and any pieces of the opposing players are set
   * to the current player's pieces.
   * Stops at the first instance that a piece belongs to the current player.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private void flipForward(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int length = (int) Math.floor((double) rows / 2);
    int row = r + length;
    int column;
    if (row >= length) {
      column = q + length;
    } else {
      column = q + row;
    }
    HexCell[] desiredRow = hexCellMap.get(row);
    if (column >= 0) {
      for (int i = column + 1; i < desiredRow.length; i++) {
        int[] nums = converter(i, row);
        HexCell curr = findCell(nums[0], nums[1]);
        if (curr.getPiece() == null) {
          break;
        }
        if (curr.getPiece().equals(currentPlayer)) {
          break;
        } else {
          curr.setPiece(currentPlayer);
        }
      }
    }
  }

  /**
   * Determines if a move is valid in the q coordinate upwards
   * direction of a desired cell for the current player.
   * Loops diagonally through the board checking if the first cell with same q in the
   * row above is not empty or is already filled by a piece from the same player
   * making the move invalid.
   * It will keep looping if the cells aren't empty and are occupied by the opposing player.
   * If it finds a piece that belong to the current player the move is valid.
   * Traverses diagonally by lowering the r coordinate, therefore moving upwards through the board.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @return whether the move is valid
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private boolean validQUp(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCell(q, r - count);
      } catch (IllegalArgumentException e) {
        return false;
      }
      if (curr == null || curr.getPiece() == null) {
        return false;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        return count != 1;
      } else {
        count++;
      }
    }
  }

  /**
   * Called only if move is valid in a certain direction.
   * Since move is valid, loops through and any pieces of the opposing players are set
   * to the current player's pieces.
   * Stops at the first instance that a piece belongs to the current player.
   * Traverses diagonally by lowering the r coordinate, therefore moving upwards through the board.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private void flipQUp(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCell(q, r - count);
      } catch (IllegalArgumentException e) {
        break;
      }
      if (curr == null || curr.getPiece() == null) {
        break;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        break;
      } else {
        curr.setPiece(currentPlayer);
        count++;
      }
    }
  }

  /**
   * Determines if a move is valid in the q coordinate downwards
   * direction of a desired cell for the current player.
   * Loops diagonally through the board checking if the first cell with the same q in the row
   * below is not empty or is already filled by a piece from the same player
   * making the move invalid.
   * It will keep looping if the cells aren't empty and are occupied by the opposing player.
   * If it finds a piece that belong to the current player the move is valid.
   * Traverses diagonally by increasing the r coordinate, therefore moving downwards
   * through the board.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @return whether the move is valid
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private boolean validQDown(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCell(q, r + count);
      } catch (IllegalArgumentException e) {
        return false;
      }
      if (curr == null || curr.getPiece() == null) {
        return false;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        return count != 1;
      } else {
        count++;
      }
    }
  }

  /**
   * Called only if move is valid in a certain direction.
   * Since move is valid, loops through and any pieces of the opposing players are set
   * to the current player's pieces.
   * Stops at the first instance that a piece belongs to the current player.
   * Traverses diagonally by increasing the r coordinate, therefore
   * moving downwards through the board.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private void flipQDown(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCell(q, r + count);
      } catch (IllegalArgumentException e) {
        break;
      }
      if (curr == null || curr.getPiece() == null) {
        break;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        break;
      } else {
        curr.setPiece(currentPlayer);
        count++;
      }
    }
  }

  /**
   * Determines if a move is valid in the s coordinate upwards
   * direction of a desired cell for the current player.
   * Loops diagonally through the board checking if the first cell with the same s in the row
   * above is not empty or is already filled by a piece from the same player
   * making the move invalid.
   * It will keep looping if the cells aren't empty and are occupied by the opposing player.
   * If it finds a piece that belong to the current player the move is valid.
   * Traverses diagonally by decreasing the r coordinate, therefore moving upwards
   * through the board.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @return whether the move is valid
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private boolean validSUp(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCellByS((-q) - r, r - count);
      } catch (IllegalArgumentException e) {
        return false;
      }
      if (curr == null || curr.getPiece() == null) {
        return false;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        return count != 1;
      } else {
        count++;
      }
    }
  }

  /**
   * Called only if move is valid in a certain direction.
   * Since move is valid, loops through and any pieces of the opposing players are set
   * to the current player's pieces.
   * Stops at the first instance that a piece belongs to the current player.
   * Traverses diagonally by deceasing the r coordinate, therefore
   * moving upwards through the board.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private void flipSUp(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCellByS((-q) - r, r - count);
      } catch (IllegalArgumentException e) {
        break;
      }
      if (curr == null || curr.getPiece() == null) {
        break;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        break;
      } else {
        curr.setPiece(currentPlayer);
        count++;
      }
    }
  }

  /**
   * Determines if a move is valid in the s coordinate downwards
   * direction of a desired cell for the current player.
   * Loops diagonally through the board checking if the first cell with the same s in the row
   * below is not empty or is already filled by a piece from the same player
   * making the move invalid.
   * It will keep looping if the cells aren't empty and are occupied by the opposing player.
   * If it finds a piece that belong to the current player the move is valid.
   * Traverses diagonally by increasing the r coordinate, therefore moving upwards
   * through the board.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @return whether the move is valid
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private boolean validSDown(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCellByS((-q) - r, r + count);
      } catch (IllegalArgumentException e) {
        return false;
      }
      if (curr == null || curr.getPiece() == null) {
        return false;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        return count != 1;
      } else {
        count++;
      }
    }
  }

  /**
   * Called only if move is valid in a certain direction.
   * Since move is valid, loops through and any pieces of the opposing players are set
   * to the current player's pieces.
   * Stops at the first instance that a piece belongs to the current player.
   * Traverses diagonally by increasing the r coordinate, therefore
   * moving downwards through the board.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  private void flipSDown(int q, int r) throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCellByS((-q) - r, r + count);
      } catch (IllegalArgumentException e) {
        break;
      }
      if (curr == null || curr.getPiece() == null) {
        break;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        break;
      } else {
        curr.setPiece(currentPlayer);
        count++;
      }
    }
  }

  /**
   * Sets the piece of the desired cell based of the given coordinates to the current player.
   * Valid moves will flip opponents players pieces to the current players pieces if the move is
   * valid in that direction.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalStateException    if the game is already over
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   * @throws IllegalStateException    if the move is not allowable (i.e. the move is not
   *                                  logically possible) or a piece already exists in that
   *                                  position.
   */
  public void movePiece(int q, int r) throws IllegalStateException, IllegalArgumentException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
    if (gameOver) {
      throw new IllegalStateException("Game is over");
    }
    validInput(q, r);
    HexCell curr = findCell(q, r);
    if (curr == null || curr.getPiece() != null) {
      throw new IllegalStateException("Can't Put Piece on Another Piece");
    }
    if (isValidMove(q, r)) {
      setCell(q, r, currentPlayer);
      currentPlayer = currentPlayer == Player.X ? Player.O : Player.X;
      consecutivePasses = 0;
      this.notice();
    } else {
      throw new IllegalStateException("Not a valid move");
    }
  }

  /**
   * For a given cell, uses all the previous helpers to check all the different directions for
   * a possible move.
   * A subsequent valid move leads to a call to flip helpers to flip opponents pieces for those
   * valid moves.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @return valid move for given cell
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  public boolean isValidMove(int q, int r) throws IllegalArgumentException {
    boolean valid = false;
    if (validMovesBackwards(q, r)) {
      valid = true;
      flipBackwards(q, r);
    }
    if (validMovesForward(q, r)) {
      valid = true;
      flipForward(q, r);
    }
    if (validQUp(q, r)) {
      flipQUp(q, r);
      valid = true;
    }
    if (validQDown(q, r)) {
      flipQDown(q, r);
      valid = true;
    }
    if (validSUp(q, r)) {
      flipSUp(q, r);
      valid = true;
    }
    if (validSDown(q, r)) {
      flipSDown(q, r);
      valid = true;
    }
    return valid;
  }

  /**
   * For a given cell, uses all the previous helpers to check all the different directions for
   * a possible move.
   * This one does not flip the pieces and is solely used to check if this is a valid move.
   *
   * @param q the q coordinate
   * @param r the r coordinate
   * @return valid move for given cell
   * @throws IllegalArgumentException if the r coordinate is out of bounds for our current board or
   *                                  if the q coordinate is out of bounds for that desired row.
   */
  public boolean isValidMoves(int q, int r) throws IllegalArgumentException {
    boolean valid = false;
    if (validMovesBackwards(q, r)) {
      valid = true;
    }
    if (validMovesForward(q, r)) {
      valid = true;
    }
    if (validQUp(q, r)) {
      valid = true;
    }
    if (validQDown(q, r)) {
      valid = true;
    }
    if (validSUp(q, r)) {
      valid = true;
    }
    if (validSDown(q, r)) {
      valid = true;
    }
    return valid;
  }


  /**
   * Passes the move to the next player.
   * If passMove is called consecutively it will end the game.
   *
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the game is already over
   */
  public void passMove() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
    if (gameOver) {
      throw new IllegalStateException("Game is over");
    }
    currentPlayer = currentPlayer == Player.X ? Player.O : Player.X;
    this.notice();
    consecutivePasses++;
    if (consecutivePasses == 2) {
      gameOver = true;
    }
  }

  /**
   * Returns the game board for the current game.
   *
   * @throws IllegalStateException if the game has not started
   */
  public Map<Integer, HexCell[]> getBoard() {
    Map<Integer, HexCell[]> originalMap = this.hexCellMap;
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
    Map<Integer, HexCell[]> copyMap = new HashMap<>();
    for (Map.Entry<Integer, HexCell[]> entry : originalMap.entrySet()) {
      Integer key = entry.getKey();
      HexCell[] originalValue = entry.getValue();
      HexCell[] copyValue = Arrays.copyOf(originalValue, originalValue.length);
      copyMap.put(key, copyValue);
    }

    return copyMap;
  }

  /**
   * Returns the number of rows for the current game.
   */
  public int getRows() {
    return rows;
  }

  /**
   * Determines if the board is filled, with no more moves.
   *
   * @return whether the game is over
   * @throws IllegalStateException if the game has not started
   */
  public boolean isGameOver() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
    if (gameOver) {
      return true;
    }
    int count = 0;
    for (int i = 0; i < rows; i++) {
      HexCell[] array = hexCellMap.get(i);
      for (HexCell hexCell : array) {
        if (hexCell.getPiece() == null) {
          count++;
        }
      }
    }
    if (count == 0) {
      gameOver = true;
      return true;
    }
    return false;
  }

  /**
   * Returns the piece of the current player.
   *
   * @throws IllegalStateException if the game has not started
   */
  public Player getCurrentPlayer() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
    return currentPlayer;
  }

  /**
   * Returns the score (amount of pieces belonging to the given player).
   *
   * @param p the player
   * @return the score of the player
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the game is already over
   */
  public int getScore(Player p) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
    if (gameOver) {
      throw new IllegalStateException("Game is over");
    }
    int score = 0;
    for (int i = 0; i < rows; i++) {
      HexCell[] array = hexCellMap.get(i);
      for (HexCell hexCell : array) {
        if (hexCell.getPiece() != null && hexCell.getPiece().equals(p)) {
          score++;
        }
      }
    }
    return score;
  }

  /**
   * Returns a list of valid moves for the current player.
   *
   * @return a list of valid moves for the current player
   */
  public List<AxialCoordinate> getValidMoves() {
    List<AxialCoordinate> validMoves = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      HexCell[] arrayCopy = Arrays.copyOf(hexCellMap.get(i), hexCellMap.get(i).length);
      for (HexCell hexCell : arrayCopy) {
        if (hexCell.getPiece() == null) {
          if (isValidMoves(hexCell.getQ(), hexCell.getR())) {
            int q = hexCell.getQ();
            int r = hexCell.getR();
            validMoves.add(new AxialCoordinate(q, r));
          }
        }
      }
    }
    return validMoves;
  }

  /**
   * Adds a feature listener to the model.
   *
   * @param features the feature listener
   */
  @Override
  public void addFeatureListener(Observer features) {
    this.listeners.add(features);
  }

  /**
   * Notifies all the feature listeners of a change in the model.
   */
  @Override
  public void notice() {
    for (Observer features : this.listeners) {
      features.notice();
    }
  }

  /**
   * Collects the pieces that need to be flipped in the backwards direction.
   * @param q the q coordinate
   * @param r the r coordinate
   * @param list the list of pieces to be flipped
   * @throws IllegalArgumentException if the coordinates are not in bound
   */
  private void collectBackwards(int q, int r, List<HexCell> list)
          throws IllegalArgumentException {
    validInput(q, r);
    int length = (int) Math.floor((double) rows / 2);
    int row = r + length;
    int column;
    if (row >= length) {
      column = q + length;
    } else {
      column = q + row;
    }
    if (column > 0) {
      for (int i = column - 1; i >= 0; i--) {
        int[] nums = converter(i, row);
        HexCell curr = findCell(nums[0], nums[1]);
        if (curr.getPiece() == null) {
          break;
        }
        if (curr.getPiece().equals(currentPlayer)) {
          break;
        } else {
          list.add(curr);
        }
      }
    }
  }

  /**
   * Collects the pieces that need to be flipped in the forward direction.
   * @param q the q coordinate
   * @param r the r coordinate
   * @param list the list of pieces to be flipped
   * @throws IllegalArgumentException if the coordinates are not in bound
   */
  private void collectForward(int q, int r, List<HexCell> list) throws
          IllegalArgumentException {
    validInput(q, r);
    int length = (int) Math.floor((double) rows / 2);
    int row = r + length;
    int column;
    if (row >= length) {
      column = q + length;
    } else {
      column = q + row;
    }
    HexCell[] desiredRow = hexCellMap.get(row);
    if (column >= 0) {
      for (int i = column + 1; i < desiredRow.length; i++) {
        int[] nums = converter(i, row);
        HexCell curr = findCell(nums[0], nums[1]);
        if (curr.getPiece() == null) {
          break;
        }
        if (curr.getPiece().equals(currentPlayer)) {
          break;
        } else {
          list.add(curr);
        }
      }
    }
  }

  /**
   * Collects the pieces that need to be flipped in the q upwards direction.
   * @param q the q coordinate
   * @param r the r coordinate
   * @param list the list of pieces to be flipped
   * @throws IllegalArgumentException if the coordinates are not in bound
   */
  private void collectQUp(int q, int r, List<HexCell> list)
          throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCell(q, r - count);
      } catch (IllegalArgumentException e) {
        break;
      }
      if (curr == null || curr.getPiece() == null) {
        break;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        break;
      } else {
        list.add(curr);
        count++;
      }
    }
  }

  /**
   * Collects the pieces that need to be flipped in the q downwards direction.
   * @param q the q coordinate
   * @param r the r coordinate
   * @param list the list of pieces to be flipped
   * @throws IllegalArgumentException if the coordinates are not in bound
   */
  private void collectQDown(int q, int r, List<HexCell> list)
          throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCell(q, r + count);
      } catch (IllegalArgumentException e) {
        break;
      }
      if (curr == null || curr.getPiece() == null) {
        break;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        break;
      } else {
        list.add(curr);
        count++;
      }
    }
  }

  /**
   * Collects the pieces that need to be flipped in the s upwards direction.
   * @param q the q coordinate
   * @param r the r coordinate
   * @param list the list of pieces to be flipped
   * @throws IllegalArgumentException if the coordinates are not in bound
   */
  private void collectSUp(int q, int r, List<HexCell> list) throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCellByS((-q) - r, r - count);
      } catch (IllegalArgumentException e) {
        break;
      }
      if (curr == null || curr.getPiece() == null) {
        break;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        break;
      } else {
        list.add(curr);
        count++;
      }
    }
  }

  /**
   * Collects the pieces that need to be flipped in the s downwards direction.
   * @param q the q coordinate
   * @param r the r coordinate
   * @param list the list of pieces to be flipped
   * @throws IllegalArgumentException if the coordinates are not in bound
   */
  private void collectSDown(int q, int r, List<HexCell> list) throws IllegalArgumentException {
    validInput(q, r);
    int count = 1;
    while (true) {
      HexCell curr;
      try {
        curr = findCellByS((-q) - r, r + count);
      } catch (IllegalArgumentException e) {
        break;
      }
      if (curr == null || curr.getPiece() == null) {
        break;
      }
      if (curr.getPiece().equals(currentPlayer)) {
        break;
      } else {
        list.add(curr);
        count++;
      }
    }
  }

  /**
   * Collects all the pieces that need to be flipped for a given cell.
   * @param q the q coordinate
   * @param r the r coordinate
   * @return the list of pieces to be flipped
   * @throws IllegalArgumentException if the coordinates are not in bound
   */
  public List<HexCell> collectFlips(int q, int r) throws IllegalArgumentException {
    List<HexCell> list = new ArrayList<>();
    if (validMovesBackwards(q, r)) {
      collectBackwards(q, r, list);
    }
    if (validMovesForward(q, r)) {
      collectForward(q, r, list);
    }
    if (validQUp(q, r)) {
      collectQUp(q, r, list);
    }
    if (validQDown(q, r)) {
      collectQDown(q, r, list);
    }
    if (validSUp(q, r)) {
      collectSUp(q, r, list);
    }
    if (validSDown(q, r)) {
      collectSDown(q, r, list);
    }
    return list;
  }
}
