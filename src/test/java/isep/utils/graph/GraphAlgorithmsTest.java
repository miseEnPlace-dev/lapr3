package isep.utils.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphAlgorithmsTest {
  final Graph<String, Integer> completeMap = new AdjacencyMapGraph<>(false);
  Graph<String, Integer> incompleteMap = new AdjacencyMapGraph<>(false);
  Graph<Integer, Integer> integerGraphNotDirected = new AdjacencyMapGraph<>(false);

  @BeforeEach
  public void setUp() {
    completeMap.addVertex("Porto");
    completeMap.addVertex("Braga");
    completeMap.addVertex("Vila Real");
    completeMap.addVertex("Aveiro");
    completeMap.addVertex("Coimbra");
    completeMap.addVertex("Leiria");

    completeMap.addVertex("Viseu");
    completeMap.addVertex("Guarda");
    completeMap.addVertex("Castelo Branco");
    completeMap.addVertex("Lisboa");
    completeMap.addVertex("Faro");

    completeMap.addEdge("Porto", "Aveiro", 75);
    completeMap.addEdge("Porto", "Braga", 60);
    completeMap.addEdge("Porto", "Vila Real", 100);
    completeMap.addEdge("Viseu", "Guarda", 75);
    completeMap.addEdge("Guarda", "Castelo Branco", 100);
    completeMap.addEdge("Aveiro", "Coimbra", 60);
    completeMap.addEdge("Coimbra", "Lisboa", 200);
    completeMap.addEdge("Coimbra", "Leiria", 80);
    completeMap.addEdge("Aveiro", "Leiria", 120);
    completeMap.addEdge("Leiria", "Lisboa", 150);

    incompleteMap = completeMap.clone();

    completeMap.addEdge("Aveiro", "Viseu", 85);
    completeMap.addEdge("Leiria", "Castelo Branco", 170);
    completeMap.addEdge("Lisboa", "Faro", 280);

  }

  Comparator<Edge<Integer, Integer>> ceInteger = new Comparator<Edge<Integer, Integer>>() {
    @Override
    public int compare(Edge<Integer, Integer> o1, Edge<Integer, Integer> o2) {
      return o1.getWeight().compareTo(o2.getWeight());
    }
  };

  private void checkContentEquals(List<String> l1, List<String> l2, String msg) {
    Collections.sort(l1);
    Collections.sort(l2);
    assertEquals(l1, l2, msg);
  }

  /**
   * Test of BreadthFirstSearch method, of class GraphAlgorithms.
   */
  @Test
  public void testBreadthFirstSearch() {
    System.out.println("Test BreadthFirstSearch");

    Assertions.assertNull(GraphAlgorithms.BreadthFirstSearch(completeMap, "LX"),
        "Should be null if vertex does not exist");

    LinkedList<String> path = GraphAlgorithms.BreadthFirstSearch(incompleteMap, "Faro");

    assertEquals(1, path.size(), "Should be just one");

    assertEquals("Faro", path.peekFirst());

    path = GraphAlgorithms.BreadthFirstSearch(incompleteMap, "Porto");
    assertEquals(7, path.size(), "Should give seven vertices");

    assertEquals("Porto", path.removeFirst(), "BreathFirst Porto");

    LinkedList<String> expected = new LinkedList<>(Arrays.asList("Aveiro", "Braga", "Vila Real"));
    checkContentEquals(expected, path.subList(0, 3), "BreathFirst Porto");

    expected = new LinkedList<>(Arrays.asList("Coimbra", "Leiria"));
    checkContentEquals(expected, path.subList(3, 5), "BreathFirst Porto");

    expected = new LinkedList<>(Arrays.asList("Lisboa"));
    checkContentEquals(expected, path.subList(5, 6), "BreathFirst Porto");

    path = GraphAlgorithms.BreadthFirstSearch(incompleteMap, "Viseu");
    expected = new LinkedList<>(Arrays.asList("Viseu", "Guarda", "Castelo Branco"));
    assertEquals(expected, path, "BreathFirst Viseu");
  }

  /**
   * Test of DepthFirstSearch method, of class GraphAlgorithms.
   */
  @Test
  public void testDepthFirstSearch() {
    System.out.println("Test of DepthFirstSearch");

    assertNull(GraphAlgorithms.DepthFirstSearch(completeMap, "LX"), "Should be null if vertex does not exist");

    LinkedList<String> path = GraphAlgorithms.DepthFirstSearch(incompleteMap, "Faro");
    assertEquals(1, path.size(), "Should be just one");

    assertEquals("Faro", path.peekFirst());

    path = GraphAlgorithms.BreadthFirstSearch(incompleteMap, "Porto");
    assertEquals(7, path.size(), "Should give seven vertices");

    assertEquals("Porto", path.removeFirst(), "DepthFirst Porto");
    assertTrue(new LinkedList<>(Arrays.asList("Aveiro", "Braga", "Vila Real")).contains(path.removeFirst()),
        "DepthFirst Porto");

    path = GraphAlgorithms.BreadthFirstSearch(incompleteMap, "Viseu");
    List<String> expected = new LinkedList<>(Arrays.asList("Viseu", "Guarda", "Castelo Branco"));
    assertEquals(expected, path, "DepthFirst Viseu");
  }

  /**
   * Test of shortestPath method, of class GraphAlgorithms.
   */
  @Test
  public void testShortestPath() {
    System.out.println("Test of shortest path");

    LinkedList<String> shortPath = new LinkedList<>();

    Integer lenPath = GraphAlgorithms.shortestPath(completeMap, "Porto", "LX", Integer::compare, Integer::sum, 0,
        shortPath);
    assertNull(lenPath, "Length path should be null if vertex does not exist");
    assertEquals(0, shortPath.size(), "Shortest Path does not exist");

    lenPath = GraphAlgorithms.shortestPath(incompleteMap, "Porto", "Faro", Integer::compare, Integer::sum, 0,
        shortPath);
    assertNull(lenPath, "Length path should be null if vertex does not exist");
    assertEquals(0, shortPath.size(), "Shortest Path does not exist");

    lenPath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Porto", Integer::compare, Integer::sum, 0, shortPath);
    assertEquals(0, lenPath, "Length path should be 0 if vertices are the same");
    assertEquals(Arrays.asList("Porto"), shortPath, "Shortest Path only contains Porto");

    lenPath = GraphAlgorithms.shortestPath(incompleteMap, "Porto", "Lisboa", Integer::compare, Integer::sum, 0,
        shortPath);
    assertEquals(335, lenPath, "Length path should be 0 if vertices are the same");
    assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), shortPath, "Shortest Path Porto - Lisboa");

    lenPath = GraphAlgorithms.shortestPath(incompleteMap, "Braga", "Leiria", Integer::compare, Integer::sum, 0,
        shortPath);
    assertEquals(255, lenPath, "Length path should be 0 if vertices are the same");
    assertEquals(Arrays.asList("Braga", "Porto", "Aveiro", "Leiria"), shortPath, "Shortest Path Braga - Leiria");

    lenPath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Castelo Branco", Integer::compare, Integer::sum, 0,
        shortPath);
    assertEquals(335, lenPath, "Length path should be 0 if vertices are the same");
    assertEquals(Arrays.asList("Porto", "Aveiro", "Viseu", "Guarda", "Castelo Branco"), shortPath,
        "Shortest Path Porto - Castelo Branco");

    // Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco
    // should change shortest path between Porto and Castelo Branco

    completeMap.removeEdge("Aveiro", "Viseu");
    completeMap.addEdge("Leiria", "Castelo Branco", 170);

    lenPath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Castelo Branco", Integer::compare, Integer::sum, 0,
        shortPath);
    assertEquals(365, lenPath, "Length path should be 0 if vertices are the same");
    assertEquals(Arrays.asList("Porto", "Aveiro", "Leiria", "Castelo Branco"), shortPath,
        "Shortest Path Porto - Castelo Branco");
  }

  /**
   * Test of shortestPaths method, of class GraphAlgorithms.
   */
  @Test
  public void testShortestPaths() {
    System.out.println("Test of shortest path");

    ArrayList<LinkedList<String>> paths = new ArrayList<>();
    ArrayList<Integer> dists = new ArrayList<>();

    GraphAlgorithms.shortestPaths(completeMap, "Porto", Integer::compare, Integer::sum, 0, paths, dists);

    assertEquals(paths.size(), dists.size(), "There should be as many paths as sizes");
    assertEquals(completeMap.numVertices(), paths.size(), "There should be a path to every vertex");
    assertEquals(Arrays.asList("Porto"), paths.get(completeMap.key("Porto")),
        "Number of nodes should be 1 if source and vertex are the same");
    assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.key("Lisboa")),
        "Path to Lisbon");
    assertEquals(Arrays.asList("Porto", "Aveiro", "Viseu", "Guarda", "Castelo Branco"),
        paths.get(completeMap.key("Castelo Branco")), "Path to Castelo Branco");
    assertEquals(335, dists.get(completeMap.key("Castelo Branco")),
        "Path between Porto and Castelo Branco should be 335 Km");

    // Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco
    // should change shortest path between Porto and Castelo Branco
    completeMap.removeEdge("Aveiro", "Viseu");
    completeMap.addEdge("Leiria", "Castelo Branco", 170);
    GraphAlgorithms.shortestPaths(completeMap, "Porto", Integer::compare, Integer::sum, 0, paths, dists);
    assertEquals(365, dists.get(completeMap.key("Castelo Branco")),
        "Path between Porto and Castelo Branco should now be 365 Km");
    assertEquals(Arrays.asList("Porto", "Aveiro", "Leiria", "Castelo Branco"),
        paths.get(completeMap.key("Castelo Branco")), "Path to Castelo Branco");

    GraphAlgorithms.shortestPaths(incompleteMap, "Porto", Integer::compare, Integer::sum, 0, paths, dists);
    assertNull(dists.get(completeMap.key("Faro")), "Length path should be null if there is no path");
    assertEquals(335, dists.get(completeMap.key("Lisboa")), "Path between Porto and Lisboa should be 335 Km");
    assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.key("Lisboa")),
        "Path to Lisboa");

    GraphAlgorithms.shortestPaths(incompleteMap, "Braga", Integer::compare, Integer::sum, 0, paths, dists);
    assertEquals(255, dists.get(completeMap.key("Leiria")), "Path between Braga and Leiria should be 255 Km");
    assertEquals(Arrays.asList("Braga", "Porto", "Aveiro", "Leiria"), paths.get(completeMap.key("Leiria")),
        "Path to Leiria");
  }

  /**
   * Test minimum distance graph using Floyd-Warshall.
   */
  // @Test
  public void testminDistGraph() {
    // throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Test the is connected method.
   */
  @Test
  public void testIsConnected() {
    System.out.println("Test of isConnected");

    assertTrue(GraphAlgorithms.isConnected(completeMap), "Complete Map should be connected");
    assertFalse(GraphAlgorithms.isConnected(incompleteMap), "Incomplete Map should not be connected");
  }

  /**
   * Tests if it is possible to find the minimum number of connections for any two
   * nodes to be connected.
   *
   * Vila Real/Braga - Porto - Aveiro - Coimbra - Lisboa - Faro -> 5 connections
   */
  @Test
  public void test1() {
    LinkedList<String> shortestPath = GraphAlgorithms.shortestPathBetweenFarthestNodes(completeMap, Integer::compare,
        Integer::sum, 0);
    int shortestPathConnections = shortestPath.size() - 1;
    assertEquals(5, shortestPathConnections);
  }

  /**
   * Test of minimumSpanningTree method, of class GraphAlgorithms, with
   * notDirected graph.
   */
  @Test
  public void testMinimumSpanningTree() {
    System.out.println("Test of minimum spanning tree");

    integerGraphNotDirected.addVertex(0);
    integerGraphNotDirected.addVertex(1);
    integerGraphNotDirected.addVertex(2);
    integerGraphNotDirected.addVertex(3);
    integerGraphNotDirected.addVertex(4);
    integerGraphNotDirected.addVertex(5);
    integerGraphNotDirected.addVertex(6);
    integerGraphNotDirected.addVertex(7);
    integerGraphNotDirected.addVertex(8);

    integerGraphNotDirected.addEdge(0, 1, 4);
    integerGraphNotDirected.addEdge(0, 7, 8);
    integerGraphNotDirected.addEdge(1, 7, 11);
    integerGraphNotDirected.addEdge(1, 2, 8);
    integerGraphNotDirected.addEdge(7, 6, 1);
    integerGraphNotDirected.addEdge(7, 8, 7);
    integerGraphNotDirected.addEdge(2, 3, 7);
    integerGraphNotDirected.addEdge(2, 8, 2);
    integerGraphNotDirected.addEdge(2, 5, 4);
    integerGraphNotDirected.addEdge(6, 5, 2);
    integerGraphNotDirected.addEdge(6, 8, 6);
    integerGraphNotDirected.addEdge(3, 4, 9);
    integerGraphNotDirected.addEdge(3, 5, 14);
    integerGraphNotDirected.addEdge(5, 4, 10);

    Graph<Integer, Integer> result = GraphAlgorithms.kruskall(integerGraphNotDirected, ceInteger);

    Graph<Integer, Integer> expectedGraph = new AdjacencyMapGraph<Integer, Integer>(false);
    expectedGraph.addVertex(0);
    expectedGraph.addVertex(1);
    expectedGraph.addVertex(2);
    expectedGraph.addVertex(3);
    expectedGraph.addVertex(4);
    expectedGraph.addVertex(5);
    expectedGraph.addVertex(6);
    expectedGraph.addVertex(7);
    expectedGraph.addVertex(8);

    expectedGraph.addEdge(7, 6, 1);
    expectedGraph.addEdge(6, 7, 1);
    expectedGraph.addEdge(6, 5, 2);
    expectedGraph.addEdge(5, 6, 2);
    expectedGraph.addEdge(2, 8, 2);
    expectedGraph.addEdge(8, 2, 2);
    expectedGraph.addEdge(0, 1, 4);
    expectedGraph.addEdge(1, 0, 4);
    expectedGraph.addEdge(5, 2, 4);
    expectedGraph.addEdge(2, 5, 4);
    expectedGraph.addEdge(2, 3, 7);
    expectedGraph.addEdge(3, 2, 7);
    expectedGraph.addEdge(0, 7, 8);
    expectedGraph.addEdge(7, 0, 8);
    expectedGraph.addEdge(3, 4, 9);
    expectedGraph.addEdge(4, 3, 9);
    assertEquals(expectedGraph.numVertices(), result.numVertices(), "The number of vertices should be the same");
    assertEquals(expectedGraph.numEdges(), result.numEdges(), "The number of edges should be the same");

    int totalWeight = 0;
    for (Edge<Integer, Integer> edge : result.edges()) {
      totalWeight += edge.getWeight();
    }
    assertEquals(74, totalWeight, "The total weight of the minimum spanning tree should be 74");
    assertEquals(expectedGraph, result);
  }
}
