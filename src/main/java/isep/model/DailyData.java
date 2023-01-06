package isep.model;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/*
 * DailyData class
 *
 * @author Carlos Lopes <1211277@isep.ipp.pt>
 */
public class DailyData {
  private final int DAYS_TO_EXPIRE = 3;
  private Map<Integer, Map<Product, Integer>> dailyData;

  public DailyData() {
    this.dailyData = new TreeMap<>();
  }

  public void setData(SortedMap<Integer, HashMap<Product, Integer>> dailyData) {
    if (dailyData == null)
      throw new IllegalArgumentException("Data cannot be null");
    this.dailyData.putAll(dailyData);
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
    Map<Product, Integer> dailyMap = this.dailyData.get(day);
    if (dailyMap == null)
      dailyMap = new HashMap<>();

    dailyMap.put(product, quantity);

    this.dailyData.put(day, dailyMap);
  }

  /**
   * @param day - day to get data from
   * @return HashMap<Product, Integer>
   *
   *         Returns a map with the products and quantities registered in that
   *         dailyData for a
   *         specif day
   */
  public Map<Product, Integer> getDayData(Integer day) {
    return this.dailyData.get(day);
  }

  /**
   * @param day     - day to get data from
   * @param product - product to get data from
   * @return Integer
   *
   *         Returns the quantity of a product registered in that dailyData for a
   *         specif day
   */
  public Integer getQuantityOfProductForDay(Integer day, Product product) {
    if (dailyData.get(day) == null)
      return 0;
    if (dailyData.get(day).get(product) == null)
      return 0;

    return this.dailyData.get(day).get(product);
  }

  public void setQuantityOfProductDay(Integer day, Product p, Integer quant) {
    if(this.dailyData.containsKey(day))
      this.dailyData.get(day).put(p, quant);
  }

  public DailyData getDailyDataUntilDate(Integer day) {
    DailyData result = new DailyData();

    for (int i = 1; i < day; i++)
      if (dailyData.get(i) != null)
        result.addDayData(i, dailyData.get(i));

    return result;
  }

  public Integer getNonExpiredProductQuantity(Product product, int day) {
    Integer quantity = 0;

    for (int i = day; i > day - DAYS_TO_EXPIRE; i--)
      quantity += getQuantityOfProductForDay(i, product);

    return quantity;
  }

  public void removeValidProductQuantity(Product p, Integer quant, Integer day) {
    for (int i = 2; i >= 0; i--) {
      if (quant == 0) 
        continue;
        Integer quantAvailable = this.getQuantityOfProductForDay(day - i, p);
      if (quantAvailable < quant) {
        quant -= quantAvailable;
        this.setQuantityOfProductDay(day - i, p, 0);
      } else {
        this.setQuantityOfProductDay(day - i, p, quantAvailable - quant);
        quant = 0;
      }
      
    }
  }

}
