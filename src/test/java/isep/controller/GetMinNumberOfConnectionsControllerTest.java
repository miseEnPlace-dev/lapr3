package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.mock.EntityStoreMock;
import isep.model.DistributionNetwork;
import isep.model.store.EntityStore;
import isep.utils.CSVReader;

public class GetMinNumberOfConnectionsControllerTest {
  private GetMinNumberOfConnectionsController controller;
  private static LoadDistributionNetworkController loadDistributionNetworkController;
  private static EntityStore entityStore;
  private static DistributionNetwork distributionNetwork;
  private final static String DISTANCESFILENAMEV3 = "src/test/resources/distancesSampleV3.csv";
  private final static String DISTANCESFILENAMESMALL = "src/test/resources/distancias_small.csv";
  private final static String DISTANCESFILENAMEBIG = "src/test/resources/distancias_big.csv";

  @BeforeAll
  public static void setup() throws FileNotFoundException {
    entityStore = new EntityStoreMock().mockEntityStoreFromSampleFile();
    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore,
        new CSVReader(DISTANCESFILENAMEV3).read());

  }

  @Test
  public void testGetMinimumNumOfConnectionsController() {
    System.out.println("testGetMinimumNumOfConnectionsController");
    distributionNetwork = loadDistributionNetworkController.loadDistributionNetwork();

    controller = new GetMinNumberOfConnectionsController(distributionNetwork);

    assertEquals(4, controller.getMinimumNumOfConnections());
  }

  @Test
  public void testGetMinimumNumOfConnectionsControllerDisconnected() throws FileNotFoundException {
    System.out.println("testGetMinimumNumOfConnectionsControllerDisconnected");
    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore,
        new CSVReader(DISTANCESFILENAMEV3).read());
    distributionNetwork = loadDistributionNetworkController.loadDistributionNetwork();

    controller = new GetMinNumberOfConnectionsController(distributionNetwork);

    assertTrue(controller.isConnected());

  }

  @Test
  public void testGetMinimumNumOfConnectionsControllerSmall() throws FileNotFoundException {
    System.out.println("testGetMinimumNumOfConnectionsControllerSmall");
    entityStore = new EntityStoreMock().mockEntityStoreWithSmallFile();
    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore,
        new CSVReader(DISTANCESFILENAMESMALL).read());
    distributionNetwork = loadDistributionNetworkController.loadDistributionNetwork();

    controller = new GetMinNumberOfConnectionsController(distributionNetwork);

    assertEquals(6, controller.getMinimumNumOfConnections());

  }

  @Test
  public void testGetMinimumNumOfConnectionsControllerBig() throws FileNotFoundException {
    System.out.println("testGetMinimumNumOfConnectionsControllerBig");
    entityStore = new EntityStoreMock().mockEntityStoreWithBigFile();
    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore,
        new CSVReader(DISTANCESFILENAMEBIG).read());
    distributionNetwork = loadDistributionNetworkController.loadDistributionNetwork();

    controller = new GetMinNumberOfConnectionsController(distributionNetwork);

    assertEquals(28, controller.getMinimumNumOfConnections());
  }

  @Test
  public void testGetMinimumNumOfConnectionsControllerDisconnectedSmall() throws FileNotFoundException {
    System.out.println("testGetMinimumNumOfConnectionsControllerDisconnectedSmall");
    entityStore = new EntityStoreMock().mockEntityStoreWithSmallFile();
    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore,
        new CSVReader(DISTANCESFILENAMESMALL).read());
    distributionNetwork = loadDistributionNetworkController.loadDistributionNetwork();

    controller = new GetMinNumberOfConnectionsController(distributionNetwork);

    assertTrue(controller.isConnected());

  }

  @Test
  public void testGetMinimumNumOfConnectionsControllerDisconnectedBig() throws FileNotFoundException {
    System.out.println("testGetMinimumNumOfConnectionsControllerDisconnectedBig");
    entityStore = new EntityStoreMock().mockEntityStoreWithBigFile();
    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore,
        new CSVReader(DISTANCESFILENAMEBIG).read());
    distributionNetwork = loadDistributionNetworkController.loadDistributionNetwork();

    controller = new GetMinNumberOfConnectionsController(distributionNetwork);

    assertTrue(controller.isConnected());

  }

  @Test
  public void testGetMinimumNumOfConnectionsControllerWithNoDistributionNetwork() {
    System.out.println("testGetMinimumNumOfConnectionsControllerWithNoDistributionNetwork");
    controller = new GetMinNumberOfConnectionsController(null);

    assertThrows(IllegalArgumentException.class, () -> {
      controller.getMinimumNumOfConnections();
    });
  }

  @Test
  public void testGetMinimumNumOfConnectionsControllerWithNoDistributionNetworkDisconnected() {
    System.out.println("testGetMinimumNumOfConnectionsControllerWithNoDistributionNetworkDisconnected");
    controller = new GetMinNumberOfConnectionsController(null);

    assertThrows(NullPointerException.class, () -> {
      controller.isConnected();
    });
  }
}
