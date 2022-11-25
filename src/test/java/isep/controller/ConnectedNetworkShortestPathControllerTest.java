package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.controller.LoadDistributionNetworkController;
import isep.mock.EntityStoreMock;
import isep.model.DistributionNetwork;
import isep.model.Entity;
import isep.model.store.EntityStore;
import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Graph;

public class ConnectedNetworkShortestPathControllerTest {

  private static LoadDistributionNetworkController loadDistributionNetworkController;
  private static EntityStore entityStore;
  private static ConnectedNetworkShortestPathController connectedNetworkShortestPathController;

  @BeforeAll
  public static void setup() throws FileNotFoundException {
    entityStore = new EntityStoreMock().mockEntityStoreFromSampleFile();
    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore,
        "src/test/resources/distancesSampleV3.csv");

  }

  @Test
  public void testGetConnectedNetworkShortestPath() {
    connectedNetworkShortestPathController = new ConnectedNetworkShortestPathController(
        loadDistributionNetworkController.loadDistributionNetwork());

    Graph<Entity, Integer> graph = new AdjacencyMapGraph<>(false);
    Entity ct1 = entityStore.getEntityByLocalizationId("CT1");
    Entity ct2 = entityStore.getEntityByLocalizationId("CT2");
    Entity ct3 = entityStore.getEntityByLocalizationId("CT3");
    Entity ct4 = entityStore.getEntityByLocalizationId("CT4");
    Entity ct5 = entityStore.getEntityByLocalizationId("CT5");
    Entity ct6 = entityStore.getEntityByLocalizationId("CT6");
    Entity ct7 = entityStore.getEntityByLocalizationId("CT7");
    Entity ct8 = entityStore.getEntityByLocalizationId("CT8");
    Entity ct9 = entityStore.getEntityByLocalizationId("CT9");

    graph.addVertex(ct1);
    graph.addVertex(ct2);
    graph.addVertex(ct3);
    graph.addVertex(ct4);
    graph.addVertex(ct5);
    graph.addVertex(ct6);
    graph.addVertex(ct7);
    graph.addVertex(ct8);
    graph.addVertex(ct9);

    graph.addEdge(ct3, ct5, 10000);
    graph.addEdge(ct5, ct3, 10000);
    graph.addEdge(ct8, ct3, 50467);
    graph.addEdge(ct3, ct8, 50467);
    graph.addEdge(ct9, ct5, 62655);
    graph.addEdge(ct5, ct9, 62655);
    graph.addEdge(ct8, ct1, 62877);
    graph.addEdge(ct1, ct8, 62877);
    graph.addEdge(ct2, ct7, 63448);
    graph.addEdge(ct7, ct2, 63448);
    graph.addEdge(ct7, ct6, 67584);
    graph.addEdge(ct6, ct7, 67584);
    graph.addEdge(ct8, ct4, 70717);
    graph.addEdge(ct4, ct8, 70717);
    graph.addEdge(ct7, ct5, 125041);
    graph.addEdge(ct5, ct7, 125041);

    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath(), graph);
  }

  /*
   * Test network null assertThrow
   */
  @Test
  public void testGetConnectedNetworkShortestPathNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      connectedNetworkShortestPathController = new ConnectedNetworkShortestPathController(null);
    });
  }

}
