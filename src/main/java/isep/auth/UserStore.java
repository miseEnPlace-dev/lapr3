package isep.auth;

import java.util.ArrayList;
import java.util.List;
import isep.shared.SystemRole;

public class UserStore {
  private List<User> users;

  public UserStore() {
    users = new ArrayList<>();
  }

  public boolean createUser(String username, String password, SystemRole role) {
    if (userExist(username, password))
      return false;

    users.add(new User(username, password, role));
    return true;
  }

  private boolean userExist(String userName, String password) {
    if (users.isEmpty())
      return false;

    for (User user : users)
      if (user.getUsername().equals(userName) && user.getPassword().equals(password))
        return true;

    return false;
  }


  public User getByUsername(String username, String password) {
    if (!userExist(username, password))
      return null;

    for (User user : users)
      if (user.getUsername().equals(username) && user.getPassword().equals(password))
        return user;

    return null;
  }
}
