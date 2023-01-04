package isep.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import isep.mock.BasketMock;
import isep.shared.exceptions.InvalidHubException;
import isep.shared.exceptions.InvalidOrderException;

public class BasketTest {
  private static Producer firstProducer;
  private static Producer secondProducer;
  private static Basket mockBasket;

  @BeforeAll
  public static void setup() {
    firstProducer = new Producer("id2", 0, 0, "loc2");
    secondProducer = new Producer("id3", 0, 0, "loc3");

  }

  @BeforeEach
  public void resetMockBasket() throws InvalidHubException {
    Enterprise hub = new Enterprise("id", 0, 0, "loc");
    hub.makeHub();
    Client client = new Client("id1", 0, 0, "loc1");
    mockBasket = new Basket(hub, client);
  }

  @Test
  public void testCreateBasketWithNullClient() {
    Enterprise hub = new Enterprise("id", 0, 0, "loc");
    hub.makeHub();

    assertThrows(IllegalArgumentException.class, () -> {
      new Basket(hub, null);
    });
  }

  @Test
  public void testCreateBasketWithNullOrdered() {
    Enterprise hub = new Enterprise("id", 0, 0, "loc");
    hub.makeHub();
    Client client = new Client("id1", 0, 0, "loc1");

    assertThrows(IllegalArgumentException.class, () -> {
      new Basket(null, new ReceivedProducts(), hub, client);
    });
  }

  @Test
  public void testCreateBasketWithNullReceived() {
    Enterprise hub = new Enterprise("id", 0, 0, "loc");
    hub.makeHub();
    Client client = new Client("id1", 0, 0, "loc1");

    Map<Product, Double> ordered = new HashMap<>();
    ordered.put(new Product("Apples"), 100.);

    assertThrows(IllegalArgumentException.class, () -> {
      new Basket(ordered, null, hub, client);
    });
  }

  @Test
  public void testCreateBasketWithNoOrders() {
    Enterprise hub = new Enterprise("id", 0, 0, "loc");
    hub.makeHub();
    Client client = new Client("id1", 0, 0, "loc1");

    Map<Product, Double> ordered = new HashMap<>();
    ReceivedProducts received = new ReceivedProducts();

    assertThrows(InvalidOrderException.class, () -> {
      new Basket(ordered, received, hub, client);
    });
  }

  @Test
  public void testCreateBasketWithNullHub() {
    Client client = new Client("id1", 0, 0, "loc1");

    assertThrows(IllegalArgumentException.class, () -> {
      new Basket(null, client);
    });
  }

  @Test
  public void testCreateBasketWithEnterpriseNotHub() {
    Enterprise enterprise = new Enterprise("id", 0, 0, "loc");
    Client client = new Client("id1", 0, 0, "loc1");

    assertThrows(InvalidHubException.class, () -> {
      new Basket(enterprise, client);
    });
  }

  @Test
  public void testAddProductQuantity() throws InvalidHubException {
    Product product = new Product("Apples");

    mockBasket.addOrderedProduct(product, 100.);
    assertTrue(mockBasket.getProducts().contains(product));
  }

  @Test
  public void testAddProductQuantityWithDuplicateProduct() throws InvalidHubException {
    Product product = new Product("Apples");

    mockBasket.addOrderedProduct(product, 100.);
    assertThrows(IllegalArgumentException.class, () -> {
      mockBasket.addOrderedProduct(product, 200.);
    });
  }

  @Test
  public void testIsFullyFulfilledWithOneProducer() throws InvalidHubException {
    Basket basket = new BasketMock().mockFullyFulfilledWithOneProducerBasket();
    assertTrue(basket.isFullyFulfilled());
    assertTrue(basket.isFullySuppliedBy(firstProducer));
    assertFalse(basket.isPartiallyFulfilled());
  }

  @Test
  public void testIsFullyFulfilledWithTwoProducers() throws InvalidHubException {
    Basket basket = new BasketMock().mockFullyFulfilledWithTwoProducersBasket();
    assertTrue(basket.isFullyFulfilled());
    assertFalse(basket.isFullySuppliedBy(firstProducer));
    assertFalse(basket.isFullySuppliedBy(secondProducer));
  }
}
