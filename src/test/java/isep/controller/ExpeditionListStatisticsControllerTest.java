package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.mock.ExpeditionListMock;
import isep.model.ExpeditionList;
import isep.shared.exceptions.InvalidHubException;
import isep.shared.exceptions.InvalidNumberOfHubsException;
import isep.shared.exceptions.InvalidOrderException;
import isep.shared.exceptions.UndefinedHubsException;

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

    firstBasketStats.put("Client", "id2");
    firstBasketStats.put("No. of products totally fulfilled", "2");
    firstBasketStats.put("No. of products partially fulfilled", "1");
    firstBasketStats.put("No. of products not fulfilled", "0");
    firstBasketStats.put("Basket fulfillment percentage", "86.8%");
    firstBasketStats.put("No. of producers that supply basket", "1");

    expected.add(firstBasketStats);

    Map<String, String> secondBasketStats = new LinkedHashMap<>();

    secondBasketStats.put("Client", "id3");
    secondBasketStats.put("No. of products totally fulfilled", "1");
    secondBasketStats.put("No. of products partially fulfilled", "1");
    secondBasketStats.put("No. of products not fulfilled", "1");
    secondBasketStats.put("Basket fulfillment percentage", "6.8%");
    secondBasketStats.put("No. of producers that supply basket", "2");

    expected.add(secondBasketStats);

    assertEquals(expected, controller.getBasketsStatistics());
  }

  @Test
  public void testGetClientsStatistics() {
    List<Map<String, String>> expected = new ArrayList<>();

    Map<String, String> firstClientStats = new LinkedHashMap<>();

    firstClientStats.put("Client", "id2");
    firstClientStats.put("No. of totally fulfilled baskets", "0");
    firstClientStats.put("No. of partially fulfilled baskets", "0");
    firstClientStats.put("No. of distinct producers that supply all baskets", "1");

    expected.add(firstClientStats);

    Map<String, String> secondClientStats = new LinkedHashMap<>();

    secondClientStats.put("Client", "id3");
    secondClientStats.put("No. of totally fulfilled baskets", "0");
    secondClientStats.put("No. of partially fulfilled baskets", "1");
    secondClientStats.put("No. of distinct producers that supply all baskets", "2");

    expected.add(secondClientStats);

    assertEquals(expected, controller.getClientsStatistics());
  }

  @Test
  public void testGetProducersStatistics() {
    List<Map<String, String>> expected = new ArrayList<>();

    Map<String, String> firstProducerStats = new LinkedHashMap<>();

    firstProducerStats.put("Producer", "id3");
    firstProducerStats.put("No. of totally supplied baskets", "1");
    firstProducerStats.put("No. of partially supplied baskets", "1");
    firstProducerStats.put("No. of distinct clients", "2");
    firstProducerStats.put("No. of out of stock products", "2");
    firstProducerStats.put("No. of supplied hubs", "1");

    expected.add(firstProducerStats);

    Map<String, String> secondProducerStats = new LinkedHashMap<>();

    secondProducerStats.put("Producer", "id4");
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

    firstHubStats.put("Hub", "id1");
    firstHubStats.put("No. of distinct clients", "2");
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

  @Test
  public void testGetHubStatisticsWithSmallFile()
      throws InvalidHubException, FileNotFoundException, InvalidOrderException, UndefinedHubsException,
      InvalidNumberOfHubsException {
    ExpeditionList expeditionList = new ExpeditionListMock().mockExpeditionListWithSmallFile();
    ExpeditionListStatisticsController ctrl = new ExpeditionListStatisticsController(expeditionList);
    ctrl.getHubsStatistics();

    List<Map<String, String>> expected = new ArrayList<>();

    Map<String, String> firstHubStats = new LinkedHashMap<>();
    firstHubStats.put("Hub", "E2");
    firstHubStats.put("No. of distinct clients", "6");
    firstHubStats.put("No. of distinct producers", "3");

    expected.add(firstHubStats);

    Map<String, String> secondHubStats = new LinkedHashMap<>();
    secondHubStats.put("Hub", "E4");
    secondHubStats.put("No. of distinct clients", "6");
    secondHubStats.put("No. of distinct producers", "3");

    expected.add(secondHubStats);

    assertEquals(expected, ctrl.getHubsStatistics());
  }

  @Test
  public void testHubStatisticsWithSampleFile()
      throws FileNotFoundException, InvalidOrderException, InvalidHubException, UndefinedHubsException,
      InvalidNumberOfHubsException {
    ExpeditionList expeditionList = new ExpeditionListMock().mockExpeditionListWithSampleFile();
    ExpeditionListStatisticsController ctrl = new ExpeditionListStatisticsController(expeditionList);

    List<Map<String, String>> expected = new ArrayList<>();

    Map<String, String> firstHubStats = new LinkedHashMap<>();
    firstHubStats.put("Hub", "E1");
    firstHubStats.put("No. of distinct clients", "1");
    firstHubStats.put("No. of distinct producers", "2");

    expected.add(firstHubStats);

    assertEquals(expected, ctrl.getHubsStatistics());
  }

  @Test
  public void testGetBasketStatisticsWithSampleFile() throws FileNotFoundException, InvalidOrderException,
      InvalidHubException, UndefinedHubsException, InvalidNumberOfHubsException {
    ExpeditionList expeditionList = new ExpeditionListMock().mockExpeditionListWithSampleFile();
    ExpeditionListStatisticsController ctrl = new ExpeditionListStatisticsController(expeditionList);

    List<Map<String, String>> expected = new ArrayList<>();

    Map<String, String> firstBasketStats = new LinkedHashMap<>();
    firstBasketStats.put("Client", "C3");
    firstBasketStats.put("No. of products totally fulfilled", "3");
    firstBasketStats.put("No. of products partially fulfilled", "0");
    firstBasketStats.put("No. of products not fulfilled", "0");
    firstBasketStats.put("Basket fulfillment percentage", "100.0%");
    firstBasketStats.put("No. of producers that supply basket", "2");

    expected.add(firstBasketStats);

    assertEquals(expected, ctrl.getBasketsStatistics());
  }

  @Test
  public void testGetClientsStatisticsWithSampleFile() throws FileNotFoundException, InvalidOrderException,
      InvalidHubException, UndefinedHubsException, InvalidNumberOfHubsException {
    ExpeditionList expeditionList = new ExpeditionListMock().mockExpeditionListWithSampleFile();
    ExpeditionListStatisticsController ctrl = new ExpeditionListStatisticsController(expeditionList);

    List<Map<String, String>> expected = new ArrayList<>();

    Map<String, String> firstClientStats = new LinkedHashMap<>();
    firstClientStats.put("Client", "C3");
    firstClientStats.put("No. of totally fulfilled baskets", "1");
    firstClientStats.put("No. of partially fulfilled baskets", "0");
    firstClientStats.put("No. of distinct producers that supply all baskets", "2");

    expected.add(firstClientStats);

    assertEquals(expected, ctrl.getClientsStatistics());
  }

  @Test
  public void testGetProducersStatisticsWithSampleFile() throws FileNotFoundException, InvalidOrderException,
      InvalidHubException, UndefinedHubsException, InvalidNumberOfHubsException {
    ExpeditionList expeditionList = new ExpeditionListMock().mockExpeditionListWithSampleFile();
    ExpeditionListStatisticsController ctrl = new ExpeditionListStatisticsController(expeditionList);

    List<Map<String, String>> expected = new ArrayList<>();

    Map<String, String> firstProducerStats = new LinkedHashMap<>();
    firstProducerStats.put("Producer", "P2");
    firstProducerStats.put("No. of totally supplied baskets", "0");
    firstProducerStats.put("No. of partially supplied baskets", "1");
    firstProducerStats.put("No. of distinct clients", "1");
    firstProducerStats.put("No. of out of stock products", "2");
    firstProducerStats.put("No. of supplied hubs", "1");

    expected.add(firstProducerStats);

    Map<String, String> secondProducerStats = new LinkedHashMap<>();
    secondProducerStats.put("Producer", "P1");
    secondProducerStats.put("No. of totally supplied baskets", "0");
    secondProducerStats.put("No. of partially supplied baskets", "1");
    secondProducerStats.put("No. of distinct clients", "1");
    secondProducerStats.put("No. of out of stock products", "3");
    secondProducerStats.put("No. of supplied hubs", "1");

    expected.add(secondProducerStats);

    assertEquals(expected, ctrl.getProducersStatistics());
  }
}
