package isep.ui;

import isep.controller.App;
import isep.controller.DefineHubsController;
import isep.model.DistributionNetwork;
import isep.ui.utils.Utils;

public class DefineHubsUI implements Runnable {
  DefineHubsController controller;
  DistributionNetwork network;

  public DefineHubsUI() {
  }

  @Override
  public void run() {
    network = App.getInstance().getCompany().getDistributionNetwork();
    controller = new DefineHubsController(network);
    // TODO Auto-generated method stub

    Utils.readLineFromConsole("Press any key to continue... ");

  }

}
