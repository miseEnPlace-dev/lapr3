package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.mock.ExpeditionListMock;
import isep.model.ExpeditionList;
import isep.shared.exceptions.InvalidHubException;

public class ExpeditionListStatisticsControllerTest {
  private static ExpeditionListStatisticsController controller;

  @BeforeAll
  public static void setup() throws InvalidHubException {
    ExpeditionList expList = new ExpeditionListMock().mockSimpleExpeditionList();
    controller = new ExpeditionListStatisticsController(expList);
  }

  @Test
  public void testGetBasketStatistics() {
    List<Map<String, String>> expected = new ArrayList<>();

    Map<String, String> firstBasketStats = new LinkedHashMap<>();

    firstBasketStats.put("No. of products totally fulfilled", "2");
    firstBasketStats.put("No. of products partially fulfilled", "1");
    firstBasketStats.put("Basket fulfillment percentage", "66.7%");
    firstBasketStats.put("No. of producers that supply basket", "1");

    expected.add(firstBasketStats);

    Map<String, String> secondBasketStats = new LinkedHashMap<>();

    secondBasketStats.put("No. of products totally fulfilled", "1");
    secondBasketStats.put("No. of products partially fulfilled", "1");
    secondBasketStats.put("Basket fulfillment percentage", "33.3%");
    secondBasketStats.put("No. of producers that supply basket", "2");

    expected.add(secondBasketStats);

    assertEquals(expected, controller.getBasketsStatistics());
  }

  @Test
  public void testGetClientsStatistics() {
    List<Map<String, String>> expected = new ArrayList<>();

    Map<String, String> firstClientStats = new LinkedHashMap<>();

    firstClientStats.put("No. of totally fulfilled baskets", "0");
    firstClientStats.put("No. of partially fulfilled baskets", "1");
    firstClientStats.put("No. of distinct producers that supply all baskets", "2");

    expected.add(firstClientStats);

    assertEquals(expected, controller.getClientsStatistics());
  }

  @Test
  public void testGetProducersStatistics() {
    List<Map<String, String>> expected = new ArrayList<>();

    Map<String, String> firstProducerStats = new LinkedHashMap<>();

    firstProducerStats.put("No. of totally supplied baskets", "1");
    firstProducerStats.put("No. of partially supplied baskets", "1");
    firstProducerStats.put("No. of distinct clients", "1");
    firstProducerStats.put("No. of out of stock products", "2");
    firstProducerStats.put("No. of supplied hubs", "1");

    expected.add(firstProducerStats);

    Map<String, String> secondProducerStats = new LinkedHashMap<>();

    secondProducerStats.put("No. of totally supplied baskets", "0");
    secondProducerStats.put("No. of partially supplied baskets", "1");
    secondProducerStats.put("No. of distinct clients", "1");
    secondProducerStats.put("No. of out of stock products", "0");
    secondProducerStats.put("No. of supplied hubs", "1");

    expected.add(secondProducerStats);

    assertEquals(expected, controller.getProducersStatistics());
  }

  @Test
  public void testGetHubsStatistics() {
    List<Map<String, String>> expected = new ArrayList<>();

    Map<String, String> firstHubStats = new LinkedHashMap<>();

    firstHubStats.put("No. of distinct clients", "1");
    firstHubStats.put("No. of distinct producers", "2");

    expected.add(firstHubStats);

    assertEquals(expected, controller.getHubsStatistics());
  }

  @Test
  public void testIsExpeditionListLoaded() throws InvalidHubException {
    ExpeditionListStatisticsController controller = new ExpeditionListStatisticsController(null);
    assertFalse(controller.isExpeditionListLoaded());
    controller = new ExpeditionListStatisticsController(new ExpeditionListMock().mockSimpleExpeditionList());
    assertTrue(controller.isExpeditionListLoaded());
  }
}
