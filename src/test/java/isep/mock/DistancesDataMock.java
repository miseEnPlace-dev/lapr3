package isep.mock;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import isep.utils.CSVReader;

public class DistancesDataMock {
  private final static String DISTANCES_SAMPLE_FILE_PATH = "src/test/resources/distancesSample.csv";
  private final static String DISTANCES_BIG_FILE_PATH = "data/big/distancias_big.csv";
  private final static String DISTANCES_SMALL_FILE_PATH = "data/small/distancias_small.csv";

  public List<Map<String, String>> mockDistancesDataFromSampleFile() throws FileNotFoundException {
    CSVReader reader = new CSVReader(DISTANCES_SAMPLE_FILE_PATH);
    return reader.read();
  }

  public List<Map<String, String>> mockDistancesDataWithBigFile() throws FileNotFoundException {
    CSVReader reader = new CSVReader(DISTANCES_BIG_FILE_PATH);
    return reader.read();
  }

  public List<Map<String, String>> mockDistancesDataWithSmallFile() throws FileNotFoundException {
    CSVReader reader = new CSVReader(DISTANCES_SMALL_FILE_PATH);
    return reader.read();
  }
}
