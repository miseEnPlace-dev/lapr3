package isep.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import isep.shared.exceptions.InvalidHourFormatException;

public class HourTest {
  /**
   * Tests if the class Hour is correctly implemented.
   */
  @Test
  public void testHourIsCreatedCorrectly() throws InvalidHourFormatException {
    System.out.println("testHourIsCreatedCorrectly");
    Hour hour = new Hour(12, 30);
    assertTrue(hour.getHour() == 12);
    assertTrue(hour.getMinute() == 30);
  }

  /**
   * Tests if the class Hour is correctly implemented.
   */
  @Test
  public void testInvalidHours() {
    System.out.println("testInvalidHours");
    assertThrows(InvalidHourFormatException.class, () -> new Hour(24, 30));
    assertThrows(InvalidHourFormatException.class, () -> new Hour(0, 60));
    assertThrows(InvalidHourFormatException.class, () -> new Hour(0, -1));
    assertThrows(InvalidHourFormatException.class, () -> new Hour(-1, 0));
  }

  /**
   * Tests the various constructors.
   */
  @Test
  public void testMinutesConstructor() throws InvalidHourFormatException {
    System.out.println("testMinutesConstructor");
    assertEquals((new Hour(750)).getHour(), 12);
    assertEquals((new Hour(0)).getHour(), 0);
    assertEquals((new Hour(1439)).getMinute(), 59);
    assertThrows(InvalidHourFormatException.class, () -> new Hour(-1));
    assertThrows(InvalidHourFormatException.class, () -> new Hour(1440));
  }

  @Test
  public void testStringConstructor() throws InvalidHourFormatException {
    System.out.println("testStringConstructor");
    Hour h1 = new Hour(8, 30);
    Hour h2 = new Hour(12, 0);
    assertEquals((new Hour("8h30")), h1);
    assertEquals((new Hour("12h00")), h2);
    assertThrows(InvalidHourFormatException.class, () -> new Hour("24h30"));
    assertThrows(InvalidHourFormatException.class, () -> new Hour("00h60"));
    assertThrows(InvalidHourFormatException.class, () -> new Hour("00h-1"));
  }

  /**
   * Tests is between method.
   */
  @Test
  public void testIsBetween() throws InvalidHourFormatException {
    System.out.println("testIsBetween");
    Hour hour = new Hour(12, 30);
    Hour start = new Hour(12, 00);
    Hour end = new Hour(13, 00);
    assertTrue(hour.isBetween(start, end));
    assertFalse(start.isBetween(hour, end));
    assertTrue(start.isBetween(start, end));
  }

  /**
   * Tests clone method.
   */
  @Test
  public void testHourClone() throws InvalidHourFormatException {
    System.out.println("testHourClone");
    Hour hour = new Hour(12, 30);
    Hour clone = hour.clone();
    assertEquals(hour, clone);
  }

  /**
   * Tests compareTo method.
   */
  @Test
  public void testHourCompareTo() throws InvalidHourFormatException {
    System.out.println("testHourCompareTo");
    Hour hour = new Hour(12, 30);
    Hour hour2 = new Hour(12, 30);
    Hour hour3 = new Hour(12, 31);
    Hour hour4 = new Hour(13, 00);
    assertEquals(hour.compareTo(hour2), 0);
    assertEquals(hour.compareTo(hour3), -1);
    assertEquals(hour.compareTo(hour4), -30);
  }

  /**
   * Tests equals method.
   */
  @Test
  public void testHourEquals() throws InvalidHourFormatException {
    System.out.println("testHourEquals");
    Hour hour = new Hour(12, 30);
    Hour hour2 = new Hour(12, 30);
    Hour hour3 = new Hour(12, 31);
    Hour hour4 = new Hour(13, 00);
    assertTrue(hour.equals(hour2));
    assertFalse(hour.equals(hour3));
    assertFalse(hour.equals(hour4));
  }

  /**
   * Tests toString method.
   */
  @Test
  public void testHourToString() throws InvalidHourFormatException {
    System.out.println("testHourToString");
    Hour hour = new Hour(12, 30);
    assertEquals(hour.toString(), "12h30");
  }
}
