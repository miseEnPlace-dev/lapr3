package isep.utils.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import isep.model.VertexHeuristic;
import isep.utils.HaversineCalculator;
import isep.utils.graph.astar.AStar;

/**
 * Unit tests for the A* algorithm.
 * 
 * @author Tomás Russo <121128@isep.ipp.pt>
 */
public class AStarTest {
  class Vertex implements VertexHeuristic<Vertex> {
    private String name;
    private double latitude;
    private double longitude;

    public Vertex(String name, double latitude, double longitude) {
      this.name = name;
      this.latitude = latitude;
      this.longitude = longitude;
    }

    public String getName() {
      return name;
    }

    public double getLatitude() {
      return latitude;
    }

    public double getLongitude() {
      return longitude;
    }

    public int getHeuristicValue(Vertex target) {
      return (int) HaversineCalculator.getDistanceBetweenTwoCoordinates(this.latitude, this.longitude,
          target.latitude, target.longitude);
    }

    @Override
    public String toString() {
      return name;
    }
  }

  final Graph<Vertex, Integer> completeMap = new AdjacencyMapGraph<>(false);

  Vertex porto = new Vertex("Porto", 41.14961, -8.61099);
  Vertex braga = new Vertex("Braga", 41.54545, -8.42653);
  Vertex vilaReal = new Vertex("Vila Real", 41.30045, -7.74482);
  Vertex aveiro = new Vertex("Aveiro", 40.64427, -8.64554);
  Vertex coimbra = new Vertex("Coimbra", 40.20331, -8.41025);
  Vertex leiria = new Vertex("Leiria", 39.74383, -8.80777);
  Vertex viseu = new Vertex("Viseu", 40.65716, -7.90907);
  Vertex guarda = new Vertex("Guarda", 40.53726, -7.26337);
  Vertex casteloBranco = new Vertex("Castelo Branco", 39.81909, -7.43889);
  Vertex lisboa = new Vertex("Lisboa", 38.72225, -9.13934);
  Vertex faro = new Vertex("Faro", 37.01987, -7.93206);
  Vertex madeira = new Vertex("Madeira", 32.63333, -16.9);

  @BeforeEach
  public void setUp() {
    completeMap.addVertex(porto);
    completeMap.addVertex(braga);
    completeMap.addVertex(vilaReal);
    completeMap.addVertex(aveiro);
    completeMap.addVertex(coimbra);
    completeMap.addVertex(leiria);
    completeMap.addVertex(viseu);
    completeMap.addVertex(guarda);
    completeMap.addVertex(casteloBranco);
    completeMap.addVertex(lisboa);
    completeMap.addVertex(faro);

    completeMap.addEdge(porto, aveiro, 75);
    completeMap.addEdge(porto, braga, 60);
    completeMap.addEdge(porto, vilaReal, 100);
    completeMap.addEdge(viseu, guarda, 75);
    completeMap.addEdge(guarda, casteloBranco, 100);
    completeMap.addEdge(aveiro, coimbra, 60);
    completeMap.addEdge(coimbra, lisboa, 200);
    completeMap.addEdge(coimbra, leiria, 80);
    completeMap.addEdge(aveiro, leiria, 120);
    completeMap.addEdge(leiria, lisboa, 150);
    completeMap.addEdge(aveiro, viseu, 85);
    completeMap.addEdge(leiria, casteloBranco, 170);
    completeMap.addEdge(lisboa, faro, 280);
  }

  Comparator<Edge<Integer, Integer>> ceInteger = new Comparator<Edge<Integer, Integer>>() {
    @Override
    public int compare(Edge<Integer, Integer> o1, Edge<Integer, Integer> o2) {
      return o1.getWeight().compareTo(o2.getWeight());
    }
  };

  @Test
  public void testWithNonExistentVertex() {
    List<Vertex> result = AStar.findShortestPath(completeMap, porto, madeira);
    assertNull(result);
  }

  @Test
  public void testFromOPOToOPO() {
    List<Vertex> result = AStar.findShortestPath(completeMap, porto, porto);
    List<Vertex> expected = new ArrayList<>();
    expected.add(porto);
    assertEquals(expected, result);
  }

  @Test
  public void testFromOPOToLIS() {
    List<Vertex> result = AStar.findShortestPath(completeMap, porto, lisboa);
    List<Vertex> expected = new ArrayList<>();
    expected.add(porto);
    expected.add(aveiro);
    expected.add(coimbra);
    expected.add(lisboa);
    assertEquals(expected, result);
  }

  @Test
  public void testFromBGZToLEI() {
    List<Vertex> result = AStar.findShortestPath(completeMap, braga, leiria);
    List<Vertex> expected = new ArrayList<>();
    expected.add(braga);
    expected.add(porto);
    expected.add(aveiro);
    expected.add(leiria);
    assertEquals(expected, result);
  }

  @Test
  public void testFromVRLToLIS() {
    List<Vertex> result = AStar.findShortestPath(completeMap, vilaReal, lisboa);
    List<Vertex> expected = new ArrayList<>();
    expected.add(vilaReal);
    expected.add(porto);
    expected.add(aveiro);
    expected.add(coimbra);
    expected.add(lisboa);
    assertEquals(expected, result);
  }

  @Test
  public void testFromOPOToCBA() {
    List<Vertex> result = AStar.findShortestPath(completeMap, porto, casteloBranco);
    List<Vertex> expected = new ArrayList<>();
    expected.add(porto);
    expected.add(aveiro);
    expected.add(viseu);
    expected.add(guarda);
    expected.add(casteloBranco);
    assertEquals(expected, result);
  }

  @Test
  public void testFromOPOToFAO() {
    List<Vertex> result = AStar.findShortestPath(completeMap, porto, faro);
    List<Vertex> expected = new ArrayList<>();
    expected.add(porto);
    expected.add(aveiro);
    expected.add(coimbra);
    expected.add(lisboa);
    expected.add(faro);
    assertEquals(expected, result);
  }
}
