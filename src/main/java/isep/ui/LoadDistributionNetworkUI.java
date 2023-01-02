package isep.ui;

import isep.controller.LoadDistributionNetworkController;
import isep.model.DistributionNetwork;
import isep.model.store.EntityStore;
import isep.ui.utils.Utils;
import isep.utils.CSVReader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import isep.controller.App;

public class LoadDistributionNetworkUI implements Runnable {
  LoadDistributionNetworkController controller;
  DistributionNetwork network;
  EntityStore entityStore;
  CSVReader csvReader;

  public LoadDistributionNetworkUI() {
    entityStore = App.getInstance().getCompany().getEntityStore();
  }

  @Override
  public void run() {

    try {
      List<Map<String, String>> data = insertData();
      controller = new LoadDistributionNetworkController(entityStore, data);
      network = controller.loadDistributionNetwork();

    } catch (FileNotFoundException e) {
      System.out.println("\nFile not found!");
    }

  }

  /*
   * Asks to insert the file path
   *
   * @returns the file path
   */
  private List<Map<String, String>> insertData() throws FileNotFoundException {
    String filePath = Utils.readLineFromConsole("\nFile path: ");
    csvReader = new CSVReader(filePath);

    return csvReader.read();

  }

}
