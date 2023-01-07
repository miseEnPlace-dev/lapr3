package isep.ui;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import isep.controller.App;
import isep.controller.LoadDistributionNetworkController;
import isep.model.DistributionNetwork;
import isep.model.store.EntityStore;
import isep.ui.utils.Utils;
import isep.utils.CSVReader;

public class LoadDistributionNetworkUI implements Runnable {
  LoadDistributionNetworkController controller;
  DistributionNetwork network;
  EntityStore entityStore;
  CSVReader csvReader;

  public LoadDistributionNetworkUI() {
    entityStore = App.getInstance().getCompany().getEntityStore();
    network = App.getInstance().getCompany().getDistributionNetwork();
  }

  @Override
  public void run() {

    try {
      List<Map<String, String>> data = insertDataDistances();
      entityStore = insertDataEntities();
      controller = new LoadDistributionNetworkController(entityStore, data);
      network = controller.loadDistributionNetwork();

      App.getInstance().getCompany().setDistributionNetwork(network);
      App.getInstance().getCompany().setEntityStore(entityStore);
    } catch (FileNotFoundException e) {
      System.out.println("\nFile not found!");
    }
  }

  /*
   * Asks to insert the file path
   *
   * @returns the file path
   */
  private List<Map<String, String>> insertDataDistances() throws FileNotFoundException {
    String filePath = Utils.readLineFromConsole("\nFile path distances: ");
    csvReader = new CSVReader(filePath);
    App.getInstance().getCompany().setCurrentDistancesFilePath(filePath);

    return csvReader.read();

  }

  /*
   * Asks to insert the file path
   *
   * @returns the file path
   */
  private EntityStore insertDataEntities() throws FileNotFoundException {
    String filePath = Utils.readLineFromConsole("\nFile path entities: ");
    csvReader = new CSVReader(filePath);

    EntityStore store = new EntityStore();

    store.addEntitiesFromList(csvReader.read());
    App.getInstance().getCompany().setCurrentEntitiesFilePath(filePath);

    return store;
  }

}
