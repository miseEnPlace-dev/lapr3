package isep.ui;

import isep.controller.App;
import isep.controller.FindNearestHubController;
import isep.model.DistributionNetwork;

public class NearestHubUI implements Runnable {
  private FindNearestHubController findNearestHubController;
  private DistributionNetwork distributionNetwork;

  public NearestHubUI() {
  }

  @Override
  public void run() {
    distributionNetwork = App.getInstance().getCompany().getDistributionNetwork();
    findNearestHubController = new FindNearestHubController(distributionNetwork);
    System.out.println("\nYour nearest hub is:");
    findNearestHubController.findNearestHub();

  }

  private void printNearestHub() {
    // TODO Auto-generated method stub
  }
}
