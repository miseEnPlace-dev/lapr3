package isep.controller;

import isep.model.Company;

public class App {
  private final Company company;

  private App() {
    this.company = new Company();
  }

  /**
   * Allows the user to get the instance of the running App's company.
   *
   * @return the instance of the running App company
   */

  public Company getCompany() {
    return company;
  }

  // ############# Singleton #############
  private static App singleton = null;

  public static App getInstance() {
    if (singleton == null)
      synchronized (App.class) {
        singleton = new App();
      }

    return singleton;
  }
}
