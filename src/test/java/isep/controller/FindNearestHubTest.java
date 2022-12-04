package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.Test;

import isep.model.DistributionNetwork;
import isep.model.Enterprise;
import isep.model.Entity;
import isep.model.Producer;
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

  private static final String ENTITIES_FILE_PATH_WITH_ZERO_ENTERPRISES = "src/test/resources/entitiesSampleV3.csv";
  private static final String ENTITIES_FILE_PATH_WITH_ONE_ENTERPRISE = "src/test/resources/entitiesSampleV2.csv";
  private static final String ENTITIES_FILE_PATH_WITH_TWO_ENTERPRISES = "src/test/resources/entitiesSampleV4.csv";
  private static final String ENTITIES_BIG_FILE_PATH = "data/big/clientes-produtores_big.csv";

  private static final String DISTANCES_FILE_PATH = "src/test/resources/distancesSampleV4.csv";
  private static final String DISTANCES_BIG_FILE_PATH = "data/big/distancias_big.csv";

  @Test
  public void testWithNoEnterprises() throws FileNotFoundException {
    System.out.println("testWithNoEnterprises");

    loadDistributionNetwork(ENTITIES_FILE_PATH_WITH_ZERO_ENTERPRISES, DISTANCES_FILE_PATH);

    findNearestHubController = new FindNearestHubController(distributionNetwork);

    Map<Entity, Enterprise> expected = new HashMap<>();
    Iterator<Entity> iterator = entityStore.getEntities();
    while (iterator.hasNext()) {
      Entity entity = iterator.next();

      if (!(entity instanceof Producer))
        expected.put((Entity) entity, null);
    }

    Map<Entity, Enterprise> actual = findNearestHubController.findNearestHub();
    assertEquals(expected, actual);
    // 4 -> total number of clients (clients or enterprises)
    assertEquals(4, actual.size());
  }

  @Test
  public void testWithZeroHubs() throws FileNotFoundException {
    System.out.println("testWithZeroHubs");

    loadDistributionNetwork(ENTITIES_FILE_PATH_WITH_ONE_ENTERPRISE, DISTANCES_FILE_PATH);

    findNearestHubController = new FindNearestHubController(distributionNetwork);

    Map<Entity, Enterprise> expected = new HashMap<>();
    Iterator<Entity> iterator = entityStore.getEntities();
    while (iterator.hasNext()) {
      Entity entity = iterator.next();

      if (!(entity instanceof Producer))
        expected.put((Entity) entity, null);
    }

    Map<Entity, Enterprise> actual = findNearestHubController.findNearestHub();
    assertEquals(expected, actual);
    // 4 -> total number of clients (clients or enterprises)
    assertEquals(4, actual.size());
  }

  @Test
  public void testWithOneHub() throws FileNotFoundException, InvalidNumberOfHubsException {
    System.out.println("testWithOneHub");

    loadDistributionNetwork(ENTITIES_FILE_PATH_WITH_ONE_ENTERPRISE, DISTANCES_FILE_PATH);

    findNearestHubController = new FindNearestHubController(distributionNetwork);

    defineHubsController = new DefineHubsController(distributionNetwork);
    defineHubsController.defineHubs(1);

    Enterprise hub = (Enterprise) entityStore.getEntityByLocalizationId("CT4");

    Map<Entity, Enterprise> expected = new HashMap<>();
    Iterator<Entity> iterator = entityStore.getEntities();
    while (iterator.hasNext()) {
      Entity entity = iterator.next();

      if (!(entity instanceof Producer))
        expected.put((Entity) entity, hub);
    }

    Map<Entity, Enterprise> actual = findNearestHubController.findNearestHub();
    assertEquals(expected, actual);
    // 4 -> total number of clients (clients or enterprises)
    assertEquals(4, actual.size());
  }

  @Test
  public void testWithTwoHubs() throws FileNotFoundException, InvalidNumberOfHubsException {
    System.out.println("testWithTwoHubs");

    loadDistributionNetwork(ENTITIES_FILE_PATH_WITH_TWO_ENTERPRISES, DISTANCES_FILE_PATH);

    findNearestHubController = new FindNearestHubController(distributionNetwork);

    defineHubsController = new DefineHubsController(distributionNetwork);
    defineHubsController.defineHubs(2);

    Enterprise hub1 = (Enterprise) entityStore.getEntityByLocalizationId("CT4");
    Enterprise hub2 = (Enterprise) entityStore.getEntityByLocalizationId("CT5");

    Map<Entity, Enterprise> expected = new HashMap<>();
    Iterator<Entity> iterator = entityStore.getEntities();
    while (iterator.hasNext()) {
      Entity entity = iterator.next();

      if (entity instanceof Producer)
        continue;
      if (entity.getLocalizationId().equals("CT5"))
        expected.put((Entity) entity, hub2);
      else
        expected.put((Entity) entity, hub1);
    }

    Map<Entity, Enterprise> actual = findNearestHubController.findNearestHub();
    assertEquals(expected, actual);
    // 6 -> total number of clients (clients or enterprises)
    assertEquals(6, actual.size());
  }

  @Test
  public void testWithBigFile() throws FileNotFoundException, InvalidNumberOfHubsException {
    System.out.println("testWithBigFile");

    System.out.println(">> Loading big file...");
    loadDistributionNetwork(ENTITIES_BIG_FILE_PATH, DISTANCES_BIG_FILE_PATH);
    System.out.println(">> Big file loaded!");
    findNearestHubController = new FindNearestHubController(distributionNetwork);

    System.out.println(">> Defining hubs...");
    defineHubsController = new DefineHubsController(distributionNetwork);
    defineHubsController.defineHubs(1);
    System.out.println(">> Hubs defined!");

    Enterprise hub = distributionNetwork.getHubs().get(0);

    Map<Entity, Enterprise> expected = new HashMap<>();
    Iterator<Entity> iterator = entityStore.getEntities();
    while (iterator.hasNext()) {
      Entity entity = iterator.next();

      if (!(entity instanceof Producer))
        expected.put((Entity) entity, hub);
    }

    System.out.println(">> Finding nearest hub...");
    Map<Entity, Enterprise> actual = findNearestHubController.findNearestHub();
    System.out.println(">> Nearest hub found!");
    assertEquals(expected, actual);
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
