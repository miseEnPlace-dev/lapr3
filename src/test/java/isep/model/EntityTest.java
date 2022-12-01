package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EntityTest {

  /*
   * Entity Test class
   */
  @Test
  public void testEntityEnterprise() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    assertEquals(entity.getId(), "Test");
    assertEquals(entity.getLatitude(), 10);
    assertEquals(entity.getLongitude(), 10);
    assertEquals(entity.getLocalizationId(), "Test");

  }

  @Test
  public void testEntityProducer() {
    Entity entity = new Producer("Test", 10, 10, "Test");
    assertEquals(entity.getId(), "Test");
    assertEquals(entity.getLatitude(), 10);
    assertEquals(entity.getLongitude(), 10);
    assertEquals(entity.getLocalizationId(), "Test");

  }

  @Test
  public void testEntityClient() {
    Entity entity = new Client("Test", 10, 10, "Test");
    assertEquals(entity.getId(), "Test");
    assertEquals(entity.getLatitude(), 10);
    assertEquals(entity.getLongitude(), 10);
    assertEquals(entity.getLocalizationId(), "Test");

  }

  @Test
  public void testEntityEnterpriseNullId() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Enterprise(null, 10, 10, "Test");
    });
  }

  @Test
  public void testEntityEnterpriseNullLocalizationId() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Enterprise("Test", 10, 10, null);
    });
  }

  @Test
  public void testEntityEnterpriseWithInvalidLatitude() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Enterprise("Test", -100, 10, "Test");
    });
  }

  @Test
  public void testEntityEnterpriseWithInvalidLongitude() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Enterprise("Test", 10, 200, "Test");
    });
  }

  @Test
  public void testEntityEnterpriseMakeHub() {
    Enterprise entity = new Enterprise("Test", 10, 10, "Test");
    entity.makeHub();
    assertEquals(entity.isHub(), true);
  }

  @Test
  public void testEntityEnterpriseUnMakeHub() {
    Enterprise entity = new Enterprise("Test", 10, 10, "Test");
    entity.makeHub();
    entity.unMakeHub();
    assertEquals(entity.isHub(), false);
  }

  @Test
  public void testEntityToString() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    assertEquals(entity.toString(), "Entity [id=Test, latitude=10.0, longitude=10.0, localizationId=Test");
  }

}
