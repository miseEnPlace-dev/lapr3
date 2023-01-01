package isep.ui;

import isep.controller.App;
import isep.model.Company;

public class ClientUI implements Runnable {

  public ClientUI() {
    App app = App.getInstance();
    Company company = app.getCompany();
  }

  @Override
  public void run() {

    System.out.println("\nNo action available for this role");

  }

}
