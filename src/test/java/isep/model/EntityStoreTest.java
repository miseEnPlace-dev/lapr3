package isep.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
    assertTrue(entityStore.addEntity("test", 0, 0, "test", Role.CLIENT));
  }

  @Test
  public void testEntityStoreGetEntities() {
    List<Entity> list = new ArrayList<>();
    list.add(new Client("C1", 10.0, 0.0, "CT1"));
    list.add(new Client("C2", 20.0, 0.0, "CT2"));
    list.add(new Client("C3", 30.0, 10.0, "CT4"));
    list.add(new Enterprise("E1", 20.0, 10.0, "CT3"));
    list.add(new Producer("P1", 10.0, 10.0, "CT5"));
    list.add(new Producer("P2", 11.0, 20.0, "CT6"));
    list.add(new Client("C4", 30.0, 10.0, "CT7"));
    list.add(new Enterprise("E2", 20.0, 20.0, "CT8"));
    list.add(new Client("C5", 40.0, 10.0, "CT9"));
    list.add(new Client("C6", 50.0, 10.0, "CT10"));
    list.add(new Client("C7", 60.0, 10.0, "CT11"));
    list.add(new Client("C8", 70.0, 10.0, "CT12"));
    list.add(new Client("C9", 80.0, 10.0, "CT13"));
    list.add(new Client("C10", 80.0, 10.0, "CT14"));
    list.add(new Client("C11", 80.0, 10.0, "CT15"));
    list.add(new Client("C12", 80.0, 10.0, "CT16"));
    list.add(new Client("C13", 80.0, 10.0, "CT17"));

    assertEquals(list.get(0).getId(), entityStore.getEntityByLocalizationId("CT1").getId());
    assertEquals(list.get(1).getId(), entityStore.getEntityByLocalizationId("CT2").getId());
    assertEquals(list.get(2).getId(), entityStore.getEntityByLocalizationId("CT4").getId());
    assertEquals(list.get(3).getId(), entityStore.getEntityByLocalizationId("CT3").getId());
    assertEquals(list.get(4).getId(), entityStore.getEntityByLocalizationId("CT5").getId());
    assertEquals(list.get(5).getId(), entityStore.getEntityByLocalizationId("CT6").getId());
    assertEquals(list.get(6).getId(), entityStore.getEntityByLocalizationId("CT7").getId());
    assertEquals(list.get(7).getId(), entityStore.getEntityByLocalizationId("CT8").getId());
    assertEquals(list.get(8).getId(), entityStore.getEntityByLocalizationId("CT9").getId());
    assertEquals(list.get(9).getId(), entityStore.getEntityByLocalizationId("CT10").getId());
    assertEquals(list.get(10).getId(), entityStore.getEntityByLocalizationId("CT11").getId());
    assertEquals(list.get(11).getId(), entityStore.getEntityByLocalizationId("CT12").getId());
    assertEquals(list.get(12).getId(), entityStore.getEntityByLocalizationId("CT13").getId());
    assertEquals(list.get(13).getId(), entityStore.getEntityByLocalizationId("CT14").getId());
    assertEquals(list.get(14).getId(), entityStore.getEntityByLocalizationId("CT15").getId());
    assertEquals(list.get(15).getId(), entityStore.getEntityByLocalizationId("CT16").getId());
    assertEquals(list.get(16).getId(), entityStore.getEntityByLocalizationId("CT17").getId());
  }

  @Test
  public void testGetEntitiesWithRoleClient() {
    List<Client> expected = new ArrayList<>();
    expected.add(new Client("C1", 10.0, 0.0, "CT1"));
    expected.add(new Client("C2", 20.0, 0.0, "CT2"));
    expected.add(new Client("C3", 30.0, 10.0, "CT4"));
    expected.add(new Client("C4", 30.0, 10.0, "CT7"));
    expected.add(new Client("C5", 40.0, 10.0, "CT9"));
    expected.add(new Client("C6", 50.0, 10.0, "CT10"));
    expected.add(new Client("C7", 60.0, 10.0, "CT11"));
    expected.add(new Client("C8", 70.0, 10.0, "CT12"));
    expected.add(new Client("C9", 80.0, 10.0, "CT13"));
    expected.add(new Client("C10", 80.0, 10.0, "CT14"));
    expected.add(new Client("C11", 80.0, 10.0, "CT15"));
    expected.add(new Client("C12", 80.0, 10.0, "CT16"));
    expected.add(new Client("C13", 80.0, 10.0, "CT17"));

    List<Client> actual = entityStore.getEntitiesWithRole(Role.CLIENT);

    assertArrayEquals(expected.toArray(), actual.toArray());
  }

  @Test
  public void testGetEntitiesWithRoleEnterprise() {
    List<Enterprise> expected = new ArrayList<>();
    expected.add(new Enterprise("E1", 20.0, 10.0, "CT3"));
    expected.add(new Enterprise("E2", 20.0, 20.0, "CT8"));

    List<Enterprise> actual = entityStore.getEntitiesWithRole(Role.ENTERPRISE);

    assertArrayEquals(expected.toArray(), actual.toArray());
  }

  @Test
  public void testGetEntitiesWithRoleProducer() {
    List<Producer> expected = new ArrayList<>();
    expected.add(new Producer("P1", 10.0, 10.0, "CT5"));
    expected.add(new Producer("P2", 11.0, 20.0, "CT6"));

    List<Producer> actual = entityStore.getEntitiesWithRole(Role.PRODUCER);

    assertArrayEquals(expected.toArray(), actual.toArray());
  }

  @Test
  public void testGetEntityById() {
    Entity entity = new Client("C1", 10.0, 0.0, "CT1");
    assertEquals(entity, entityStore.getEntityById("C1"));
  }

  @Test
  public void testGetEntityByIdWithNull() {
    assertNull(entityStore.getEntityById(null));
  }

  @Test
  public void testGetEntityByIdWithNonExistingId() {
    assertNull(entityStore.getEntityById("C100"));
  }
}
