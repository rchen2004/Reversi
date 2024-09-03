package cs3500.reversi.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import cs3500.reversi.model.HexCell;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReadOnlyReversi;


/**
 * This class represents a panel for rendering a Reversi game board using Swing.
 * It extends JPanel and implements ViewFeatures.
 * It intakes mouse and key events.
 */
public class JReversiPanel extends JPanel {

  private final ReadOnlyReversi model;

  private Map<HexCell, Point> centers;

  /**
   * We'll allow an arbitrary number of listeners for our events...even if
   * we happen right now to only expect a single listener.
   */
  private final List<ViewFeatures> featuresListeners;

  private HexCell selected;

  /**
   * Constructs a JReversiPanel with the given Reversi model.
   * Initializes the featuresListeners as a list in preparation for possible features.
   * The map to record the current physical coordinate centers of the cells is first initialized
   * as an empty hashmap.
   * MouseEventsListener and KeyListener are replaced with our overridden ones.
   *
   * @param model The Reversi model to be rendered.
   */
  public JReversiPanel(ReadOnlyReversi model) {
    this.featuresListeners = new ArrayList<>();
    this.model = model;
    this.centers = new HashMap<>();
    MouseEventsListener listener = new MouseEventsListener();
    KeyListener listner = new MyKeyListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    this.addKeyListener(listner);
    setFocusable(true);
  }


  /**
   * Adds a listener for view features to this panel.
   *
   * @param features The listener to be added.
   */
  public void addFeaturesListener(ViewFeatures features) {
    this.featuresListeners.add(Objects.requireNonNull(features));
  }

  /**
   * Advances the game by one step.
   */
  public void advance() {
    if (model.isGameOver()) {
      this.quit();
    }
    this.repaint();
  }

  private void quit() {
    for (ViewFeatures listener : JReversiPanel.this.featuresListeners) {
      listener.quit();
    }
  }

  /**
   * Draws a hexagon on the graphics context with the specified attributes.
   *
   * @param g2d        The graphics context on which to draw.
   * @param x          The x-coordinate of the hexagon's center.
   * @param y          The y-coordinate of the hexagon's center.
   * @param sideLength The length of the hexagon's sides.
   * @param color      The color to fill the hexagon.
   */
  public void drawHexagon(Graphics g2d, int x, int y, int sideLength, Color color) {
    int[] xPoints = new int[6];
    int[] yPoints = new int[6];
    // Calculate the coordinates of the pointy-top hexagon points
    for (int i = 0; i < 6; i++) {
      double angle = i * Math.PI / 3.0;
      xPoints[i] = x + (int) (sideLength * Math.sin(angle));
      yPoints[i] = y + (int) (sideLength * Math.cos(angle));
    }
    Polygon hexagon = new Polygon(xPoints, yPoints, 6);
    // Set the stroke (line width) to make the lines thicker
    Graphics2D g2 = (Graphics2D) g2d;
    Stroke originalStroke = g2.getStroke();
    g2.setStroke(new BasicStroke(7)); // You can adjust the thickness as needed
    g2.setColor(Color.black);
    g2.drawPolygon(hexagon);
    g2.setStroke(originalStroke); // Reset the stroke to its original value
    g2.setColor(color);
    g2.fillPolygon(hexagon);
  }


  /**
   * Draws a circle on the graphics context with the specified attributes.
   *
   * @param g2d     The graphics context on which to draw.
   * @param centerX The x-coordinate of the circle's center.
   * @param centerY The y-coordinate of the circle's center.
   * @param radius  The radius of the circle.
   * @param color   The color to fill the circle.
   */
  public void drawCircle(Graphics g2d, int centerX, int centerY, int radius, Color color) {
    g2d.setColor(color);
    g2d.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
  }

