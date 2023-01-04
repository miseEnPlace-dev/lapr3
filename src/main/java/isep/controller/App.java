package isep.controller;

import isep.auth.AuthFacade;
import isep.auth.UserSession;
import isep.model.Company;
import isep.shared.SystemRole;

public class App {
  private final Company company;
  private final AuthFacade authFacade;

  private App() {
    this.company = new Company();
    this.authFacade = new AuthFacade();

    bootstrap();
  }

  private void bootstrap() {
    this.authFacade.addUserWithRole(SystemRole.AGRICULTURAL_MANAGER.toString(), "123456",
        SystemRole.AGRICULTURAL_MANAGER);
    this.authFacade.addUserWithRole(SystemRole.CLIENT.toString(), "123456", SystemRole.CLIENT);
    this.authFacade.addUserWithRole(SystemRole.DRIVER.toString(), "123456", SystemRole.DRIVER);
    this.authFacade.addUserWithRole(SystemRole.DISTRIBUTION_MANAGER.toString(), "123456",
        SystemRole.DISTRIBUTION_MANAGER);
    this.authFacade.addUserWithRole("agri", "1", SystemRole.AGRICULTURAL_MANAGER);
  }

  /**
   * Allows the user to get the instance of the running App's company.
   *
   * @return the instance of the running App company
   */

  public Company getCompany() {
    return company;
  }

  public UserSession getCurrentUserSession() {
    return this.authFacade.getCurrentUserSession();
  }

  public boolean doLogin(String email, String pwd) {
    return this.authFacade.doLogin(email, pwd).isLoggedIn();
  }

  public void doLogout() {
    this.authFacade.doLogout();
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
