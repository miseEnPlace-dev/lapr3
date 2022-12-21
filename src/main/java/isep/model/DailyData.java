package isep.model;

import java.util.HashMap;
import java.util.TreeMap;

public class DailyData {

  private TreeMap<Integer, HashMap<Product, Integer>> dailyData;

  public DailyData() {
    this.dailyData = new TreeMap<>();
  }

  /**
   * @param day      - day to add data
   * @param products - product/quantity data
   * 
   *                 Adds the products/quantity data to a specif day
   */
  public void addDayData(Integer day, HashMap<Product, Integer> products) {
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
    HashMap<Product, Integer> map;
    map = this.dailyData.containsKey(day) ? this.dailyData.get(day) : new HashMap<>();

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
  public HashMap<Product, Integer> getDayData(Integer day) {
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

}
