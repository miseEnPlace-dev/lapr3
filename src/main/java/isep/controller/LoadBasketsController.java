package isep.controller;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import isep.model.mapper.BasketsMapper;
import isep.utils.CSVReader;

/**
 * US 307 - Controller for loading hampers from a CSV file.
 *
 * @author Ricardo Moreira <1211285@isep.ipp.pt>
 */
public class LoadBasketsController {
  private String filename;

  public LoadBasketsController(String filename) {
    this.filename = filename;
  }

  public List<Map<String, String>> readData() throws FileNotFoundException {
    CSVReader csvreader = new CSVReader(filename);
    return csvreader.read();
  }

  public Map<Integer, Map<String, Map<Integer, Double>>> mapBaskets(List<Map<String, String>> data) {
    return BasketsMapper.toPlan(data);
  }
}
