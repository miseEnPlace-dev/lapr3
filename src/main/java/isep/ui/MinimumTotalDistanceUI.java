package isep.ui;

import isep.controller.App;
import isep.controller.ExpeditionPathController;
import isep.model.DistributionNetwork;
import isep.model.ExpeditionList;
import isep.model.ExpeditionPath;
import isep.ui.utils.Utils;

public class MinimumTotalDistanceUI implements Runnable {
  private ExpeditionPathController controller;
  private DistributionNetwork network;
  private ExpeditionList expeditionList;
  private ExpeditionPath expeditionPath;

  public MinimumTotalDistanceUI() {
  }

  @Override
  public void run() {
    int day = Utils.readIntegerFromConsole("Enter the day of the expedition list: ");

    expeditionList = new ExpeditionList(day);
    network = App.getInstance().getCompany().getDistributionNetwork();
    controller = new ExpeditionPathController(network, expeditionList);

    expeditionPath = controller.findExpeditionPath();

    expeditionPath.printPath();

    Utils.readLineFromConsole("\nPress any key to continue... ");

  }
}
