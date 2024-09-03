package cs3500.reversi.provider.view;

import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import cs3500.reversi.provider.model.ROReversiModel;
import cs3500.reversi.provider.model.DiscColor;
import cs3500.reversi.provider.model.Coordinate;

/**
 * The HexagonalReversiPanel class represents the panel used to display a hexagonal grid
 * for the Reversi game. It extends JPanel and provides methods for drawing the hexagonal grid,
 * handling mouse events, and responding to key events.
 */
public class HexagonalReversiPanel extends JPanel {
  private final ROReversiModel model;
  private final int boardSize;
  private final List<ViewFeatures> featuresListeners;
  private final List<Hexagon> hexes = new ArrayList<>(6);
  private Hexagon highlighted;

  /**
   * Constructs a HexagonalReversiPanel with the specified Reversi model.
   *
   * @param model The Reversi model to be displayed.
   */
  public HexagonalReversiPanel(ROReversiModel model) {
    this.model = model;
    this.boardSize = model.getSize();
    this.featuresListeners = new ArrayList<>();
    setBackground(Color.DARK_GRAY);
    MouseListener mouseListener = new MouseListener();
    this.addMouseListener(mouseListener);
    KeyListener keyListener = new KeyListener();
    this.addKeyListener(keyListener);
    this.highlighted = null;
    setFocusable(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 500);
  }

  @Override
  public void paintComponent(Graphics g) {
    int width = getWidth();
    int height = getHeight();

    Graphics2D g2d = (Graphics2D) g;
    Point origin = new Point(width / 2, height / 2);

    for (Hexagon hex : hexes) {
      hex.resetHighlighted();
    }

    // Update the position of the highlighted hexagon if it exists
    if (highlighted != null) {
      for (Hexagon hex : hexes) {
        if (hex.contains(highlighted.getX(), highlighted.getY())) {
          hex.highlight();
          highlighted = hex;
          break;
        }
      }
    }

    g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
    double radiusBasedOnWidth = width / (1.75 * (2 * boardSize - 1));
    double radiusBasedOnHeight = (double) height / (3 * boardSize - 1);

    drawHexGrid(g2d, origin, 2 * boardSize - 1, Math.min(radiusBasedOnHeight, radiusBasedOnWidth));
  }

  private void drawHexGrid(Graphics g, Point origin, int size, double radius) {
    hexes.clear();
    double thirtyD = Math.toRadians(30);
    double xOffset = Math.cos(thirtyD) * (radius) - 1;
    double yOffset = Math.sin(thirtyD) * (radius) - 1;
    int half = size / 2;

    for (int row = 0; row < size; row++) {
      int cols = size - java.lang.Math.abs(row - half);
      for (int col = 0; col < cols; col++) {
        int x = (int) (origin.x + xOffset * (col * 2 + 1 - cols));
        int y = (int) (origin.y + yOffset * (row - half) * 3);
        int q = boardSize - cols + col;
        if (row > boardSize - 1) {
          int diff = row - boardSize;
          q = boardSize - cols + col - 1 - diff;
        }

        int r = row - boardSize + 1;
        drawHex(g, x, y, (int) radius, q, r);
      }
    }
  }

  private void drawHex(Graphics g, int x, int y, int radius, int q, int r) {
    Graphics2D g2d = (Graphics2D) g;
    Hexagon hex = new Hexagon(x, y, radius, q, r);
    hexes.add(hex);

    for (Hexagon cell : hexes) {
      cell.resetHighlighted();
    }
    if (highlighted != null && highlighted.contains(x, y)) {
      hex.highlight();
    }
    if (hex.isHighlighted()) {
      hex.drawHex(g2d, 0, Color.CYAN, true);
    } else {
      hex.drawHex(g2d, 0, Color.LIGHT_GRAY, true);
    }
    hex.drawHex(g2d, 2, Color.BLACK, false);
    setDiscColor(q, r, g2d, hex);
  }

  private void setDiscColor(int q, int r, Graphics2D g2d, Hexagon hex) {
    if (model.getTileAt(q, r).containsDisc()) {
      DiscColor color = model.getDiscAt(q, r).getColor();
      if (color.equals(DiscColor.WHITE)) {
        g2d.setColor(Color.WHITE);
        hex.drawDisc(g2d);
      } else {
        g2d.setColor(Color.BLACK);
        hex.drawDisc(g2d);
      }
    }
  }

  public void addFeaturesListener(ViewFeatures features) {
    this.featuresListeners.add(Objects.requireNonNull(features));
  }

  private class MouseListener extends MouseInputAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      Point physicalP = e.getPoint();
      Hexagon clickedHex = null;

      for (Hexagon hex : hexes) {
        if (hex.contains(physicalP)) {
          clickedHex = hex;
          break;
        }
      }
      handleHighlight(clickedHex);
      repaint();
    }

    private void handleHighlight(Hexagon clickedHex) {
      // Toggle highlight when the same hexagon is clicked again
      if (clickedHex != null && clickedHex.equals(highlighted)) {
        clickedHex.resetHighlighted();
        highlighted = null;
      } else {
        for (Hexagon hex : hexes) {
          hex.resetHighlighted();
        }

        if (clickedHex != null) {
          clickedHex.highlight();
          highlighted = clickedHex;
          for (ViewFeatures listener : featuresListeners) {
            listener.clickHex(clickedHex);
          }
        } else {
          highlighted = null;
        }
      }
    }
  }

  private class KeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      int input = e.getKeyCode();
      if (input == KeyEvent.VK_P) {
        passListener();
      } else if (input == KeyEvent.VK_M) {
        moveListener();
      } else if (input == KeyEvent.VK_Q) {
        System.out.println("Game Quit");
        quitListener();
      }
    }
  }

  private void quitListener() {
    for (ViewFeatures listener : featuresListeners) {
      listener.quit();
    }
  }

  private void passListener() {
    for (ViewFeatures listener : featuresListeners) {
      listener.pass();
    }
  }

  private void moveListener() {
    for (ViewFeatures listener : featuresListeners) {
      int q = highlighted.getQ();
      int r = highlighted.getR();
      listener.move(new Coordinate(q, r));
    }
  }
}