package isep.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import isep.ui.utils.Utils;

public class UtilsTest {

  @Test
  public void testReadLineFromConsole() {
    String input = "test";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    assertEquals(input, Utils.readLineFromConsole("prompt"));
  }

  @Test
  public void testReadLineFromConsoleWithValidation() {
    String input = "test";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    assertEquals(input, Utils.readLineFromConsoleWithValidation("prompt"));
  }

  @Test
  public void testReadIntegerFromConsole() {
    String input = "1";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    assertEquals(1, Utils.readIntegerFromConsole("prompt"));
  }

  @Test
  public void testReadPositiveIntegerFromConsole() {
    String input = "1";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    assertEquals(1, Utils.readPositiveIntegerFromConsole("prompt"));

  }

  @Test
  public void testNonNegativeIntegerFromConsole() {
    String input = "0";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    assertEquals(0, Utils.readNonNegativeIntegerFromConsole("prompt"));
  }

  @Test
  public void testReadDoubleFromConsole() {
    String input = "1.0";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    assertEquals(1.0, Utils.readDoubleFromConsole("prompt"));
  }

  @Test
  public void testReadDateFromConsole() {
    String input = "01/01/2020";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    try {
      assertEquals(formatter.parse(input), Utils.readDateFromConsole("prompt"));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  public void testReadDateInFutureConsole() {
    String input = "01/01/3000";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    try {
      assertEquals(formatter.parse(input), Utils.readDateInFutureFromConsole("prompt"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
