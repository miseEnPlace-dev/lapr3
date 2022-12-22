package isep.utils.graph.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import isep.model.VertexHeuristic;
import isep.utils.graph.Graph;

/**
 * Class that implements the A* algorithm.
 * 
 * Addapted from https://www.happycoders.eu/algorithms/a-star-algorithm-java/
 * 
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
public class AStar {
  /**
   * Finds the shortest path between two vertices in a graph.
   * 
   * @param graph  the graph
   * @param start  the start vertex
   * @param target the target vertex
   * @return the shortest path between the start and target vertex
   */
  public static <V extends VertexHeuristic<V>> List<V> findShortestPath(Graph<V, Integer> graph, V start, V target) {
    TreeSet<VertexWrapper<V>> queue = new TreeSet<>();
    Map<V, VertexWrapper<V>> vertexWrappers = new HashMap<>();
    Set<V> shortestPathFound = new HashSet<>();

    VertexWrapper<V> sourceWrapper = new VertexWrapper<V>(start, null, 0, start.getHeuristicValue(target));
    vertexWrappers.put(start, sourceWrapper);
    queue.add(sourceWrapper);

    while (!queue.isEmpty()) {
      VertexWrapper<V> vertexWrapper = queue.pollFirst();
      V vertex = vertexWrapper.getVertex();
      shortestPathFound.add(vertex);

      // Have we reached the target? --> Build and return the path
      if (vertex.equals(target)) {
        return buildPath(vertexWrapper);
      }

      // Iterate over all neighbors
      ArrayList<V> neighbors = (ArrayList<V>) graph.adjVertices(vertex);
      for (V neighbour : neighbors) {
        // Ignore neighbour if shortest path already found
        if (shortestPathFound.contains(neighbour)) {
          continue;
        }

        // Calculate total cost from start to neighbour via current node
        int cost = graph.edge(vertex, neighbour).getWeight();
        int totalCostFromStart = vertexWrapper.getTotalCostFromStart() + cost;

        // Neighbour not yet discovered?
        VertexWrapper<V> neighbourWrapper = vertexWrappers.get(neighbour);
        if (neighbourWrapper == null) {
          neighbourWrapper = new VertexWrapper<>(neighbour, vertexWrapper, totalCostFromStart,
              neighbour.getHeuristicValue(target));
          vertexWrappers.put(neighbour, neighbourWrapper);
          queue.add(neighbourWrapper);
        }

        // Neighbour discovered, but total cost via current node is lower?
        // --> Update costs and predecessor
        else if (totalCostFromStart < neighbourWrapper.getTotalCostFromStart()) {
          queue.remove(neighbourWrapper);

          neighbourWrapper.setTotalCostFromStart(totalCostFromStart);
          neighbourWrapper.setPredecessor(vertexWrapper);

          queue.add(neighbourWrapper);
        }
      }
    }

    // All nodes were visited but the target was not found
    return null;
  }

  private static <V> List<V> buildPath(VertexWrapper<V> vertexWrapper) {
    List<V> path = new ArrayList<>();

    while (vertexWrapper != null) {
      path.add(vertexWrapper.getVertex());
      vertexWrapper = vertexWrapper.getPredecessor();
    }

    Collections.reverse(path);
    return path;
  }
}
