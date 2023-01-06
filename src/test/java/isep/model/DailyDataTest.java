package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class DailyDataTest {

  @Test
  public void testQuantityAvailableWorks() {
    DailyData dailyData = new DailyData();
    Product p = new Product("Banana");

    Map<Product, Double> productsDay1 = new HashMap<>();
    productsDay1.put(p, 300.);
    productsDay1.put(new Product("Apple"), 100.);
    dailyData.addDayData(1, productsDay1);

    Map<Product, Double> productsDay2 = new HashMap<>();
    productsDay2.put(p, 100.);
    productsDay2.put(new Product("Tomato"), 200.);
    dailyData.addDayData(2, productsDay2);

    Map<Product, Double> productsDay3 = new HashMap<>();
    productsDay3.put(p, 500.);
    dailyData.addDayData(3, productsDay3);

    Map<Product, Double> productsDay4 = new HashMap<>();
    productsDay4.put(p, 500.);
    dailyData.addDayData(4, productsDay4);

    assertEquals(1100., dailyData.getQuantityAvailable(p, 4));
  }

  @Test
  public void testQuantityAvailableWorksWithNoData() {
    assertEquals(0, new DailyData().getQuantityAvailable(new Product("test"), 2));
  }

  @Test
  public void testQuantityAvailableWorksWithPartialData() {
    DailyData dailyData = new DailyData();
    Product p = new Product("Banana");

    Map<Product, Double> productsDay1 = new HashMap<>();
    productsDay1.put(p, 300.);
    productsDay1.put(new Product("Apple"), 100.);
    dailyData.addDayData(1, productsDay1);

    Map<Product, Double> productsDay2 = new HashMap<>();
    productsDay2.put(p, 100.);
    productsDay2.put(new Product("Tomato"), 200.);
    dailyData.addDayData(2, productsDay2);

    assertEquals(400., dailyData.getQuantityAvailable(p, 3));
  }

  @Test
  public void testRemoveValidQuantityWorks() {
    DailyData dailyData = new DailyData();
    Product p = new Product("Banana");

    Map<Product, Double> productsDay1 = new HashMap<>();
    productsDay1.put(p, 300.);
    productsDay1.put(new Product("Apple"), 100.);
    dailyData.addDayData(1, productsDay1);

    Map<Product, Double> productsDay2 = new HashMap<>();
    productsDay2.put(p, 100.);
    productsDay2.put(new Product("Tomato"), 200.);
    dailyData.addDayData(2, productsDay2);

    Map<Product, Double> productsDay3 = new HashMap<>();
    productsDay3.put(p, 500.);
    dailyData.addDayData(3, productsDay3);

    Map<Product, Double> productsDay4 = new HashMap<>();
    productsDay4.put(p, 500.);
    dailyData.addDayData(4, productsDay4);

    dailyData.removeValidProductQuantity(p, 700., 4);
    assertEquals(300., dailyData.getQuantityOfProductForDay(1, p));
    assertEquals(.0, dailyData.getQuantityOfProductForDay(2, p));
    assertEquals(.0, dailyData.getQuantityOfProductForDay(3, p));
    assertEquals(400., dailyData.getQuantityOfProductForDay(4, p));
  }

  @Test
  public void testGetQuantityOfNonExistingProduct() {
    DailyData dailyData = new DailyData();
    Product p = new Product("Banana");

    Map<Product, Double> productsDay1 = new HashMap<>();
    productsDay1.put(new Product("Apple"), 100.);
    dailyData.addDayData(1, productsDay1);

    assertEquals(.0, dailyData.getQuantityOfProductForDay(1, p));
  }

  @Test
  public void testAddDayDataWithInvalidDay() {
    DailyData dailyData = new DailyData();
    Map<Product, Double> productsDay1 = new HashMap<>();
    productsDay1.put(new Product("Apple"), 100.);

    assertThrows(IllegalArgumentException.class, () -> {
      dailyData.addDayData(-1, productsDay1);
    });
  }

  @Test
  public void testAddDayDataWithInvalidProductsData() {
    DailyData dailyData = new DailyData();

    assertThrows(IllegalArgumentException.class, () -> {
      dailyData.addDayData(1, null);
    });
  }

  @Test
  public void testAddDayDataWithEmptyProductsData() {
    DailyData dailyData = new DailyData();
    Map<Product, Double> p = new HashMap<>();

    assertThrows(IllegalArgumentException.class, () -> {
      dailyData.addDayData(1, p);
    });
  }
}
