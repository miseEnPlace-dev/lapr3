package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import isep.mock.DistancesDataMock;
import isep.mock.EntityStoreMock;
import isep.model.DistributionNetwork;
import isep.model.Entity;
import isep.model.store.EntityStore;

public class LoadDistributionNetworkTest {
  private static EntityStore entityStore;
  private static LoadDistributionNetworkController loadDistributionNetworkController;

  @BeforeAll
  public static void setup() throws FileNotFoundException {
    entityStore = new EntityStoreMock().mockEntityStoreFromSampleFile();
    List<Map<String, String>> distancesData =
        new DistancesDataMock().mockDistancesDataFromSampleFile();

    loadDistributionNetworkController =
        new LoadDistributionNetworkController(entityStore, distancesData);
  }

  @Test
  public void testLoadDistributionNetworkFromSampleFile() {
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Entity ct7 = entityStore.getEntityByLocalizationId("CT7");
    Entity ct2 = entityStore.getEntityByLocalizationId("CT2");
    Entity ct6 = entityStore.getEntityByLocalizationId("CT6");

    Entity ct9 = entityStore.getEntityByLocalizationId("CT9");
    Entity ct5 = entityStore.getEntityByLocalizationId("CT5");

    assertEquals(network.getDistanceBetweenConnectedEntities(ct7, ct2), 63448);
    assertEquals(network.getDistanceBetweenConnectedEntities(ct7, ct6), 67584);
    assertEquals(network.getDistanceBetweenConnectedEntities(ct9, ct5), 62655);
  }

  @Test
  public void testLoadDistributionNetworkIsBidirectional() {
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Entity ct7 = entityStore.getEntityByLocalizationId("CT7");
    Entity ct2 = entityStore.getEntityByLocalizationId("CT2");

    assertEquals(network.getDistanceBetweenConnectedEntities(ct7, ct2),
        network.getDistanceBetweenConnectedEntities(ct2, ct7));
  }

  @Test
  public void testLoadDistributionNetworkWithBigFile() throws FileNotFoundException {
    entityStore = new EntityStoreMock().mockEntityStoreWithBigFile();
    List<Map<String, String>> distancesData =
        new DistancesDataMock().mockDistancesDataWithBigFile();

    loadDistributionNetworkController =
        new LoadDistributionNetworkController(entityStore, distancesData);

    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    assertEquals(323, network.getNumberOfEntities());
    assertEquals(783, network.getNumberOfRelations());
  }
}
