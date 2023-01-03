package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.mock.ExpeditionListMock;
import isep.shared.exceptions.InvalidHubException;

public class ExpeditionListTest {
  private static ExpeditionList expList;

  @BeforeAll
  public static void setup() throws InvalidHubException {
    expList = new ExpeditionListMock().mockSimpleExpeditionList();
  }

  @Test
  public void testGetNumberOfFullySatisfiedProducts() {
    assertEquals(2, expList.getNumberOfFullySatisfiedProducts(expList.getBaskets().get(0)));
    assertEquals(1, expList.getNumberOfFullySatisfiedProducts(expList.getBaskets().get(1)));
  }

  @Test
  public void testGetNumberOfPartiallySatisfiedProducts() {
    assertEquals(1, expList.getNumberOfPartiallySatisfiedProducts(expList.getBaskets().get(0)));
    assertEquals(1, expList.getNumberOfPartiallySatisfiedProducts(expList.getBaskets().get(1)));
  }

  @Test
  public void testGetNumberOfNotSatisfiedProducts() {
    assertEquals(0, expList.getNumberOfNotSatisfiedProducts(expList.getBaskets().get(0)));
    assertEquals(1, expList.getNumberOfNotSatisfiedProducts(expList.getBaskets().get(1)));
  }

  @Test
  public void testGetPercentageOfFullySatisfiedProducts() {
    assertEquals(0.6666666666666666, expList.getPercentageOfFullySatisfiedProducts(expList.getBaskets().get(0)));
    assertEquals(0.3333333333333333, expList.getPercentageOfFullySatisfiedProducts(expList.getBaskets().get(1)));
  }

  @Test
  public void getNumberOfDistinctProducersForBasket() {
    assertEquals(1, expList.getNumberOfDistinctProducersForBasket(expList.getBaskets().get(0)));
    assertEquals(2, expList.getNumberOfDistinctProducersForBasket(expList.getBaskets().get(1)));
  }
}
