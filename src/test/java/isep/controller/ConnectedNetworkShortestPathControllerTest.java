package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import isep.mock.EntityStoreMock;
import isep.model.DistributionNetwork;
import isep.model.Entity;
import isep.model.store.EntityStore;
import isep.shared.exceptions.NetworkNotConnectedException;
import isep.utils.CSVReader;
import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Edge;
import isep.utils.graph.Graph;

/*
 * Test connected network shortest path controller
 *
 * @author André Barros <1211299@isep.ipp.pt>
 */
public class ConnectedNetworkShortestPathControllerTest {

  private static LoadDistributionNetworkController loadDistributionNetworkController;
  private static EntityStore entityStore;
  private static ConnectedNetworkShortestPathController connectedNetworkShortestPathController;
  private final static String DISTANCESFILENAMEV3 = "src/test/resources/distancesSampleV3.csv";
  private final static String DISTANCESFILENAMESMALL = "src/test/resources/distancias_small.csv";
  private final static String DISTANCESFILENAMEBIG = "src/test/resources/distancias_big.csv";

  @BeforeAll
  public static void setup() throws FileNotFoundException {
    entityStore = new EntityStoreMock().mockEntityStoreFromSampleFile();
    loadDistributionNetworkController = new LoadDistributionNetworkController(entityStore,
        new CSVReader(DISTANCESFILENAMEV3).read());

  }

  /*
   * Test connected network shortest path controller
   */
  @Test
  public void testGetConnectedNetworkShortestPathCtrl() throws NetworkNotConnectedException {
    System.out.println("testGetConnectedNetworkShortestPathCtrl");
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
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().numVertices(),
        graph.numVertices());
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().numEdges(), graph.numEdges());

  }

  /*
   * Test network null assertThrow
   */
  @Test
  public void testGetConnectedNetworkShortestCtrlNetworkNull() throws NetworkNotConnectedException {
    System.out.println("testGetConnectedNetworkShortestCtrlNetworkNull");
    assertThrows(IllegalArgumentException.class, () -> {
      connectedNetworkShortestPathController = new ConnectedNetworkShortestPathController(null);
    });
  }

  /*
   * Test connected network shortest path controller inDegree vertices
   */
  @Test
  public void testGetConnectedNetworkkShortestPathCtrlInDegree() throws NetworkNotConnectedException {
    System.out.println("testGetConnectedNetworkkShortestPathCtrlInDegree");
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

    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().inDegree(ct1),
        graph.inDegree(ct1));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().inDegree(ct2),
        graph.inDegree(ct2));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().inDegree(ct3),
        graph.inDegree(ct3));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().inDegree(ct4),
        graph.inDegree(ct4));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().inDegree(ct5),
        graph.inDegree(ct5));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().inDegree(ct6),
        graph.inDegree(ct6));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().inDegree(ct7),
        graph.inDegree(ct7));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().inDegree(ct8),
        graph.inDegree(ct8));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().inDegree(ct9),
        graph.inDegree(ct9));

  }

  /*
   * Test connected network shortest path controller outDegree vertices
   */
  @Test
  public void testGetConnectedNetworkkShortestPathCtrlOutDegree() throws NetworkNotConnectedException {
    System.out.println("testGetConnectedNetworkkShortestPathCtrlOutDegree");

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

    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().outDegree(ct1),
        graph.outDegree(ct1));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().outDegree(ct2),
        graph.outDegree(ct2));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().outDegree(ct3),
        graph.outDegree(ct3));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().outDegree(ct4),
        graph.outDegree(ct4));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().outDegree(ct5),
        graph.outDegree(ct5));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().outDegree(ct6),
        graph.outDegree(ct6));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().outDegree(ct7),
        graph.outDegree(ct7));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().outDegree(ct8),
        graph.outDegree(ct8));
    assertEquals(connectedNetworkShortestPathController.getConnectedNetworkShortestPath().outDegree(ct9),
        graph.outDegree(ct9));
  }

  /*
   * Test connected network shortest path num vertices
   */
  @Test
  public void testShortestPathSourceNotNullNumVertices() throws NetworkNotConnectedException {
    System.out.println("testShortestPathSourceNotNullNumVertices");
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    assertEquals(network.getMinimumShortestPathNetwork().numVertices(), 9);
  }

  /*
   * Test connected network shortest path num edges
   */
  @Test
  public void testShortestPathSourceNotNullNumEdges() throws NetworkNotConnectedException {
    System.out.println("testShortestPathSourceNotNullNumEdges");
    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Graph<Entity, Integer> graph = network.getMinimumShortestPathNetwork();

    assertEquals(graph.numEdges(), 16);
    assertEquals(graph.numVertices(), 9);
  }

  /*
   * Test connected network shortest path with entity in network using another
   * file
   */
  @Test
  public void testShortestPathSourceInNetworkAnotherFile() throws FileNotFoundException, NetworkNotConnectedException {
    System.out.println("testShortestPathSourceInNetworkAnotherFile");
    LoadDistributionNetworkController loadDistributionNetworkController = new LoadDistributionNetworkController(
        entityStore,
        new CSVReader(DISTANCESFILENAMEV3).read());

    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Graph<Entity, Integer> graph = network.getMinimumShortestPathNetwork();

    assertEquals(graph.numVertices(), 9);
    assertEquals(graph.numEdges(), 16);
  }

  /*
   * Test connected network shortest path
   */
  @Test
  public void testShortestPathConnectedNetwork() throws FileNotFoundException, NetworkNotConnectedException {
    System.out.println("testShortestPathConnectedNetwork");
    LoadDistributionNetworkController loadDistributionNetworkController = new LoadDistributionNetworkController(
        entityStore,
        new CSVReader(DISTANCESFILENAMEV3).read());

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

    Graph<Entity, Integer> result = network.getMinimumShortestPathNetwork();

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
  public void testShortestPathConnectedNetworkSmall() throws FileNotFoundException, NetworkNotConnectedException {
    System.out.println("testShortestPathConnectedNetworkSmall");
    entityStore = new EntityStoreMock().mockSimpleEntityStore();

    LoadDistributionNetworkController loadDistributionNetworkController = new LoadDistributionNetworkController(
        entityStore,
        new CSVReader(DISTANCESFILENAMESMALL).read());

    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Graph<Entity, Integer> result = network.getMinimumShortestPathNetwork();

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
  public void testShortestPathConnectedNetworkBig() throws FileNotFoundException, NetworkNotConnectedException {
    System.out.println("testShortestPathConnectedNetworkBig");
    entityStore = new EntityStoreMock().mockEntityStoreWithBigFile();

    LoadDistributionNetworkController loadDistributionNetworkController = new LoadDistributionNetworkController(
        entityStore,
        new CSVReader(DISTANCESFILENAMEBIG).read());

    DistributionNetwork network = loadDistributionNetworkController.loadDistributionNetwork();

    Graph<Entity, Integer> result = network.getMinimumShortestPathNetwork();

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
