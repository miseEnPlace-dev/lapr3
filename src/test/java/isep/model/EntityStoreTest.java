package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.mock.EntityStoreMock;
import isep.model.store.EntityStore;

public class EntityStoreTest {
  private static EntityStore entityStore;

  @BeforeAll
  public static void setup() throws FileNotFoundException {
    entityStore = new EntityStoreMock().mockSimpleEntityStore();
  }

  /*
   * Test EntityStore class
   */
  @Test
  public void testEntityStoreAddEntity() {
    boolean add = entityStore.addEntity("test", 0, 0, "test", Role.CLIENT);
    assertTrue(add);

  }

  @Test
  public void testEntityStoreGetEntities() {
    Iterator<Entity> list = entityStore.getEntities();
    assertEquals(list, entityStore.getEntities());
  }
}
