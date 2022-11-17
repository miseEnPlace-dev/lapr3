package isep.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class CustomScannerTest {
  private static final String CSV_FILE_PATH = "./src/test/resources/test.csv";
  private static final String TXT_FILE_PATH = "./src/test/resources/scanner.txt";

  @Test
  public void testNext() throws FileNotFoundException {
    CustomScanner scanner = new CustomScanner(CSV_FILE_PATH);

    assertEquals("key1,key2,key3", scanner.next());
  }

  @Test
  public void testNextLine() throws FileNotFoundException {
    CustomScanner scanner = new CustomScanner(CSV_FILE_PATH);

    assertEquals("key1,key2,key3", scanner.nextLine());
    assertEquals("123,456,789", scanner.nextLine());
  }

  @Test
  public void testNextInt() throws FileNotFoundException {
    CustomScanner scanner = new CustomScanner(TXT_FILE_PATH);
    assertEquals(1, scanner.nextInt());
    assertEquals(2, scanner.nextInt());
    assertEquals(2, scanner.nextInt());
    assertEquals(3, scanner.nextInt());
  }

  @Test
  public void testHasNextLine() throws FileNotFoundException {
    CustomScanner scanner = new CustomScanner(CSV_FILE_PATH);
    assertEquals(true, scanner.hasNextLine());
    scanner.nextLine();
    assertEquals(true, scanner.hasNextLine());
    scanner.nextLine();
    assertEquals(true, scanner.hasNextLine());
    scanner.nextLine();
    assertEquals(false, scanner.hasNextLine());
  }
}
