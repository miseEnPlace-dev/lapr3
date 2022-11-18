package isep.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {
  private CustomScanner scanner;

  private final String TEMP_SEPARATOR = ";";

  public CSVReader(String fileName) throws FileNotFoundException {
    scanner = new CustomScanner(fileName);
    System.out.printf("  Reading %s...%n", fileName);
  }

  public String[] readHeader() {
    return scanner.nextLine().split(",");
  }

  public List<Map<String, String>> read() {
    String[] header = readHeader();
    List<Map<String, String>> list = new ArrayList<>(100000);

    while (scanner.hasNextLine()) {
      HashMap<String, String> map = new HashMap<>(header.length * 3);

      String line = scanner.nextLine();
      String separator = ",";

      // handle commas inside quotes
      if (line.contains("\"")) {
        boolean insideString = false;
        separator = TEMP_SEPARATOR;
        int length = line.length();
        for (int i = 0; i < length; i++) {
          if (line.charAt(i) == '"')
            insideString = !insideString;
          if (line.charAt(i) == ',' && !insideString)
            line = line.substring(0, i) + TEMP_SEPARATOR + line.substring(i + 1);
        }
        line = line.replaceAll("\"", ""); // removes double quotas
      }

      String[] lineFields = line.split(separator);

      for (int i = 0; i < header.length; i++)
        map.put(header[i], lineFields[i]);

      list.add(map);
    }

    return list;
  }
}
