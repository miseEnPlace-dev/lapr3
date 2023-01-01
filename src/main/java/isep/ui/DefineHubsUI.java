package isep.ui;

import isep.controller.App;
import isep.controller.DefineHubsController;
import isep.model.DistributionNetwork;

public class DefineHubsUI implements Runnable {
  DefineHubsController controller;
  DistributionNetwork network;

  public DefineHubsUI() {
    network = App.getInstance().getCompany().getDistributionNetwork();
    controller = new DefineHubsController(network);
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }

}
