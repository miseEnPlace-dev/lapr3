package isep.ui;

import java.util.Map;

import isep.controller.App;
import isep.controller.FindNearestHubController;
import isep.model.DistributionNetwork;
import isep.model.Enterprise;
import isep.model.Entity;

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
    System.out.println("\nYour nearest hub is:");
    printNearestHub();
  }

  private void printNearestHub() {
    for (Map.Entry<Entity, Enterprise> entry : findNearestHubController.findNearestHub().entrySet()) {
      System.out.println("Hub :" + entry.getKey().getId());
    }
  }
}
