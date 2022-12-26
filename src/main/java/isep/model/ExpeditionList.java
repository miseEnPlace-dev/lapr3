package isep.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpeditionList {
  private List<Basket> baskets;
  private int day;

  public ExpeditionList(int day) {
    this.baskets = new ArrayList<>();
    setDay(day);
  }

  private void setDay(int day) {
    if (day < 0)
      throw new IllegalArgumentException("Day must be positive number");

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

  /**
   * Get all producers contained in the ExpeditionList.
   * 
   * @return A {@code List} of all Producers
   */
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

  /**
   * Get all hubs contained in the ExpeditionList.
   * 
   * @return A {@code List} of all hubs (Enterprises)
   */
  public List<Enterprise> getHubs() {
    List<Enterprise> hubs = new ArrayList<>();
    for (Basket basket : this.baskets) {
      if (!hubs.contains(basket.getHub())) {
        hubs.add(basket.getHub());
      }
    }
    return hubs;
  }

  /**
   * Get, for each hub in the ExpeditionList, all producers that deliver to that
   * hub.
   * 
   * @return A {@code Map} that associates a Hub ({@code Enterprise} object) to a
   *         list of {@code Producer} objects.
   */
  public Map<Enterprise, List<Producer>> getProducersThatSupplyHubs() {
    Map<Enterprise, List<Producer>> map = new HashMap<Enterprise, List<Producer>>();

    for (Basket basket : baskets) {
      Enterprise hub = basket.getHub();
      List<Producer> producers = basket.getProducers();

      List<Producer> existingProducers = map.get(hub);
      if (existingProducers != null) {
        for (Producer producer : producers) {
          if (!existingProducers.contains(producer))
            existingProducers.add(producer);
        }
      }

      map.put(hub, producers);
    }

    return map;
  }

  public Map<Enterprise, Integer> getBasketsDeliveredAtHubs() {
    Map<Enterprise, Integer> map = new HashMap<Enterprise, Integer>();

    for (Basket basket : baskets) {
      Enterprise hub = basket.getHub();
      int basketCount = 0;

      if (map.containsKey(hub)) {
        basketCount = map.get(hub);
      }

      map.put(hub, ++basketCount);
    }

    return map;
  }
}
