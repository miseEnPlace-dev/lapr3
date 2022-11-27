package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.controller.LoadDistributionNetworkController;
import isep.mock.EntityStoreMock;
import isep.model.store.EntityStore;
import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Edge;
import isep.utils.graph.Graph;

/*
 * Test connected network shortest path
 *
 * @author André Barros <1211299@isep.ipp.pt>
 */
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
    System.out.println("testShortestPathSourceNotNullNumVertices");
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    assertEquals(connectedNetworkShortestPath.getConnectedNetworkShortestPath(network).numVertices(), 9);
  }

  /*
   * Test connected network shortest path num edges
   */
  @Test
  public void testShortestPathSourceNotNullNumEdges() {
    System.out.println("testShortestPathSourceNotNullNumEdges");
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Graph<Entity, Integer> graph = connectedNetworkShortestPath.getConnectedNetworkShortestPath(network);

    assertEquals(graph.numEdges(), 16);
    assertEquals(graph.numVertices(), 9);
  }

  /*
   * Test connected network shortest path with entity in network using another
   * file
   */
  @Test
  public void testShortestPathSourceInNetworkAnotherFile() throws FileNotFoundException {
    System.out.println("testShortestPathSourceInNetworkAnotherFile");
    String distancesFileName = "src/test/resources/distancesSampleV3.csv";
    LoadDistributionNetworkController loadDistributionNetworkController = new LoadDistributionNetworkController(
        entityStore,
        distancesFileName);

    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Graph<Entity, Integer> graph = connectedNetworkShortestPath.getConnectedNetworkShortestPath(network);

    assertEquals(graph.numVertices(), 9);
    assertEquals(graph.numEdges(), 16);
  }

  /*
   * Test inDegree of vertex in connected network shortest path
   */
  @Test
  public void testShortestPathInDegree() throws FileNotFoundException {
    System.out.println("testShortestPathInDegree");
    String distancesFileName = "src/test/resources/distancesSampleV3.csv";
    LoadDistributionNetworkController loadDistributionNetworkController = new LoadDistributionNetworkController(
        entityStore,
        distancesFileName);

    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Graph<Entity, Integer> graph = connectedNetworkShortestPath.getConnectedNetworkShortestPath(network);

    assertEquals(graph.inDegree(entityStore.getEntityByLocalizationId("CT1")), 1);
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
  @Test
  public void testShortestPathConnectedNetwork() throws FileNotFoundException {
    System.out.println("testShortestPathConnectedNetwork");
    String distancesFileName = "src/test/resources/distancesSampleV3.csv";
    LoadDistributionNetworkController loadDistributionNetworkController = new LoadDistributionNetworkController(
        entityStore,
        distancesFileName);

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

    Graph<Entity, Integer> result = connectedNetworkShortestPath.getConnectedNetworkShortestPath(network);

    Graph<Entity, Integer> expectedGraph = new AdjacencyMapGraph<>(false);
    expectedGraph.addVertex(ct1);
    expectedGraph.addVertex(ct2);
    expectedGraph.addVertex(ct3);
    expectedGraph.addVertex(ct4);
    expectedGraph.addVertex(ct5);
    expectedGraph.addVertex(ct6);
    expectedGraph.addVertex(ct7);
    expectedGraph.addVertex(ct8);
    expectedGraph.addVertex(ct9);

    expectedGraph.addEdge(ct3, ct5, 10000);
    expectedGraph.addEdge(ct5, ct3, 10000);
    expectedGraph.addEdge(ct8, ct3, 50467);
    expectedGraph.addEdge(ct3, ct8, 50467);
    expectedGraph.addEdge(ct9, ct5, 62655);
    expectedGraph.addEdge(ct5, ct9, 62655);
    expectedGraph.addEdge(ct8, ct1, 62877);
    expectedGraph.addEdge(ct1, ct8, 62877);
    expectedGraph.addEdge(ct2, ct7, 63448);
    expectedGraph.addEdge(ct7, ct2, 63448);
    expectedGraph.addEdge(ct7, ct6, 67584);
    expectedGraph.addEdge(ct6, ct7, 67584);
    expectedGraph.addEdge(ct8, ct4, 70717);
    expectedGraph.addEdge(ct4, ct8, 70717);
    expectedGraph.addEdge(ct7, ct5, 125041);
    expectedGraph.addEdge(ct5, ct7, 125041);

    int sumWeight = 0;
    // gets the sum of the weights of the edges
    for (Edge<Entity, Integer> weight : result.edges()) {
      sumWeight += weight.getWeight();
    }

    assertEquals(expectedGraph.numVertices(), result.numVertices());
    assertEquals(expectedGraph.numEdges(), result.numEdges());
    assertEquals(1025578, sumWeight);

    assertEquals(expectedGraph, result);
  }

  /*
   * Test connected network with file distancias_small.csv
   */
  @Test
  public void testShortestPathConnectedNetworkSmall() throws FileNotFoundException {
    System.out.println("testShortestPathConnectedNetworkSmall");
    String distancesFileName = "src/test/resources/distancias_small.csv";
    entityStore = new EntityStoreMock().mockSimpleEntityStore();

    LoadDistributionNetworkController loadDistributionNetworkController = new LoadDistributionNetworkController(
        entityStore,
        distancesFileName);

    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Graph<Entity, Integer> result = connectedNetworkShortestPath.getConnectedNetworkShortestPath(network);

    assertEquals(17, result.numVertices());
    assertEquals(32, result.numEdges());

    int sumWeight = 0;
    // gets the sum of the weights of the edges
    for (Edge<Entity, Integer> weight : result.edges()) {
      sumWeight += weight.getWeight();
    }

    assertEquals(2370464, sumWeight);

  }

  /*
   * Test connected network with file distancias_big.csv
   */
  @Test
  public void testShortestPathConnectedNetworkBig() throws FileNotFoundException {
    System.out.println("testShortestPathConnectedNetworkBig");
    String distancesFileName = "data/big/distancias_big.csv";
    entityStore = new EntityStoreMock().mockEntityStoreWithBigFile();

    LoadDistributionNetworkController loadDistributionNetworkController = new LoadDistributionNetworkController(
        entityStore,
        distancesFileName);

    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Graph<Entity, Integer> result = connectedNetworkShortestPath.getConnectedNetworkShortestPath(network);

    assertEquals(323, result.numVertices());
    assertEquals(644, result.numEdges());

    int sumWeight = 0;
    // gets the sum of the weights of the edges
    for (Edge<Entity, Integer> weight : result.edges()) {
      sumWeight += weight.getWeight();
    }

    assertEquals(8463964, sumWeight);

  }
}
