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
    this.authFacade.addUserWithRole("agricula", "123456", SystemRole.GESTOR_AGRICOLA);
    this.authFacade.addUserWithRole("client", "123456", SystemRole.CLIENTE);
    this.authFacade.addUserWithRole("driver", "123456", SystemRole.CONDUTOR);
    this.authFacade.addUserWithRole("dist", "123456", SystemRole.GESTOR_DISTRIBUICAO);
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
