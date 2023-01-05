package isep.auth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.shared.SystemRole;

public class AuthFacadeTest {
  private static AuthFacade authFacade;

  @BeforeAll
  public static void setup() {
    authFacade = new AuthFacade();
    authFacade.addUserWithRole("user 1", "pwd", SystemRole.CLIENT);
  }

  @Test
  public void testDoLoginWithNullUsername() {
    assertThrows(IllegalArgumentException.class, () -> {
      authFacade.doLogin(null, "pwd");
    });
  }

  @Test
  public void testDoLoginWithNullPassword() {
    assertThrows(IllegalArgumentException.class, () -> {
      authFacade.doLogin("user 1", null);
    });
  }

  @Test
  public void testDoLoginWorks() {
    assertFalse(authFacade.getCurrentUserSession().isLoggedIn());
    assertNotNull(authFacade.doLogin("user 1", "pwd"));
    assertTrue(authFacade.getCurrentUserSession().isLoggedIn());
  }

  @Test
  public void testDoLoginWithWrongPassword() {
    assertThrows(IllegalArgumentException.class, () -> {
      authFacade.doLogin("user 1", "wrong pwd");
    });
  }

  @Test
  public void testDoLogout() {
    assertNotNull(authFacade.doLogin("user 1", "pwd"));
    assertNotNull(authFacade.getCurrentUserSession());
    authFacade.doLogout();
    assertFalse(authFacade.getCurrentUserSession().isLoggedIn());
  }
}
