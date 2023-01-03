package isep.model;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ReceivedProducts {
  private Map<Producer, Map<Product, Integer>> received;

  public ReceivedProducts() {
    this.received = new LinkedHashMap<>();
  }

  public void addProduct(Producer producer, Map<Product, Integer> products) {
    if (producer == null)
      throw new IllegalArgumentException("Producer cannot be null");

    if (products == null)
      throw new IllegalArgumentException("Products cannot be null");

    if (received.containsKey(producer))
      throw new IllegalArgumentException("Producer already exists");

    received.put(producer, products);
  }

  public Set<Producer> getProducers() {
    Set<Producer> producers = new LinkedHashSet<>();

    for (Producer producer : received.keySet())
      producers.add(producer);

    return producers;
  }

  public Integer getQuantityOfProduct(Product product) {
    for (Producer producer : received.keySet()) {
      Map<Product, Integer> receivedProducts = received.get(producer);

      if (!receivedProducts.containsKey(product))
        continue;

      return receivedProducts.get(product);
    }

    return null;
  }
}
