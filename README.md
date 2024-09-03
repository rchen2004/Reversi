## **Overview**
The Reversi codebase provides a simple implementation of the classic Reversi (Othello) board game
in Java. It aims to allow users to play Reversi on a hexagonal grid and offers basic functionality
for moves, piece flipping, passing moves and win condition checking. The grid is organized using
the axial coordinate system.

## **Assumptions**

This codebase assumes users have a basic understanding of Java, the Model-View-Controller
design pattern, and the rules of the Reversi game. It provides a text-based interface,
so users are expected to run the game from a terminal or command prompt.

## Quick Start

To get started with the Basic Reversi game, follow these steps:

1. Ensure you have Java installed on your system.
2. Clone this repository or download the BasicReversi.java file to your local machine.
3. Create a main class and play out a game like this one:
* The movePiece method takes in axial coordinates to make moves.

1. Alternatively, with the Jar file attached to this project,
   simply input ``` java -jar Homework5.jar human easyai ```
   into your terminal to play a quick game against an AI.

```java
public class main {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Usage: ReversiMain <player1Type>"
              + " <player2Type> where playerType is one of:");
      System.out.println("  human - human player");
      System.out.println("  easyai - easy AI player");
      System.out.println("  hardai - hard AI player");
      System.out.println("  providerStrategy1 - provider strategy 1");
      System.out.println("  providerStrategy2 - provider strategy 2");
      System.exit(1);
    }

    String player1Type = args[0];
    String player2Type = args[1];

    Reversi model = new BasicReversi(7);
    AdapterRoReversiModel adapter = new AdapterRoReversiModel(model);
    ReversiViews view = new SimpleReversiView(model, "White Controller");
    ReversiView view2 = new HexagonalReversiView(adapter);
    ReversiViews view3 = new AdapterView(view2);

    ReversiPlayer player1 = createPlayer(model, Player.O, player1Type);
    ReversiPlayer player2 = createPlayer(model, Player.X, player2Type);

    ViewFeatures controller = new Controller(model, player1, view);
    ViewFeatures controller2 = new Controller(model, player2, view3);

    model.startGame();
    view.display(true);
    view2.display(true);
  }
}
```

## Key Components

### Reversi Interface
This Interface provides the necessary methods that will allow a Reversi class to have a
functional game.

### Cell Interface
Ths Interface provides the necessary methods that will allow interaction with a cell object.

### BasicReversi Class

The BasicReversi class is the main component that drives the game's control flow.
It contains methods for initializing the game board, performing moves that change the model,
and checking for win conditions.

### HexCell Class

The HexCell class represents individual cells on the hexagonal game board. It is responsible
for holding information about the piece (X, O, or empty) in each cell.

## Key Subcomponents
#### `initBoard()`
This method initializes the hexagonal game board by creating the necessary data structures.

#### `fillCells()`
The `fillCells()` method populates the cells on the game board with HexCell objects.

#### `setInitialPieces()`
This method sets the initial pieces for both players at the beginning of the game.

### Move Validation and Flipping Methods
The codebase includes several methods that check and perform piece flipping based on the game
rules, such as `validMovesBackwards()`, `flipBackwards()`, `validMovesForward()`,
`flipForward()`, `validQUp()`, `flipQUp()`, `validQDown()`, `flipQDown(`),
`validSUp()`, `flipSUp()`, `validSDown()`, and `flipSDown()`.

#### `movePiece()`
This method is used to handle player moves, validate them, and flip pieces if necessary.

#### `passMove()`
The `passMove()` method allows players to pass their turn, and it handles the end of the
game when consecutive passes occur.

#### `countPieces()`
The `countPieces()` method counts the number of pieces on the board for a given player.

#### `gameOver()`
The `gameOver()` method checks if the game has ended based on the win condition or when the
board is full.

## Source Organization
* `src.cs3500` holds all packages for the projecct.
* `cs3500.reversi` package contains both the model and view packages.
* `cs3500.reversi.model` package contains all classes and interfaces for components of only the model.
  * This includes both the Reversi Interface and Cell Interface.
* `cs3500.reversi.view` contains the interface and class for the view for the reversi game.
* `BasicReversi.java`: The main Java file that contains the BasicReversi class and the game logic.
* `HexCell.java`: The HexCell class representing individual cells on the game board.

The codebase is organized with these two main Java files, and the components and methods are
contained within these classes. The control flow and logic of the game are mainly driven by
the BasicReversi class.

By following this README, users should be able to navigate and understand the codebase to play
the Basic Reversi game.

## Changes for Part 2
* Added a AxialCoordinate class to represent the axial coordinates of the cells on the board.
* Split the mutable interface into a read-only and a mutable interface.
* Created strategies such as:
  * Picking the move that grants the most points
  * Picking the move that takes a corner position
  * Avoiding a move located on the border (working on)
* Added a real view that displays the game in a GUI rather in text.
  * The GUI view is a JFrame that displays the game board.
  * It intakes mouse clicks on the board and highlights cells clicked. It understands to unhighlight the highlighted ones when clicking on the same one or outside the board.
  * It also intakes keyboard inputs of "M" and "P" where M will be used to move and P will be used to pass the turn.
* Created a mock class to ensure the strategies are iterating through the hashmap correctly for each strategy.
* Added getValidMoves which returns a list of valid moves for the current player.

## Changes for Part 3
* Added a controller class that handles the interaction between the model and the view.
* Added a ViewFeatures interface that contains the methods that the controller class implements.
* Added a controller test class that tests the controller class.
* Added a controller mock class that tests the controller class.
* Added a ReversiPlayer interface that contains the methods that the HumanPlayer and AIPlayer class implements.
* Added HumanPlayer class that allows the user to play the game and choose moves.
* Added AIPlayer class that allows the user to play against an AI and have the AI calculate its moves.
* Updated the main to take in 2 arguments on the player types.
* Updated the main to produce 2 views, one for each player.

## Changes for Part 4
* We did not have customers so all changes we made were based on incorporating our providers view.
* Incorporated providers view implementations into our view.
  * Their view works and is the second player view.
  * Our Original Controller works alongside their view.
  * Only issue that remains is the error message that doesn't pop up and simply throws an exception.
  * Provider strategy is also incorporated and can be of use if argument provided is "providerstrategy1" or "providerstrategy2".
    * providerstrategy2 will use the strategy that focuses on taking corners and then the move that grants the most points.
    * providerstrategy1 will use the strategy that focuses on taking the move that grants the most points.


