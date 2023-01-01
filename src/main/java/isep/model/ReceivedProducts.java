package isep.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ReceivedProducts {
  private Map<Producer, Map<Product, Integer>> received;

  public ReceivedProducts() {
    this.received = new TreeMap<>();
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
    Set<Producer> producers = new TreeSet<>();

    for (Producer producer : received.keySet())
      producers.add(producer);

    return producers;
  }

  public boolean matchesProductQuantity(Product product, int quantity) {
    for (Producer producer : received.keySet()) {
      Map<Product, Integer> receivedProducts = received.get(producer);

      if (!receivedProducts.containsKey(product))
        continue;

      return receivedProducts.get(product) == quantity;
    }

    return false;
  }

  public boolean hasNotReceivedProduct(Product product) {
    for (Producer producer : received.keySet()) {
      Map<Product, Integer> receivedProducts = received.get(producer);

      if (!receivedProducts.containsKey(product))
        continue;

      return false;
    }

    return true;
  }

  public int getNumberOfDistinctProducers() {
    return received.size();
  }
}
