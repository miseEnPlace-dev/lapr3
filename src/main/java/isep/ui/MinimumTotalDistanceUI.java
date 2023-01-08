package isep.ui;

import isep.controller.App;
import isep.controller.ExpeditionPathController;
import isep.model.DistributionNetwork;
import isep.model.ExpeditionList;
import isep.model.ExpeditionPath;

public class MinimumTotalDistanceUI implements Runnable {
  private ExpeditionPathController controller;
  private DistributionNetwork network;
  private ExpeditionList expeditionList;
  private ExpeditionPath expeditionPath;

  public MinimumTotalDistanceUI() {
  }

  @Override
  public void run() {
    expeditionList = App.getInstance().getCompany().getCurrentExpeditionList();

    if (expeditionList == null) {
      System.out.println("\nPlease load an expedition list first...");
      System.out.println("Going back to main menu...");
      return;
    }

    network = App.getInstance().getCompany().getDistributionNetwork();
    controller = new ExpeditionPathController(network, expeditionList);

    expeditionPath = controller.findExpeditionPath();

    System.out.println("");
    expeditionPath.printPath();
  }
}
