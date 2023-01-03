package isep.auth;

import isep.shared.SystemRole;

public class AuthFacade {
  private UserSession userSession;
  private final UserStore userStore;

  public AuthFacade() {
    this.userSession = new UserSession();
    this.userStore = new UserStore();
  }

  public boolean addUserWithRole(String username, String password, SystemRole roleName) {
    return this.userStore.createUser(username, password, roleName);
  }

  public UserSession doLogin(String username, String password) throws IllegalArgumentException {
    User user = this.userStore.getByUsername(username, password);

    if (user == null)
      throw new IllegalArgumentException("User not found");

    if (user.getUsername().equals(username))
      this.userSession = new UserSession(user);

    return this.userSession;
  }

  public void doLogout() {
    this.userSession.doLogout();
  }

  public UserSession getCurrentUserSession() {
    return this.userSession;
  }
}
