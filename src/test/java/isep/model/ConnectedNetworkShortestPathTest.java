package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.controller.LoadDistributionNetworkController;
import isep.mock.EntityStoreMock;
import isep.model.store.EntityStore;
import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Graph;

public class ConnectedNetworkShortestPathTest {
  private static EntityStore entityStore;
  private static LoadDistributionNetworkController loadDistributionNetworkController;
  private final static String distancesFileName = "src/test/resources/distancesSampleV2.csv";
  private ConnectedNetworkShortestPath connectedNetworkShortestPath = new ConnectedNetworkShortestPath();

  @BeforeAll
  public static void setup() throws FileNotFoundException {
    entityStore = new EntityStoreMock().mockEntityStoreFromSampleFile();

    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore, distancesFileName);
  }

  /*
   * Test connected network shortest path num vertices
   */
  @Test
  public void testShortestPathSourceNotNullNumVertices() {
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Entity ct1 = entityStore.getEntityByLocalizationId("CT1");

    assertEquals(connectedNetworkShortestPath.getConnectedNetworkShortestPath(ct1, network).numVertices(), 9);
  }

  /*
   * Test connected network shortest path num edges
   */
  @Test
  public void testShortestPathSourceNotNullNumEdges() {
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Entity ct1 = entityStore.getEntityByLocalizationId("CT1");

    assertEquals(connectedNetworkShortestPath.getConnectedNetworkShortestPath(ct1, network).numEdges(), 22);
  }

  /*
   * Test connected network shortest path with entity in network
   */
  // @Test
  public void testShortestPathSourceInNetwork() {
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Entity ct1 = entityStore.getEntityByLocalizationId("CT1");

    Graph<Entity, Integer> graph = connectedNetworkShortestPath.getConnectedNetworkShortestPath(ct1, network);

    assertEquals(graph.numVertices(), 9);
    assertEquals(graph.numEdges(), 22);
  }

  /*
   * Test connected network shortest path with entity in network using another
   * file
   */
  @Test
  public void testShortestPathSourceInNetworkAnotherFile() throws FileNotFoundException {
    String distancesFileName = "src/test/resources/distancesSampleV3.csv";
    LoadDistributionNetworkController loadDistributionNetworkController = new LoadDistributionNetworkController(
        entityStore,
        distancesFileName);

    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Entity ct9 = entityStore.getEntityByLocalizationId("CT9");

    Graph<Entity, Integer> graph = connectedNetworkShortestPath.getConnectedNetworkShortestPath(ct9, network);

    assertEquals(graph.numVertices(), 9);
    assertEquals(graph.numEdges(), 16);
  }

  /*
   * Test inDegree of vertex in connected network shortest path
   */
  @Test
  public void testShortestPathInDegree() throws FileNotFoundException {
    String distancesFileName = "src/test/resources/distancesSampleV3.csv";
    LoadDistributionNetworkController loadDistributionNetworkController = new LoadDistributionNetworkController(
        entityStore,
        distancesFileName);

    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Entity ct1 = entityStore.getEntityByLocalizationId("CT1");

    Graph<Entity, Integer> graph = connectedNetworkShortestPath.getConnectedNetworkShortestPath(ct1, network);

    assertEquals(graph.inDegree(ct1), 1);
    assertEquals(graph.inDegree(entityStore.getEntityByLocalizationId("CT2")), 1);
    assertEquals(graph.inDegree(entityStore.getEntityByLocalizationId("CT3")), 2);
    assertEquals(graph.inDegree(entityStore.getEntityByLocalizationId("CT4")), 1);
    assertEquals(graph.inDegree(entityStore.getEntityByLocalizationId("CT5")), 3);
    assertEquals(graph.inDegree(entityStore.getEntityByLocalizationId("CT6")), 1);
    assertEquals(graph.inDegree(entityStore.getEntityByLocalizationId("CT7")), 3);
    assertEquals(graph.inDegree(entityStore.getEntityByLocalizationId("CT8")), 3);
    assertEquals(graph.inDegree(entityStore.getEntityByLocalizationId("CT9")), 1);
  }

  /*
   * Test connected network shortest path
   */
  // @Test
  public void testShortestPathConnectedNetwork() {
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Entity ct1 = entityStore.getEntityByLocalizationId("CT1");
    Entity ct2 = entityStore.getEntityByLocalizationId("CT2");
    Entity ct3 = entityStore.getEntityByLocalizationId("CT3");
    Entity ct4 = entityStore.getEntityByLocalizationId("CT4");
    Entity ct5 = entityStore.getEntityByLocalizationId("CT5");
    Entity ct6 = entityStore.getEntityByLocalizationId("CT6");
    Entity ct7 = entityStore.getEntityByLocalizationId("CT7");
    Entity ct8 = entityStore.getEntityByLocalizationId("CT8");
    Entity ct9 = entityStore.getEntityByLocalizationId("CT9");

    Graph<Entity, Integer> result = connectedNetworkShortestPath.getConnectedNetworkShortestPath(ct9, network);

    Graph<Entity, Integer> expectedGraph = new AdjacencyMapGraph<>(true);
    expectedGraph.addEdge(ct9, ct5, 62655);
    expectedGraph.addEdge(ct5, ct9, 62655);
    expectedGraph.addEdge(ct5, ct7, 125041);
    expectedGraph.addEdge(ct7, ct5, 125041);
    expectedGraph.addEdge(ct7, ct6, 67584);
    expectedGraph.addEdge(ct6, ct7, 67584);
    expectedGraph.addEdge(ct6, ct8, 3000);
    expectedGraph.addEdge(ct8, ct6, 3000);
    expectedGraph.addEdge(ct8, ct1, 62877);
    expectedGraph.addEdge(ct1, ct8, 62877);
    expectedGraph.addEdge(ct1, ct2, 5000);
    expectedGraph.addEdge(ct2, ct1, 5000);
    expectedGraph.addEdge(ct2, ct3, 8000);
    expectedGraph.addEdge(ct3, ct2, 8000);
    expectedGraph.addEdge(ct3, ct4, 2000);
    expectedGraph.addEdge(ct4, ct3, 2000);
    System.out.println(expectedGraph.toString());

    assertEquals(expectedGraph, result);
  }
}
