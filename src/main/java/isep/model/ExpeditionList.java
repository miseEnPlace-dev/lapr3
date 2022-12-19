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

  public void generateExpeditionList(int n, DistributionNetwork network) {
    // for each client of basket in baskets get the nearest hub
    // for each hub get the n nearest producers
    // for each producer get the products that are in the basket
    // for each product get the quantity that is in the basket
    // get producer that gave the product to the basket
    // create expedition

    for (Basket basket : this.baskets) {
      Enterprise hub = network.getNearestHub(basket.getClient());
      List<Producer> producers = network.getNNearestProducers(hub, n);
      for (Producer producer : producers) {
        // TO DO
      }
    }
  }
}
