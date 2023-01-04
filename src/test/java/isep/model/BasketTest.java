package isep.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.mock.BasketMock;
import isep.shared.exceptions.InvalidHubException;

public class BasketTest {
  private static Producer firstProducer;
  private static Producer secondProducer;

  @BeforeAll
  public static void setup() {
    firstProducer = new Producer("id2", 0, 0, "loc2");
    secondProducer = new Producer("id3", 0, 0, "loc3");
  }

  @Test
  public void testIsFullyFulfilledWithOneProducer() throws InvalidHubException {
    Basket basket = new BasketMock().mockFullyFulfilledWithOneProducerBasket();
    assertTrue(basket.isFullyFulfilled());
    assertTrue(basket.isFullySuppliedBy(firstProducer));
  }

  @Test
  public void testIsFullyFulfilledWithTwoProducers() throws InvalidHubException {
    Basket basket = new BasketMock().mockFullyFulfilledWithTwoProducersBasket();
    assertTrue(basket.isFullyFulfilled());
    assertFalse(basket.isFullySuppliedBy(firstProducer));
    assertFalse(basket.isFullySuppliedBy(secondProducer));
  }
}
