package isep.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import isep.shared.SystemRole;

public class UserTest {
  @Test
  public void testGetUserRole() {
    User user = new User("username", "pwd", SystemRole.CLIENT);
    assertEquals(SystemRole.CLIENT, user.getUserRole());
  }

  @Test
  public void testEqualsWithDifferentName() {
    User user = new User("username", "pwd", SystemRole.CLIENT);
    assertFalse(user.equals(new User("other", "pwd", SystemRole.CLIENT)));
  }

  @Test
  public void testEqualsWithDifferentPassword() {
    User user = new User("username", "pwd", SystemRole.CLIENT);
    assertFalse(user.equals(new User("username", "other", SystemRole.CLIENT)));
  }

  @Test
  public void testEqualsWithDifferentRole() {
    User user = new User("username", "pwd", SystemRole.CLIENT);
    assertFalse(user.equals(new User("username", "pwd", SystemRole.AGRICULTURAL_MANAGER)));
  }

  @Test
  public void testEqualsWithNull() {
    User user = new User("username", "pwd", SystemRole.CLIENT);
    assertFalse(user.equals(null));
  }

  @Test
  public void testEqualsWithSameObject() {
    User user = new User("username", "pwd", SystemRole.CLIENT);
    assertTrue(user.equals(user));
  }

  @Test
  public void testEquals() {
    User user = new User("username", "pwd", SystemRole.CLIENT);
    assertTrue(user.equals(new User("username", "pwd", SystemRole.CLIENT)));
  }
}
