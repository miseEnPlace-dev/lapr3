package isep.model;

import java.util.HashMap;

public class Producer extends Entity {

  private DailyData stock;

  public Producer(String id, double latitude, double longitude, String localizationId) {
    super(id, latitude, longitude, localizationId);
    stock = new DailyData();
  }

  public void setStock(DailyData stock){
    if(stock == null)
      throw new IllegalArgumentException("Stock cannot be null!");
    this.stock = stock;
  }

  public DailyData getStock(){
    return this.stock;
  }

  public void setDailyStock(Integer day, HashMap<Product, Integer> stock){
    if(day <= 0)
      throw new IllegalArgumentException("Day must be a positive number!");
    if(stock == null)
      throw new IllegalArgumentException("Stock cannot be null!");
    this.stock.addDayData(day, stock);
  }

  public HashMap<Product, Integer> getDailyStock(Integer day){
    return this.stock.getDayData(day);
  }

  public DailyData getStockUntilDate(Integer day){
    return this.stock.getDailyDataUntilDate(day);
  }

}
