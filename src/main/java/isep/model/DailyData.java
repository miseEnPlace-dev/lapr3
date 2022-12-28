package isep.model;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class DailyData {
  private SortedMap<Integer, Map<Product, Integer>> dailyData;

  public DailyData() {
    this.dailyData = new TreeMap<>();
  }

  /**
   * @param day      - day to add data
   * @param products - product/quantity data
   *
   *                 Adds the products/quantity data to a specif day
   */
  public void addDayData(Integer day, Map<Product, Integer> products) {
    if (day < 0)
      throw new IllegalArgumentException("Day cannot be negative");
    if (products.isEmpty())
      throw new IllegalArgumentException("A map without data cannot be added to a day data.");

    this.dailyData.put(day, products);
  }

  /**
   * @param day      - day to add data
   * @param product  - product adding
   * @param quantity - quantity of product adding
   *
   *                 Adds a single product/quantity to a specif day
   */
  public void addProductInfoToDayData(Integer day, Product product, Integer quantity) {
    Map<Product, Integer> map = this.dailyData.containsKey(day) ? this.dailyData.get(day) : new HashMap<>();

    map.put(product, quantity);

    this.dailyData.put(day, map);
  }

  /**
   * @param day - day to get data from
   * @return HashMap<Product, Integer>
   *
   *         Returns a map with the products and quantities registered
   *         in that dailyData for a specif day
   */
  public Map<Product, Integer> getDayData(Integer day) {
    return this.dailyData.get(day);
  }

  /**
   * @param day     - day to get data from
   * @param product - product to get data from
   * @return Integer
   *
   *         Returns the quantity of a product registered
   *         in that dailyData for a specif day
   */
  public Integer getQuantityOfProductForDay(Integer day, Product product) {
    return this.dailyData.get(day).get(product) != null ? this.dailyData.get(day).get(product) : 0;
  }

  public DailyData getDailyDataUntilDate(Integer day) {
    DailyData result = new DailyData();
    this.dailyData = this.dailyData.subMap(0, day);

    return result;
  }
}
