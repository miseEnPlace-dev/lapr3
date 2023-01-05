package isep.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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

  @Test
  public void testGetUsername() {
    UserSession session = new UserSession(new User("username", "pwd", SystemRole.CLIENT));
    assertEquals("username", session.getUserName());
    session.doLogout();
    assertNull(session.getUserName());
  }

  @Test
  public void testGetUserRole() {
    UserSession session = new UserSession(new User("username", "pwd", SystemRole.CLIENT));
    assertEquals(SystemRole.CLIENT, session.getUserRole());
    session.doLogout();
    assertNull(session.getUserRole());
  }
}
