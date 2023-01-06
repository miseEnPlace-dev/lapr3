package isep.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import isep.shared.SystemRole;

public class UserStoreTest {
  private UserStore store;

  @BeforeEach
  public void resetStore() {
    store = new UserStore();
  }

  @Test
  public void testCreateUser() {
    assertTrue(store.createUser("username", "pwd", SystemRole.CLIENT));
    assertEquals(new User("username", "pwd", SystemRole.CLIENT), store.getByUsername("username", "pwd"));
  }

  @Test
  public void testCreateDuplicatedUser() {
    assertTrue(store.createUser("username", "pwd", SystemRole.CLIENT));
    assertFalse(store.createUser("username", "pwd", SystemRole.CLIENT));
  }

  @Test
  public void testCreateDuplicatedUserWithDifferentRole() {
    assertTrue(store.createUser("username", "pwd", SystemRole.CLIENT));
    assertFalse(store.createUser("username", "pwd", SystemRole.DISTRIBUTION_MANAGER));
  }

  @Test
  public void testGetUserByUserNameWithNonExistingUser() {
    assertNull(store.getByUsername("username", "pwd"));
    assertTrue(store.createUser("username", "pwd", SystemRole.CLIENT));
    assertEquals(new User("username", "pwd", SystemRole.CLIENT), store.getByUsername("username", "pwd"));
  }

  @Test
  public void testGetUserByUsernameWithNullName() {
    assertTrue(store.createUser("username", "pwd", SystemRole.CLIENT));
    assertNull(store.getByUsername(null, "pwd"));
  }

  @Test
  public void testGetUserByUsernameWithNullPassword() {
    assertTrue(store.createUser("username", "pwd", SystemRole.CLIENT));
    assertNull(store.getByUsername("username", null));
  }
}
