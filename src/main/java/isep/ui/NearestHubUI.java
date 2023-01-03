package isep.ui;

import isep.controller.App;
import isep.controller.FindNearestHubController;
import isep.model.Company;
import isep.model.DistributionNetwork;

public class NearestHubUI implements Runnable {
  FindNearestHubController findNearestHubController;

  public NearestHubUI() {
    App app = App.getInstance();
    Company company = app.getCompany();
    DistributionNetwork distributionNetwork = company.getDistributionNetwork();
    findNearestHubController = new FindNearestHubController(distributionNetwork);

  }

  @Override
  public void run() {
    System.out.println("\nYour nearest hub is:");
    findNearestHubController.findNearestHub();

  }

  private void printNearestHub() {
    // TODO Auto-generated method stub
  }
}
