package isep.ui;

import java.util.Map;

import isep.controller.App;
import isep.controller.FindNearestHubController;
import isep.model.DistributionNetwork;
import isep.model.Entity;
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
    System.out.println("Client\t|  Nearest Hub");
    System.out.println("------\t|  -----------");
    for (Map.Entry<Entity, Enterprise> entry : findNearestHubController.findNearestHub().entrySet()) {
      System.out
          .println(entry.getKey().getId() + "\t|  " + (entry.getValue() == null ? "N/A" : entry.getValue().getId()));
    }
  }
}
