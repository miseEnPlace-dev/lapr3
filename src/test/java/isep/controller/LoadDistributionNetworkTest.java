package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.mock.EntityStoreMock;
import isep.model.DistributionNetwork;
import isep.model.Entity;
import isep.model.store.EntityStore;

public class LoadDistributionNetworkTest {
  private final static String distancesFileName = "src/test/resources/distancesSample.csv";
  private static EntityStore entityStore;
  private static LoadDistributionNetworkController loadDistributionNetworkController;

  @BeforeAll
  public static void setup() throws FileNotFoundException {
    entityStore = new EntityStoreMock().mockEntityStoreFromSampleFile();

    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore, distancesFileName);
  }

  @Test
  public void testLoadDistributionNetworkFromSampleFile() {
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Entity ct7 = entityStore.getEntityByLocalizationId("CT7");
    Entity ct2 = entityStore.getEntityByLocalizationId("CT2");
    Entity ct6 = entityStore.getEntityByLocalizationId("CT6");

    Entity ct9 = entityStore.getEntityByLocalizationId("CT9");
    Entity ct5 = entityStore.getEntityByLocalizationId("CT5");

    assertEquals(network.getDistanceBetween(ct7, ct2), 63448);
    assertEquals(network.getDistanceBetween(ct7, ct6), 67584);
    assertEquals(network.getDistanceBetween(ct9, ct5), 62655);
  }

  @Test
  public void testLoadDistributionNetworkIsBidirectional() {
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Entity ct7 = entityStore.getEntityByLocalizationId("CT7");
    Entity ct2 = entityStore.getEntityByLocalizationId("CT2");

    assertEquals(network.getDistanceBetween(ct7, ct2), network.getDistanceBetween(ct2, ct7));
  }
}
