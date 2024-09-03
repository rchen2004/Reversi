import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.HexCell;
import cs3500.reversi.model.Player;

/**
 * This class contains JUnit tests for the HexCell class, testing its methods and behavior.
 */
public class TestHexcell {
  HexCell cell1 = new HexCell(0, 0);
  HexCell cell2 = new HexCell(1, 2);
  HexCell cell3 = new HexCell(3, 4);
  HexCell cell4 = new HexCell(5, 6);
  HexCell sameCell = new HexCell(0, 0);

  @Test
  public void testGetQ() {
    Assert.assertEquals(cell1.getQ(), 0);
    Assert.assertEquals(cell2.getQ(), 1);
    Assert.assertEquals(cell3.getQ(), 3);
    Assert.assertEquals(cell4.getQ(), 5);
  }

  @Test
  public void testGetR() {
    Assert.assertEquals(cell1.getR(), 0);
    Assert.assertEquals(cell2.getR(), 2);
    Assert.assertEquals(cell3.getR(), 4);
    Assert.assertEquals(cell4.getR(), 6);
  }

  @Test
  public void testGetS() {
    Assert.assertEquals(cell1.getS(), 0);
    Assert.assertEquals(cell2.getS(), -3);
    Assert.assertEquals(cell3.getS(), -7);
    Assert.assertEquals(cell4.getS(), -11);
  }

  @Test
  public void testSetPieceAndGetPiece() {
    Assert.assertNull(cell1.getPiece());
    cell1.setPiece(Player.O);
    Assert.assertEquals(cell1.getPiece(), Player.O);
    cell1.setPiece(Player.X);
    Assert.assertEquals(cell1.getPiece(), Player.X);
  }

  @Test
  public void testEqualCell() {
    Assert.assertEquals(cell1, sameCell);
    Assert.assertNotEquals(cell1, cell2);
    Assert.assertNotEquals(cell1, cell3);
    Assert.assertNotEquals(cell1, cell4);
    Assert.assertNotEquals(cell2, cell3);
    Assert.assertNotEquals(cell2, cell4);
    Assert.assertNotEquals(cell3, cell4);
  }
}