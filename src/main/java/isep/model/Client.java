package isep.model;

import java.util.HashMap;

public class Client extends Entity {

  DailyData orders;

  public Client(String id, double latitude, double longitude, String localizationId) {
    super(id, latitude, longitude, localizationId);
    this.orders = new DailyData();
  }

  public void setOrder(DailyData orders){
    if(orders == null)
      throw new IllegalArgumentException("Orders cannot be null!");
    this.orders = orders;
  }

  public DailyData getOrders(){
    return this.orders;
  }

  public void setOrder(Integer day, HashMap<Product, Integer> order){
    if(day <= 0)
      throw new IllegalArgumentException("Day must be a positive number!");
    if(order == null)
      throw new IllegalArgumentException("Order cannot be null!");
    this.orders.addDayData(day, order);
  }

  public HashMap<Product, Integer> getOrder(Integer day){
    return this.orders.getDayData(day);
  }

}
