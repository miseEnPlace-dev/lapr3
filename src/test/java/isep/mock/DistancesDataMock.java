package isep.mock;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import isep.utils.CSVReader;

public class DistancesDataMock {
  private final static String DISTANCES_SAMPLE_FILE_PATH = "src/test/resources/distancesSample.csv";

  public List<Map<String, String>> mockDistancesDataFromSampleFile() throws FileNotFoundException {
    CSVReader reader = new CSVReader(DISTANCES_SAMPLE_FILE_PATH);
    return reader.read();
  }
}
