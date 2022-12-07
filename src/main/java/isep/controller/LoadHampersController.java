package isep.controller;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import isep.utils.CSVReader;

/**
 * US 307 - Controller for loading hampers from a CSV file.
 *
 * @author Ricardo Moreira <1211285@isep.ipp.pt>
 */
public class LoadHampersController {
  public String filename;

  public LoadHampersController(String filename) {
    this.filename = filename;
  }

  public List<Map<String, String>> readData() throws FileNotFoundException {
    CSVReader csvreader = new CSVReader(filename);
    return csvreader.read();
  }

  public void loadHampers(List<Map<String, String>> data) {
    // map has a variable number of products

    for (Map<String, String> map : data) {

    }


  }
}
