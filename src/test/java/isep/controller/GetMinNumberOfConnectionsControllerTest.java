package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    distributionNetwork = loadDistributionNetworkController.loadDistributionNetwork();

    controller = new GetMinNumberOfConnectionsController(distributionNetwork);

    assertEquals(4, controller.getMinimumNumOfConnections());
  }

  @Test
  public void testGetMinimumNumOfConnectionsControllerDisconnected() throws FileNotFoundException {
    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore,
        new CSVReader(DISTANCESFILENAMEV3).read());
    distributionNetwork = loadDistributionNetworkController.loadDistributionNetwork();

    controller = new GetMinNumberOfConnectionsController(distributionNetwork);

    assertTrue(controller.isConnected());

  }

  @Test
  public void testGetMinimumNumOfConnectionsControllerSmall() throws FileNotFoundException {
    entityStore = new EntityStoreMock().mockEntityStoreWithSmallFile();
    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore,
        new CSVReader(DISTANCESFILENAMESMALL).read());
    distributionNetwork = loadDistributionNetworkController.loadDistributionNetwork();

    controller = new GetMinNumberOfConnectionsController(distributionNetwork);

    assertEquals(6, controller.getMinimumNumOfConnections());

  }

  @Test
  public void testGetMinimumNumOfConnectionsControllerBig() throws FileNotFoundException {
    entityStore = new EntityStoreMock().mockEntityStoreWithBigFile();
    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore,
        new CSVReader(DISTANCESFILENAMEBIG).read());
    distributionNetwork = loadDistributionNetworkController.loadDistributionNetwork();

    controller = new GetMinNumberOfConnectionsController(distributionNetwork);

    assertEquals(28, controller.getMinimumNumOfConnections());
  }

}
