package isep.auth;

import isep.shared.SystemRole;

public class User {
  private final String username;
  private final String password;
  private final SystemRole userRole;

  public User(String username, String password, SystemRole userRole) {
    this.username = username;
    this.password = password;
    this.userRole = userRole;
  }

  public String getUsername() {
    return username;
  }

  public SystemRole getUserRole() {
    return userRole;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof User))
      return false;

    User user = (User) o;
    return username.equals(user.username) && password.equals(user.password)
        && userRole.equals(user.userRole);
  }
}