  /**
   * Overidden paintComponent method. Paints the component, rendering the Reversi game board.
   *
   * @param g The graphics context.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    Color britishRacingGreen = new Color(0, 66, 37);
    g2d.setColor(britishRacingGreen);
    g2d.fillRect(0, 0, getWidth(), getHeight());
    // Calculate the maximum size the board can be based on the frame size
    int maxSize = Math.min(getWidth(), getHeight());
    // Calculate the size of each hexagon based on the number of rows and columns
    int sideLength = (int) (maxSize / (1.5 * Math.sqrt(3) * model.getRows()));
    int length = (int) Math.ceil((double) model.getRows() / 2);
    int y = 90;
    for (int i = 0; i < length; i++) {
      y += (3.0 / 2.0) * sideLength * Math.sqrt(3.0) * 0.57;
      HexCell[] array = model.getBoard().get(i);
      makingHexagonsAndPieces(g2d, sideLength, y, array);
    }
    for (int i = 1; i <= length - 1; i++) {
      y += (3.0 / 2.0) * sideLength * Math.sqrt(3.0) * 0.57;
      HexCell[] array = model.getBoard().get(length + i - 1);
      makingHexagonsAndPieces(g2d, sideLength, y, array);
    }
  }

  /**
   * Draws hexagons and corresponding pieces on the graphics context for a given row.
   *
   * @param g2d        The graphics context.
   * @param sideLength The length of the hexagon's sides.
   * @param y          The y-coordinate of the row.
   * @param array      An array of HexCells representing the row.
   */
  private void makingHexagonsAndPieces(Graphics2D g2d, int sideLength, int y, HexCell[] array) {
    for (HexCell cell : array) {
      int x = ((sideLength + 7) * model.getRows()) + axialToXCoordinate(sideLength, cell);
      Point center = new Point(x, y);
      if (selected != null && cell.equals(selected)) {
        drawHexagon(g2d, x, y, sideLength, Color.CYAN);
        continue;
      }
      centers.put(cell, center);
      drawHexagon(g2d, x, y, sideLength, Color.GRAY);
      if (cell.getPiece() != null) {
        if (cell.getPiece() == Player.O) {
          drawCircle(g2d, x, y, (int) (Math.sqrt(3)) * (sideLength / 2), Color.white);
        } else {
          drawCircle(g2d, x, y, (int) (Math.sqrt(3)) * (sideLength / 2), Color.black);
        }
      }
    }
  }

  /**
   * Converts axial coordinates to computer graphic x-coordinate for rendering hexagons.
   *
   * @param sideLength The length of the hexagon's sides.
   * @param cell       The HexCell with axial coordinates.
   * @return The x-coordinate for rendering the hexagon.
   */
  private static int axialToXCoordinate(int sideLength, HexCell cell) {
    return (int) (sideLength * (Math.sqrt(3) * cell.getQ() + Math.sqrt(3) / 2 * cell.getR()));
  }

  /**
   * Handles mouse events for selecting hexagons (HexCells).
   */
  private class MouseEventsListener extends MouseInputAdapter {

    /**
     * Handles mouse press events, updating the selected hexagon based on the mouse click.
     *
     * @param e The mouse event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
      // This point is measured in actual physical pixels
      Point physicalP = e.getPoint();

      int maxSize = Math.min(getWidth(), getHeight());
      int sideLength = (int) (maxSize / (1.5 * Math.sqrt(3) * model.getRows()));
      double xRange = sideLength; // Use half the side length for xRange
      double yRange = sideLength * Math.sqrt(3.0) / 2.0;
      // Use (sqrt(3)/2) times the side length for yRange
      boolean inRange = false;

      for (Map.Entry<HexCell, Point> entry : centers.entrySet()) {
        HexCell cell = entry.getKey();
        Point curr = entry.getValue();
        double xDistance = Math.abs(physicalP.getX() - curr.getX());
        double yDistance = Math.abs(physicalP.getY() - curr.getY());

        if (xDistance <= xRange && yDistance <= yRange) {
          System.out.println(cell.getQ() + ", " + cell.getR());
          if (!cell.equals(selected)) {
            if (cell.getPiece() == null) {
              selected = cell;
              inRange = true;
            }
          } else {
            selected = null;
            inRange = true;
          }
        }
      }

      if (!inRange) {
        selected = null;
      }

      JReversiPanel.this.repaint();
    }
  }

  /**
   * Handles key events for the GUI, allowing the player to interact with the game.
   */
  private class MyKeyListener implements KeyListener {

    /**
     * keyPresssed looks for either a press of the p key or space bar.
     * p will let the player pass and space bar allows the placement at the selected cell.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyChar() == 'p') {
        for (ViewFeatures listener : JReversiPanel.this.featuresListeners) {
          listener.passMove();
        }
      } else if (e.getKeyChar() == 'm') {
        for (ViewFeatures listener : JReversiPanel.this.featuresListeners) {
          if (selected != null) {
            listener.movePiece(selected);
            selected = null;
          }
        }
      }
    }

    /**
     * Overridden keyReleased. Action not required for this context.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
      // action not required
    }


    /**
     * Overridden keyTyped. Action not required for this context.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
      // action not required
    }

  }

}