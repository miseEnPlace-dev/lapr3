package isep.ui;

import isep.controller.App;
import isep.controller.GetMinNumberOfConnectionsController;
import isep.model.DistributionNetwork;

public class GetMinNumberOfConnectionsUI implements Runnable {
  GetMinNumberOfConnectionsController controller;
  DistributionNetwork network;

  public GetMinNumberOfConnectionsUI() {
    network = App.getInstance().getCompany().getDistributionNetwork();
    controller = new GetMinNumberOfConnectionsController(network);
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }

}
