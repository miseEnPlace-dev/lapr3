package isep.ui;

import isep.controller.App;
import isep.model.Company;

public class DriverUI implements Runnable {

  public DriverUI() {
    App app = App.getInstance();
    Company company = app.getCompany();
  }

  @Override
  public void run() {

    System.out.println("\nNo action available for this role");

  }
}
