package isep.auth;

import isep.controller.App;
import isep.shared.SystemRole;

public class AuthController {
  private final App app;

  public AuthController() {
    this.app = App.getInstance();
  }

  public boolean doLogin(String username, String password) {
    try {
      return this.app.doLogin(username, password);
    } catch (IllegalArgumentException ex) {
      return false;
    }
  }

  public SystemRole getUserRole() {
    if (this.app.getCurrentUserSession().isLoggedIn())
      return this.app.getCurrentUserSession().getUserRole();

    return null;
  }

  public void doLogout() {
    this.app.doLogout();
  }
}
