package isep.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import isep.shared.SystemRole;

public class UserSessionTest {
  @Test
  public void testCreateUserSessionWithNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      new UserSession(null);
    });
  }

  @Test
  public void testLogin() {
    UserSession session = new UserSession(new User("username", "pwd", SystemRole.CLIENT));

    assertTrue(session.isLoggedIn());
    assertEquals("username", session.getUserName());
    assertEquals(SystemRole.CLIENT, session.getUserRole());
  }

  @Test
  public void testLogout() {
    UserSession session = new UserSession(new User("username", "pwd", SystemRole.CLIENT));

    assertTrue(session.isLoggedIn());

    session.doLogout();

    assertFalse(session.isLoggedIn());
  }
}
