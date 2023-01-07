package isep.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ReceivedProducts {
  private Map<Producer, Map<Product, Double>> received;

  public ReceivedProducts() {
    this.received = new LinkedHashMap<>();
  }

  public void addProduct(Producer producer, Product product, Double quantity) {
    if (producer == null)
      throw new IllegalArgumentException("Producer cannot be null");

    if (product == null)
      throw new IllegalArgumentException("Product cannot be null");

    if (quantity == null)
      throw new IllegalArgumentException("Quantity cannot be null");

    Map<Product, Double> producerProducts = received.get(producer);

    if (producerProducts == null)
      producerProducts = new HashMap<>();

    Double currentQuantity = producerProducts.get(product);

    if (currentQuantity != null)
      throw new IllegalArgumentException("Product already exists");
    producerProducts.put(product, quantity);

    received.put(producer, producerProducts);
  }

  public void setProduct(Producer producer, Product product, Double quantity) {
    if (producer == null)
      throw new IllegalArgumentException("Producer cannot be null");

    if (product == null)
      throw new IllegalArgumentException("Product cannot be null");

    if (quantity == null)
      throw new IllegalArgumentException("Quantity cannot be null");

    if (this.received.containsKey(producer)) {
      this.received.get(producer).put(product, quantity);
    } else {
      Map<Product, Double> products = new LinkedHashMap<>();
      products.put(product, quantity);
      this.received.put(producer, products);
    }

  }

  public void addAllProducts(Producer producer, Map<Product, Double> products) {
    if (producer == null)
      throw new IllegalArgumentException("Producer cannot be null");

    if (products == null)
      throw new IllegalArgumentException("Products cannot be null");

    received.put(producer, products);
  }

  public Double getQuantityOfSuppliedProduct(Producer producer, Product product) {
    if (producer == null)
      throw new IllegalArgumentException("Producer cannot be null");

    if (product == null)
      throw new IllegalArgumentException("Product cannot be null");

    if (received.get(producer) == null)
      return .0;

    Map<Product, Double> receivedProducts = received.get(producer);

    if (receivedProducts.get(product) == null)
      return .0;

    return receivedProducts.get(product);
  }

  public Set<Producer> getProducers() {
    return this.received.keySet();
  }

  public boolean matchesProductQuantity(Product product, Double quantity) {
    for (Producer producer : received.keySet()) {
      Map<Product, Double> receivedProducts = received.get(producer);

      if (receivedProducts.get(product) == null)
        continue;

      if (receivedProducts.get(product).equals(quantity))
        return true;
    }

    return false;
  }

  public boolean hasNotReceivedProduct(Product product) {
    for (Producer producer : received.keySet()) {
      Map<Product, Double> receivedProducts = received.get(producer);

      if (receivedProducts.get(product) == null)
        continue;

      if (receivedProducts.get(product) == 0)
        return true;
    }

    return false;
  }

  public int getNumberOfDistinctProducers() {
    return received.size();
  }

  @Override
  public String toString() {
    String result = "Received Products: \n";

    for (Producer producer : this.received.keySet()) {
      result += "   Producer: " + producer.getId() + "\n";
      Map<Product, Double> products = this.received.get(producer);
      for (Product product : products.keySet()) {
        result += "      Product: " + product.getName() + " - Quantity: " + products.get(product) + "\n";
      }
    }

    return result;
  }
}
