package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.mock.ExpeditionListMock;
import isep.model.ExpeditionList;
import isep.shared.exceptions.InvalidHubException;
import isep.shared.exceptions.InvalidOrderException;
import isep.shared.exceptions.UndefinedHubsException;

public class ExpeditionListControllerTest {
  private static ExpeditionListController controller;

  @BeforeAll
  public static void setup() throws InvalidHubException {
    controller = new ExpeditionListController();
  }

  @Test
  public void testGetExpeditonList() throws InvalidOrderException, InvalidHubException, UndefinedHubsException {
    ExpeditionList expList = controller.getExpeditionList(1);
    assertEquals(expList, controller.getExpeditionList(1));
  }

  @Test
  public void testGetExpeditionListForNNearestProducers()
      throws InvalidOrderException, InvalidHubException, UndefinedHubsException {
    ExpeditionList expList = controller.getExpeditionList(1, 1);
    assertEquals(expList, controller.getExpeditionList(1, 1));
  }

  @Test
  public void testPrintExpeditionList() throws InvalidHubException {
    ExpeditionList expList = new ExpeditionListMock().mockSimpleExpeditionList();
    controller.printExpeditionList(expList);
  }

  @Test
  public void testPrintExpeditionListForNNearestProducers() throws InvalidHubException {
    ExpeditionList expList = new ExpeditionListMock().mockSimpleExpeditionList();
    controller.printExpeditionList(expList);
  }

}
