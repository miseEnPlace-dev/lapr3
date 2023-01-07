package isep.ui;

import isep.controller.App;
import isep.controller.GetMinNumberOfConnectionsController;
import isep.model.DistributionNetwork;

public class GetMinNumberOfConnectionsUI implements Runnable {
  GetMinNumberOfConnectionsController controller;
  DistributionNetwork network;

  public GetMinNumberOfConnectionsUI() {
  }

  @Override
  public void run() {
    network = App.getInstance().getCompany().getDistributionNetwork();
    controller = new GetMinNumberOfConnectionsController(network);

    if (controller.isConnected()) {
      System.out.println("\nThe network is connected!");

      int minNumberOfConnections = controller.getMinimumNumOfConnections();
      System.out.println("The minimum number of connections is: " + minNumberOfConnections);
    } else {
      System.out.println("\nThe network is not connected!");
    }
  }

}
