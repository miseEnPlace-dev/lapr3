package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReceivedProductsTest {
  private static Producer mockProducer;
  private static Product mockProduct;

  @BeforeAll
  public static void setup() {
    mockProducer = new Producer("id", 0, 0, "loc");
    mockProduct = new Product("Apple");
  }

  @Test
  public void testAddProductWithNullProducer() {
    ReceivedProducts receivedProducts = new ReceivedProducts();
    assertThrows(IllegalArgumentException.class, () -> {
      receivedProducts.addProduct(null, mockProduct, 100.);
    });
  }

  @Test
  public void testAddProductWithNullProduct() {
    ReceivedProducts receivedProducts = new ReceivedProducts();
    assertThrows(IllegalArgumentException.class, () -> {
      receivedProducts.addProduct(mockProducer, null, 100.);
    });
  }

  @Test
  public void testAddProductWithNullQuantity() {
    ReceivedProducts receivedProducts = new ReceivedProducts();
    assertThrows(IllegalArgumentException.class, () -> {
      receivedProducts.addProduct(mockProducer, mockProduct, null);
    });
  }

  @Test
  public void testAddProductWithDuplicatedProduct() {
    ReceivedProducts receivedProducts = new ReceivedProducts();
    receivedProducts.addProduct(mockProducer, mockProduct, 100.);

    assertThrows(IllegalArgumentException.class, () -> {
      receivedProducts.addProduct(mockProducer, mockProduct, 100.);
    });
  }

  @Test
  public void testAddProductWorks() {
    ReceivedProducts receivedProducts = new ReceivedProducts();
    receivedProducts.addProduct(mockProducer, mockProduct, 100.);

    assertEquals(100, receivedProducts.getQuantityOfSuppliedProduct(mockProducer, mockProduct));
  }

  @Test
  public void testAddAllProductsWithNullProducer() {
    ReceivedProducts receivedProducts = new ReceivedProducts();
    assertThrows(IllegalArgumentException.class, () -> {
      receivedProducts.addAllProducts(null, new HashMap<Product, Double>());
    });
  }

  @Test
  public void testAddAllProductsWithNullProducts() {
    ReceivedProducts receivedProducts = new ReceivedProducts();
    assertThrows(IllegalArgumentException.class, () -> {
      receivedProducts.addAllProducts(mockProducer, null);
    });
  }

  @Test
  public void testAddAllProductsWorks() {
    ReceivedProducts receivedProducts = new ReceivedProducts();
    Map<Product, Double> products = new HashMap<Product, Double>();
    products.put(mockProduct, 100.);
    receivedProducts.addAllProducts(mockProducer, products);

    assertEquals(100, receivedProducts.getQuantityOfSuppliedProduct(mockProducer, mockProduct));
  }
}
