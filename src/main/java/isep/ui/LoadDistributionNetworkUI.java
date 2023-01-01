package isep.ui;

import isep.controller.LoadDistributionNetworkController;
import isep.model.DistributionNetwork;
import isep.controller.App;

public class LoadDistributionNetworkUI implements Runnable {
  LoadDistributionNetworkController controller;
  DistributionNetwork network;

  public LoadDistributionNetworkUI() {
    network = App.getInstance().getCompany().getDistributionNetwork();
    // controller = new LoadDistributionNetworkController(network);
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }

}
