package isep.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import isep.controller.App;
import isep.controller.FindNearestHubController;
import isep.model.DistributionNetwork;
import isep.model.Entity;
import isep.ui.utils.Utils;
import isep.model.Enterprise;

public class NearestHubUI implements Runnable {
  private FindNearestHubController findNearestHubController;
  private DistributionNetwork network;

  public NearestHubUI() {
  }

  @Override
  public void run() {
    network = App.getInstance().getCompany().getDistributionNetwork();

    if (network.getEntities().isEmpty()) {
      System.out.println("\nYou must load a distribution network first");
      return;
    }

    findNearestHubController = new FindNearestHubController(network);
    printNearestHub();
  }

  private void printNearestHub() {
    Map<Entity, Enterprise> nearestHubs = findNearestHubController.findNearestHub();

    List<Map<String, String>> table = new ArrayList<>();

    for (Map.Entry<Entity, Enterprise> entry : nearestHubs.entrySet()) {
      Map<String, String> current = new LinkedHashMap<>();

      current.put("Client/Enterprise", entry.getKey().getId());
      current.put("Nearest Hub", entry.getValue().getId());

      table.add(current);
    }

    Utils.showTable(table, "\nNearest Hub for each Client");
  }
}
