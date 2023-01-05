package isep.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents a list of baskets for a given day.
 *
 * @author André Barros <1211299@isep.ipp.pt>
 * @author Carlos Lopes <1211277@isep.ipp.pt>
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
public class ExpeditionList {
  private List<Basket> baskets;
  private int day;

  public ExpeditionList(int day) {
    this.baskets = new ArrayList<>();
    setDay(day);
  }

  private void setDay(int day) {
    if (day < 0)
      throw new IllegalArgumentException("Day must be a positive number");

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
  public Set<Producer> getProducers() {
    Set<Producer> producers = new LinkedHashSet<>();

    for (Basket basket : this.baskets)
      for (Producer producer : basket.getProducers())
        producers.add(producer);

    return producers;
  }

  /**
   * Get all hubs contained in the ExpeditionList.
   *
   * @return A {@code List} of all hubs (Enterprises)
   */
  public Set<Enterprise> getHubs() {
    Set<Enterprise> hubs = new LinkedHashSet<>();

    for (Basket basket : this.baskets)
      hubs.add(basket.getHub());

    return hubs;
  }

  public Set<Client> getClients() {
    Set<Client> clients = new LinkedHashSet<>();

    for (Basket basket : this.baskets)
      clients.add(basket.getClient());

    return clients;

  }

  /**
   * Get, for each hub in the ExpeditionList, all producers that deliver to that
   * hub.
   *
   * @return A {@code Map} that associates a Hub ({@code Enterprise} object) to a
   *         list of
   *         {@code Producer} objects.
   */
  public Map<Enterprise, List<Producer>> getProducersThatSupplyHubs() {
    Map<Enterprise, List<Producer>> map = new HashMap<Enterprise, List<Producer>>();

    for (Basket basket : baskets) {
      Enterprise hub = basket.getHub();
      Set<Producer> producers = basket.getProducers();

      List<Producer> existingProducers = map.get(hub);

      if (existingProducers != null)
        for (Producer producer : producers)
          existingProducers.add(producer);

      map.put(hub, new ArrayList<>(producers));
    }

    return map;
  }

  public Map<Enterprise, Integer> getBasketsDeliveredAtHubs() {
    Map<Enterprise, Integer> map = new HashMap<Enterprise, Integer>();

    for (Basket basket : baskets) {
      Enterprise hub = basket.getHub();
      int basketCount = 0;

      if (map.containsKey(hub))
        basketCount = map.get(hub);

      map.put(hub, ++basketCount);
    }

    return map;
  }

  public int getNumberOfFullySatisfiedProducts(Basket basket) {
    return basket.getNumberOfFullySatisfiedProducts();
  }

  public int getNumberOfPartiallySatisfiedProducts(Basket basket) {
    return basket.getNumberOfPartiallySatisfiedProducts();
  }

  public int getNumberOfNotSatisfiedProducts(Basket basket) {
    return basket.getNumberOfNotSatisfiedProducts();
  }

  public double getPercentageOfFullySatisfiedProducts(Basket basket) {
    return basket.getNumberOfFullySatisfiedProducts() / (double) basket.getNumberOfProducts();
  }

  public int getNumberOfDistinctProducersForBasket(Basket basket) {
    return basket.getProducers().size();
  }

  public int getNumberOfFullySuppliedBasketsByProducer(Producer producer) {
    int count = 0;

    for (Basket basket : baskets)
      if (basket.isFullySuppliedBy(producer))
        count++;

    return count;
  }

  public int getNumberOfPartiallySuppliedBasketsByProducer(Producer producer) {
    int count = 0;

    for (Basket basket : baskets)
      if (basket.isPartiallySuppliedBy(producer))
        count++;

    return count;
  }

  public int getNumberOfFullyFulfilledBasketsByClient(Client client) {
    int count = 0;

    for (Basket basket : baskets) {
      if (!basket.isFromClient(client))
        continue;

      if (basket.isFullyFulfilled())
        count++;
    }

    return count;
  }

  public int getNumberOfPartiallyFulfilledBasketsByClient(Client client) {
    int count = 0;

    for (Basket basket : baskets) {
      if (!basket.isFromClient(client))
        continue;

      if (basket.isPartiallyFulfilled())
        count++;
    }

    return count;
  }

  public Set<Producer> getProducersThatSupplyAllClientsBaskets(Client client) {
    Set<Producer> producers = new HashSet<>();

    for (Basket basket : baskets) {
      if (!basket.isFromClient(client))
        continue;

      Set<Producer> basketProducers = basket.getProducers();
      producers.addAll(basketProducers);
    }

    return producers;
  }

  public int getNumberOfDistinctClients(Producer producer) {
    Set<Client> clients = new HashSet<>();

    for (Basket basket : baskets) {
      if (!basket.isFullySuppliedBy(producer) && !basket.isPartiallySuppliedBy(producer))
        continue;

      Client client = basket.getClient();
      clients.add(client);
    }

    return clients.size();
  }

  public int getNumberOfDistinctHubs(Producer producer) {
    Set<Enterprise> hubs = new HashSet<>();

    for (Basket basket : baskets) {
      if (!basket.isFullySuppliedBy(producer) && !basket.isPartiallySuppliedBy(producer))
        continue;

      Enterprise hub = basket.getHub();
      hubs.add(hub);
    }

    return hubs.size();
  }

  public int getNumberOfDistinctClients(Enterprise hub) {
    Set<Client> clients = new HashSet<>();

    for (Basket basket : baskets) {
      if (!basket.isFromHub(hub))
        continue;

      Client client = basket.getClient();
      clients.add(client);
    }

    return clients.size();
  }

  public int getDistinctProducers(Enterprise hub) {
    Set<Producer> producers = new HashSet<>();

    for (Basket basket : baskets) {
      if (!basket.isFromHub(hub))
        continue;

      Set<Producer> basketProducers = basket.getProducers();
      producers.addAll(basketProducers);
    }

    return producers.size();
  }

  public int getNumberOfOutOfStockProducts(Producer producer) {
    int count = 0;

    for (Basket basket : baskets)
      for (Product product : basket.getProducts()) {
        Double availableStock = producer.getNonExpiredQuantityUntilDate(product, day);
        Double suppliedQuantity = basket.getQuantityOfSuppliedProduct(producer, product);

        if (availableStock <= suppliedQuantity)
          count++;
      }

    return count;
  }
}
