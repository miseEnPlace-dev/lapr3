package isep.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ReceivedProducts {
  private Map<Producer, Map<Product, Integer>> received;

  public ReceivedProducts() {
    this.received = new LinkedHashMap<>();
  }

  public void addProduct(Producer producer, Product product, Integer quantity) {
    if (producer == null)
      throw new IllegalArgumentException("Producer cannot be null");

    if (product == null)
      throw new IllegalArgumentException("Product cannot be null");

    if (quantity == null)
      throw new IllegalArgumentException("Quantity cannot be null");

    Map<Product, Integer> producerProducts = received.get(producer);

    if (producerProducts == null)
      producerProducts = new HashMap<>();

    Integer currentQuantity = producerProducts.get(product);

    if (currentQuantity != null)
      throw new IllegalArgumentException("Product already exists");
    producerProducts.put(product, quantity);

    received.put(producer, producerProducts);
  }

  public void setProduct(Producer producer, Product product, Integer quantity){
    if (producer == null)
      throw new IllegalArgumentException("Producer cannot be null");

    if (product == null)
      throw new IllegalArgumentException("Product cannot be null");
    
    if (quantity == null)
      throw new IllegalArgumentException("Quantity cannot be null");
      
    if(this.received.containsKey(producer)){
      this.received.get(producer).put(product, quantity);
    }else{
      Map<Product, Integer> products = new LinkedHashMap<>();
      products.put(product, quantity);
      this.received.put(producer, products);
    }

  }

  public void addAllProducts(Producer producer, Map<Product, Integer> products) {
    if (producer == null)
      throw new IllegalArgumentException("Producer cannot be null");

    if (products == null)
      throw new IllegalArgumentException("Products cannot be null");

    received.put(producer, products);
  }

  public int getQuantityOfSuppliedProduct(Producer producer, Product product) {
    if (producer == null)
      throw new IllegalArgumentException("Producer cannot be null");

    if (product == null)
      throw new IllegalArgumentException("Product cannot be null");

    if (received.get(producer) == null)
      return 0;

    Map<Product, Integer> receivedProducts = received.get(producer);

    if (receivedProducts.get(product) == null)
      return 0;

    return receivedProducts.get(product);
  }

  public Set<Producer> getProducers() {
    Set<Producer> producers = new LinkedHashSet<>();

    for (Producer producer : received.keySet())
      producers.add(producer);

    return producers;
  }

  public boolean matchesProductQuantity(Product product, int quantity) {
    for (Producer producer : received.keySet()) {
      Map<Product, Integer> receivedProducts = received.get(producer);

      if (receivedProducts.get(product) == null)
        continue;

      if (receivedProducts.get(product).equals(quantity))
        return true;
    }

    return false;
  }

  public boolean hasNotReceivedProduct(Product product) {
    for (Producer producer : received.keySet()) {
      Map<Product, Integer> receivedProducts = received.get(producer);

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
  public String toString(){
    String result = "Received Products: \n\n";

    for(Producer producer : this.received.keySet()){
      result += "Producer: " + producer.getId() + "\n";
      Map<Product, Integer> products = this.received.get(producer);
      for (Product product : products.keySet()) {
        result += "   Product: " + product.getName() + " - Quantity: " + products.get(product) + "\n";
      }
    }

    return result;
  }
}
