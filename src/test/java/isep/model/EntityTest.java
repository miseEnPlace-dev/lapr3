package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

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
  public void testNewEntityWithInvalidMinLatitude() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Enterprise("Test", -100, 10, "Test");
    });
  }

  @Test
  public void testNewEntityWithInvalidMaxLatitude() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Enterprise("Test", 100, 10, "Test");
    });
  }

  @Test
  public void testNewEntityWithInvalidMinLongitude() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Enterprise("Test", 10, -200, "Test");
    });
  }

  @Test
  public void testNewEntityWithInvalidMaxLongitude() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Enterprise("Test", 10, 200, "Test");
    });
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

  @Test
  public void testZeroHeuristicValue() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    Entity target = new Enterprise("Test", 10, 10, "Test");
    assertEquals(0, entity.getHeuristicValue(target));
  }

  @Test
  public void testHeuristicValue() {
    Entity entity = new Enterprise("CT32", 40.4333, -8.4333, "CT32");
    Entity target = new Enterprise("CT160", 40.3781, -8.4515, "CT160");
    assertEquals(6328, entity.getHeuristicValue(target));
  }

  @Test
  public void testEqualsWithNull() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    assertEquals(false, entity.equals(null));
  }

  @Test
  public void testEqualsWithDifferentClass() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    assertEquals(false, entity.equals("Test"));
  }

  @Test
  public void testSetDayDataWithNegativeDay() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    assertThrows(IllegalArgumentException.class, () -> {
      entity.setDayData(-1, new HashMap<>());
    });
  }

  @Test
  public void testSetDayDataWithNullData() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    assertThrows(IllegalArgumentException.class, () -> {
      entity.setDayData(1, null);
    });
  }

  @Test
  public void testSetDayData() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    Map<Product, Double> data = new HashMap<>();
    data.put(new Product("banana"), 1.);
    entity.setDayData(1, data);
    assertEquals(data, entity.getDayData(1));
  }

  @Test
  public void testGetDayDataWithNegativeDay() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    assertNull(entity.getDayData(-1));
  }

  @Test
  public void testGetDayData() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    Map<Product, Double> data = new HashMap<>();
    data.put(new Product("banana"), 1.);
    entity.setDayData(1, data);
    assertEquals(data, entity.getDayData(1));
  }

  @Test
  public void testGetDayDataWithNoData() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    assertEquals(null, entity.getDayData(1));
  }

  @Test
  public void testGetDayDataWithInvalidDay() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    Map<Product, Double> data = new HashMap<>();
    data.put(new Product("banana"), 1.);
    entity.setDayData(1, data);
    assertEquals(null, entity.getDayData(2));
  }

  @Test
  public void testGetDayDataWithMultipleProducts() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    Map<Product, Double> data = new HashMap<>();
    data.put(new Product("banana"), 1.);
    data.put(new Product("apple"), 2.);
    entity.setDayData(3, data);

    Map<Product, Double> expected = new HashMap<>();
    expected.put(new Product("banana"), 1.);
    expected.put(new Product("apple"), 2.);

    assertEquals(expected, entity.getDayData(3));
  }

  @Test
  public void testAddDayData() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    Map<Product, Double> data = new HashMap<>();
    data.put(new Product("banana"), 1.);
    entity.addDayData(1, data);

    assertEquals(data, entity.getDayData(1));
  }

  @Test
  public void testGetQuantityOfProductForDay() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    Map<Product, Double> data = new HashMap<>();
    data.put(new Product("banana"), 1.);
    entity.addDayData(1, data);

    assertEquals(1, entity.getQuantityOfProductForDay(1, new Product("banana")));
  }

  @Test
  public void testSetDailyDataWithNull() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    assertThrows(IllegalArgumentException.class, () -> {
      entity.setDailyData(null);
    });
  }

  @Test
  public void testSetDailyData() {
    Entity entity = new Enterprise("Test", 10, 10, "Test");
    Map<Integer, Map<Product, Double>> data = new HashMap<>();
    Map<Product, Double> dayData = new HashMap<>();
    dayData.put(new Product("banana"), 1.);
    data.put(1, dayData);

    DailyData dailyData = new DailyData();
    dailyData.addDayData(1, dayData);

    entity.setDailyData(dailyData);

    assertEquals(dailyData, entity.getDailyData());
  }
}
