package isep.ui;

import isep.controller.App;
import isep.controller.FindNearestHubController;
import isep.model.DistributionNetwork;
import isep.ui.utils.Utils;

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
    findNearestHubController.findNearestHub();
  }

  private void printNearestHub() {
    // TODO Auto-generated method stub
  }
}
