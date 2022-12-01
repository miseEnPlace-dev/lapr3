package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.mock.DistancesDataMock;
import isep.mock.EntityStoreMock;
import isep.model.DistributionNetwork;
import isep.model.Enterprise;
import isep.model.Entity;
import isep.model.Producer;
import isep.model.Role;
import isep.model.store.EntityStore;
import isep.shared.exceptions.InvalidNumberOfHubsException;
import isep.utils.CSVReader;

/**
 * Tests for the FindNearestHub class.
 * 
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
public class FindNearestHubTest {
  private static FindNearestHubController findNearestHubController;
  private static LoadDistributionNetworkController loadDistributionNetworkController;
  private static EntityStore entityStore;
  private static DistributionNetwork distributionNetwork;
  private static DefineHubsController defineHubsController;

  @BeforeAll
  public static void setup() {

  }

  @Test
  public void testWithNoEnterprises() throws FileNotFoundException {
    loadDistributionNetwork("src/test/resources/entitiesSampleV3.csv", "src/test/resources/distancesSampleV4.csv");

    findNearestHubController = new FindNearestHubController(distributionNetwork);

    Map<Entity, Enterprise> expected = new HashMap<>();
    for (Entity entity : entityStore.getEntities()) {
      if (entity instanceof Producer)
        continue;

      expected.put(entity, null);
    }

    Map<Entity, Enterprise> actual = findNearestHubController.findNearestHub();
    assertEquals(expected, actual);
    // 4 -> total number of clients (clients or enterprises)
    assertEquals(4, actual.size());
  }

  @Test
  public void testWithZeroHubs() throws FileNotFoundException {
    loadDistributionNetwork("src/test/resources/entitiesSampleV2.csv", "src/test/resources/distancesSampleV4.csv");

    findNearestHubController = new FindNearestHubController(distributionNetwork);

    Map<Entity, Enterprise> expected = new HashMap<>();
    for (Entity entity : entityStore.getEntities()) {
      if (entity instanceof Producer)
        continue;

      expected.put(entity, null);
    }

    Map<Entity, Enterprise> actual = findNearestHubController.findNearestHub();
    assertEquals(expected, actual);
    // 4 -> total number of clients (clients or enterprises)
    assertEquals(4, actual.size());
  }

  @Test
  public void testWithOneHub() throws FileNotFoundException, InvalidNumberOfHubsException {
    loadDistributionNetwork("src/test/resources/entitiesSampleV2.csv", "src/test/resources/distancesSampleV4.csv");

    findNearestHubController = new FindNearestHubController(distributionNetwork);

    defineHubsController = new DefineHubsController(distributionNetwork);
    defineHubsController.defineHubs(1);

    Enterprise hub = (Enterprise) entityStore.getEntityByLocalizationId("CT4");

    Map<Entity, Enterprise> expected = new HashMap<>();
    for (Entity entity : entityStore.getEntities()) {
      if (entity instanceof Producer)
        continue;

      expected.put(entity, hub);
    }

    Map<Entity, Enterprise> actual = findNearestHubController.findNearestHub();
    assertEquals(expected, actual);
    // 4 -> total number of clients (clients or enterprises)
    assertEquals(4, actual.size());
  }

  @Test
  public void testWithTwoHubs() throws FileNotFoundException, InvalidNumberOfHubsException {
    loadDistributionNetwork("src/test/resources/entitiesSampleV4.csv", "src/test/resources/distancesSampleV4.csv");

    findNearestHubController = new FindNearestHubController(distributionNetwork);

    defineHubsController = new DefineHubsController(distributionNetwork);
    defineHubsController.defineHubs(2);

    Enterprise hub1 = (Enterprise) entityStore.getEntityByLocalizationId("CT4");
    Enterprise hub2 = (Enterprise) entityStore.getEntityByLocalizationId("CT5");

    Map<Entity, Enterprise> expected = new HashMap<>();
    for (Entity entity : entityStore.getEntities()) {
      if (entity instanceof Producer)
        continue;
      if (entity.getLocalizationId().equals("CT5"))
        expected.put(entity, hub2);
      else
        expected.put(entity, hub1);
    }

    Map<Entity, Enterprise> actual = findNearestHubController.findNearestHub();
    assertEquals(expected, actual);
    // 6 -> total number of clients (clients or enterprises)
    assertEquals(6, actual.size());
  }

  private void loadDistributionNetwork(String entitiesFile, String distancesFile) throws FileNotFoundException {
    CSVReader entitiesReader = new CSVReader(entitiesFile);
    CSVReader distancesReader = new CSVReader(distancesFile);

    entityStore = new EntityStore();
    entityStore.addEntitiesFromList(entitiesReader.read());

    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore, distancesReader.read());

    distributionNetwork = loadDistributionNetworkController.loadDistributionNetwork();
  }
}
