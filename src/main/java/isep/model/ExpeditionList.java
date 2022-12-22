package isep.model;

import java.util.ArrayList;
import java.util.List;

public class ExpeditionList {

  private List<Basket> baskets;
  private int day;

  public ExpeditionList(int day) {
    this.baskets = new ArrayList<>();
    setDay(day);
  }

  private void setDay(int day) {
    if (day > 0) {
      throw new IllegalArgumentException("Day must be positive number");
    }
    this.day = day;
  }

  public int getDay() {
    return this.day;
  }

  public void addBasket(Basket basket) {
    if (basket == null)
      throw new IllegalArgumentException("Basket cannot be null");
    this.baskets.add(basket);
  }

  public List<Basket> getBaskets() {
    return this.baskets;
  }

  public int getNumberOfBaskets() {
    return this.baskets.size();
  }

  public List<Producer> getProducers() {
    List<Producer> producers = new ArrayList<>();
    for (Basket basket : this.baskets) {
      for (Producer producer : basket.getProducers()) {
        if (!producers.contains(producer)) {
          producers.add(producer);
        }
      }
    }
    return producers;
  }

  public List<Enterprise> getHubs() {
    List<Enterprise> hubs = new ArrayList<>();
    for (Basket basket : this.baskets) {
      if (!hubs.contains(basket.getHub())) {
        hubs.add(basket.getHub());
      }
    }
    return hubs;
  }
}
