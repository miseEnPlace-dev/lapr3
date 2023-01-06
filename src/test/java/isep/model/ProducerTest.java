package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProducerTest {
  private static Map<Product, Double> products;

  @BeforeAll
  public static void setup() {
    Product p = new Product("apple");
    Product p2 = new Product("banana");
    products = new LinkedHashMap<>();
    products.put(p, 100.1);
    products.put(p2, 200.2);
  }

  @Test
  public void testInstatiateProducer() {
    Producer producer = new Producer("id", 0, 0, "loc");
    assertNotNull(producer);
  }

  @Test
  public void testGetId() {
    Producer producer = new Producer("id", 0, 0, "loc");
    assertEquals("id", producer.getId());
  }

  @Test
  public void testGetLatitude() {
    Producer producer = new Producer("id", 0, 0, "loc");
    assertEquals(0, producer.getLatitude());
  }

  @Test
  public void testGetLongitude() {
    Producer producer = new Producer("id", 0, 0, "loc");
    assertEquals(0, producer.getLongitude());
  }

  @Test
  public void testGetNonExpiredUntilDate() {
    Producer producer = new Producer("id", 0, 0, "loc");
    DailyData dailyData = new DailyData();

    dailyData.addDayData(1, products);
    producer.setDailyData(dailyData);

    assertEquals(100.1, producer.getNonExpiredQuantityUntilDate(new Product("apple"), 1), 0.1);
  }
}
