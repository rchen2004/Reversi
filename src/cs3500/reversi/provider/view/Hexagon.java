package cs3500.reversi.provider.view;

import java.awt.Polygon;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.util.Objects;

/**
 * The Hexagon class represents a hexagon shape in a graphical context. It extends
 * the Polygon class and provides methods for drawing a hexagon, including highlighting
 * and drawing a disc within the hexagon.
 */
public class Hexagon extends Polygon {
  private static final int SIDES = 6;
  private Point center;
  private int radius;
  private boolean highlighted;
  private final int q;
  private final int r;

  /**
   * Constructs a Hexagon with the specified center, radius, and axial coordinates.
   *
   * @param center The center point of the hexagon.
   * @param radius The radius of the hexagon.
   * @param q      The q coordinate on the axial coordinate system.
   * @param r      The r coordinate on the axial coordinate system.
   */
  public Hexagon(Point center, int radius, int q, int r) {
    npoints = SIDES;
    xpoints = new int[SIDES];
    ypoints = new int[SIDES];
    this.center = center;
    this.radius = radius;
    this.highlighted = false;
    updatePoints();
    this.q = q;
    this.r = r;
  }

  /**
   * Constructs a Hexagon with the specified coordinates and radius.
   *
   * @param x      The x-coordinate of the center.
   * @param y      The y-coordinate of the center.
   * @param radius The radius of the hexagon.
   * @param q      The q coordinate on the axial coordinate system.
   * @param r      The r coordinate on the axial coordinate system.
   */
  public Hexagon(int x, int y, int radius, int q, int r) {
    this(new Point(x, y), radius, q, r);
  }

  /**
   * Gets the q coordinate of the hexagon.
   *
   * @return The q coordinate.
   */
  public int getQ() {
    return q;
  }

  /**
   * Gets the r coordinate of the hexagon.
   *
   * @return The r coordinate.
   */
  public int getR() {
    return r;
  }

  /**
   * Checks if the hexagon is highlighted.
   *
   * @return {@code true} if the hexagon is highlighted, {@code false} otherwise.
   */
  public boolean isHighlighted() {
    return highlighted;
  }

  /**
   * Highlights the hexagon.
   */
  public void highlight() {
    highlighted = true;
  }

  /**
   * Resets the highlight state of the hexagon.
   */
  public void resetHighlighted() {
    highlighted = false;
  }


  /**
   * Gets the x-coordinate of the hexagon's center.
   *
   * @return The x-coordinate of the center.
   */
  public int getX() {
    return center.x;
  }

  /**
   * Gets the y-coordinate of the hexagon's center.
   *
   * @return The y-coordinate of the center.
   */
  public int getY() {
    return center.y;
  }

  private double findAngle(double fraction) {
    int rotation = 90;
    return fraction * Math.PI * 2 + Math.toRadians((rotation + 180) % 360);
  }

  private Point findPoint(double angle) {
    int x = (int) (center.x + Math.cos(angle) * radius);
    int y = (int) (center.y + Math.sin(angle) * radius);

    return new Point(x, y);
  }

  private void updatePoints() {
    Point[] points = new Point[SIDES];
    for (int p = 0; p < SIDES; p++) {
      double angle = findAngle((double) p / SIDES);
      Point point = findPoint(angle);
      xpoints[p] = point.x;
      ypoints[p] = point.y;
      points[p] = point;
    }
  }

  /**
   * Draws the hexagon with the specified line thickness, fill color, and fill style.
   *
   * @param g             The Graphics2D object to draw on.
   * @param lineThickness The thickness of the hexagon's border.
   * @param fillColor     The fill color of the hexagon.
   * @param filled        Whether to fill the hexagon.
   */
  public void drawHex(Graphics2D g, int lineThickness, Color fillColor, boolean filled) {
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    // Store before changing
    Stroke originalStroke = g.getStroke();
    Color prevColor = g.getColor();
    g.setColor(fillColor);

    // Create a stroke
    Stroke hexagonStroke = new BasicStroke(lineThickness,
            BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);

    // Apply stroke
    g.setStroke(hexagonStroke);

    if (filled) {
      g.fillPolygon(xpoints, ypoints, npoints);
    } else {
      g.drawPolygon(xpoints, ypoints, npoints);
    }

    if (highlighted && filled) {

      g.setColor(Color.CYAN);
      g.fillPolygon(xpoints, ypoints, npoints);

      g.setColor(Color.BLACK);
      g.setStroke(new BasicStroke(lineThickness + 1));
      g.drawPolygon(xpoints, ypoints, npoints);
    }

    // Clean up when done
    g.setColor(prevColor);
    g.setStroke(originalStroke);
  }

  /**
   * Draws a disc within the hexagon.
   *
   * @param g The Graphics2D object to draw on.
   */
  public void drawDisc(Graphics2D g) {
    // Enable anti-aliasing for smoother edges
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Increase the size of the oval for better quality
    int discRadius = (int) (radius * 0.4);
    int discDiameter = 2 * discRadius;
    double discX = center.x - (radius / 2.4);
    double discY = center.y - (radius / 2.4);
    g.fillOval((int) discX, (int) discY, discDiameter, discDiameter);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Hexagon otherHex = (Hexagon) obj;
    return this.q == otherHex.q && this.r == otherHex.r;
  }

  @Override
  public int hashCode() {
    return Objects.hash(q, r);
  }
}