package isep.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {
  private CustomScanner scanner;

  private final String DEFAULT_SEPARATOR = ",";
  private final String TEMP_SEPARATOR = ";";

  public CSVReader(String fileName) throws FileNotFoundException {
    scanner = new CustomScanner(fileName);
    System.out.printf("  Reading %s...%n", fileName);
  }

  public String[] readHeader() {
    String line = scanner.nextLine();
    String[] handled = handleQuotes(line);
    return handled[1].split(handled[0]);
  }

  private String[] handleQuotes(String line) {
    String separator = DEFAULT_SEPARATOR;
    if (line.contains("\"")) {
      boolean insideQuotes = false;
      separator = TEMP_SEPARATOR;
      int length = line.length();
      for (int i = 0; i < length; i++) {
        if (line.charAt(i) == '"')
          insideQuotes = !insideQuotes;
        if (line.charAt(i) == DEFAULT_SEPARATOR.charAt(0) && !insideQuotes)
          line = line.substring(0, i) + TEMP_SEPARATOR + line.substring(i + 1);
      }
      // remove all double quotes
      line = line.replaceAll("\"", ""); // removes double quotes
    }
    return new String[] { separator, line };
  }

  public List<Map<String, String>> read() {
    String[] header = readHeader();
    List<Map<String, String>> list = new ArrayList<>();

    while (scanner.hasNextLine()) {
      HashMap<String, String> map = new HashMap<>();

      String[] handled = handleQuotes(scanner.nextLine());
      String separator = handled[0];
      String line = handled[1];

      String[] lineFields = line.split(separator);

      for (int i = 0; i < header.length; i++)
        map.put(header[i], lineFields[i]);

      list.add(map);
    }

    return list;
  }
}
