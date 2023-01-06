package isep.ui;

import isep.controller.App;
import isep.controller.GetMinNumberOfConnectionsController;
import isep.model.DistributionNetwork;
import isep.ui.utils.Utils;

public class GetMinNumberOfConnectionsUI implements Runnable {
  GetMinNumberOfConnectionsController controller;
  DistributionNetwork network;

  public GetMinNumberOfConnectionsUI() {
  }

  @Override
  public void run() {
    network = App.getInstance().getCompany().getDistributionNetwork();
    controller = new GetMinNumberOfConnectionsController(network);
    // TODO Auto-generated method stub

  }

}
