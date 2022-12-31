package isep.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import isep.controller.AuthController;
import isep.shared.Constants;
import isep.ui.utils.Utils;

public class AuthUI implements Runnable {
  private AuthController ctrl;

  public AuthUI() {
    ctrl = new AuthController();
  }

  public void run() {
    boolean success = doLogin();

    this.logout();
  }

  private List<MenuItem> getMenuItemForRoles() {
    List<MenuItem> rolesUI = new ArrayList<>();
    // To complete with other user roles and related RoleUI
    rolesUI.add(new MenuItem(Constants.ROLE_GESTOR_AGRICOLA, new AgriculturalManagerUI()));
    rolesUI.add(new MenuItem(Constants.ROLE_CONDUTOR, new DriverUI()));
    rolesUI.add(new MenuItem(Constants.ROLE_CLIENTE, new ClientUI()));
    rolesUI.add(new MenuItem(Constants.ROLE_GESTOR_DISTRIBUICAO, new DistributionManagerUI()));
    //
    return rolesUI;
  }

  private boolean doLogin() {
    System.out.println("\nLogin:");

    int maxAttempts = Constants.MAX_OF_PASSWORD_TRIES;
    boolean success = false;
    do {
      maxAttempts--;
      String id = Utils.readLineFromConsole("Enter UserId/Email: ");
      String pwd = Utils.readLineFromConsole("Enter Password: ");

      success = ctrl.doLogin(id, pwd);
      if (!success) {
        System.out.println("Invalid UserId and/or Password. \n You have  " + maxAttempts + " more attempt(s).");
      }

    } while (!success && maxAttempts > 0);
    return success;
  }

  private void logout() {
    ctrl.doLogout();
  }
}
