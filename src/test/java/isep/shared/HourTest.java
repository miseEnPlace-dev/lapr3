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
    Hour hour = new Hour(12, 30);
    assertTrue(hour.getHour() == 12);
    assertTrue(hour.getMinute() == 30);
  }

  /**
   * Tests if the class Hour is correctly implemented.
   */
  @Test
  public void testInvalidHours() {
    assertThrows(InvalidHourFormatException.class, () -> new Hour(24, 30));
    assertThrows(InvalidHourFormatException.class, () -> new Hour(0, 60));
    assertThrows(InvalidHourFormatException.class, () -> new Hour(0, -1));
  }

  /**
   * Tests the various constructors.
   */
  @Test
  public void testMinutesConstructor() throws InvalidHourFormatException {
    assertEquals((new Hour(750)).getHour(), 12);
    assertEquals((new Hour(0)).getHour(), 0);
    assertEquals((new Hour(1439)).getMinute(), 59);
    assertThrows(InvalidHourFormatException.class, () -> new Hour(-1));
    assertThrows(InvalidHourFormatException.class, () -> new Hour(1440));
  }

  @Test
  public void testStringConstructor() throws InvalidHourFormatException {
    assertEquals((new Hour(12, 30)).getHour(), 12);
    assertThrows(InvalidHourFormatException.class, () -> new Hour("24:30"));
    assertThrows(InvalidHourFormatException.class, () -> new Hour("00:60"));
    assertThrows(InvalidHourFormatException.class, () -> new Hour("00:-1"));
  }

  /**
   * Tests is between method.
   */
  @Test
  public void testIsBetween() throws InvalidHourFormatException {
    Hour hour = new Hour(12, 30);
    Hour start = new Hour(12, 00);
    Hour end = new Hour(13, 00);
    assertTrue(hour.isBetween(start, end));
    assertFalse(start.isBetween(hour, end));
    assertTrue(start.isBetween(start, end));
  }
}
