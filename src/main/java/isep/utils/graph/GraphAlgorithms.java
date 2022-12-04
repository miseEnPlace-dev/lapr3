package isep.utils.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BinaryOperator;

import isep.utils.MergeSort;

/**
 *
 * @author DEI-ISEP
 * @author Tomás Lopes <1211289@isep.ipp.pt>
 */
public class GraphAlgorithms {
  /**
   * Performs breadth-first search of a Graph starting in a vertex
   *
   * @param g    Graph instance
   * @param vert vertex that will be the source of the search
   * @return a LinkedList with the vertices of breadth-first search
   */
  public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {
    Queue<V> queue = new LinkedList<>();
    LinkedList<V> visited = new LinkedList<>();

    queue.add(vert);
    visited.add(vert);

    while (!queue.isEmpty()) {
      V v = queue.poll();
      Collection<V> adj = g.adjVertices(v);
      if (adj == null)
        return null;
      for (V w : adj) {
        if (!visited.contains(w)) {
          visited.add(w);
          queue.add(w);
        }
      }
    }

    return visited;
  }

  /**
   * Performs depth-first search starting in a vertex
   *
   * @param g       Graph instance
   * @param vOrig   vertex of graph g that will be the source of the search
   * @param visited set of previously visited vertices
   * @param qdfs    return LinkedList with vertices of depth-first search
   */
  private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {
    int key = g.key(vOrig);
    if (key == -1)
      return;
    visited[key] = true;
    qdfs.add(vOrig);
    Collection<V> adj = g.adjVertices(vOrig);
    if (adj == null)
      return;
    for (V v : adj) {
      key = g.key(v);
      if (key == -1)
        return;
      if (!visited[key])
        DepthFirstSearch(g, v, visited, qdfs);
    }
  }

  /**
   * Performs depth-first search starting in a vertex
   *
   * @param g    Graph instance
   * @param vert vertex of graph g that will be the source of the search
   *
   * @return a LinkedList with the vertices of depth-first search
   */
  public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {
    boolean[] visited = new boolean[g.numVertices()];
    LinkedList<V> qdfs = new LinkedList<>();
    DepthFirstSearch(g, vert, visited, qdfs);
    return qdfs.size() == 0 ? null : qdfs;
  }

  /**
   * Checks if a graph is connected by performing a breadth-first search and
   * checking if all vertices were visited
   *
   * @param g    Graph instance
   * @param vert vertex of graph g that will be the source of the search
   * @return true if the graph is connected, false otherwise
   */
  public static <V, E> boolean isConnected(Graph<V, E> g) {
    LinkedList<V> bfs = BreadthFirstSearch(g, g.vertices().iterator().next());
    return isConnected(g, bfs);
  }

  /**
   * Checks if a graph is connected by performing a breadth-first search and
   * checking if all vertices were visited
   *
   * @param g    Graph instance
   * @param vert vertex of graph g that will be the source of the search
   * @return true if the graph is connected, false otherwise
   */
  private static <V, E> boolean isConnected(Graph<V, E> g, LinkedList<V> bfs) {
    return bfs != null && bfs.size() == g.numVertices();
  }

  /**
   * Find the shortest path between the two farthest nodes of a graph using DFS.
   *
   * @param g Graph instance
   * @return the shortest path between the farthest nodes of a graph.
   */
  public static <V, E> LinkedList<V> shortestPathBetweenFarthestNodes(Graph<V, E> g, Comparator<E> ce,
      BinaryOperator<E> sum, E zero) {
    LinkedList<V> max = new LinkedList<>();

    for (V v : g.vertices()) {
      LinkedList<V> dfs = DepthFirstSearch(g, v);
      if (!isConnected(g, dfs))
        return null;

      for (V w : dfs) {
        LinkedList<V> path = new LinkedList<>();
        shortestPath(g, v, w, ce, sum, zero, path);
        if (path.size() > max.size())
          max = path;
      }
    }

    return max;
  }

  /**
   * Returns all paths from vOrig to vDest
   *
   * @param g       Graph instance
   * @param vOrig   Vertex that will be the source of the path
   * @param vDest   Vertex that will be the end of the path
   * @param visited set of discovered vertices
   * @param path    stack with vertices of the current path (the path is in
   *                reverse order)
   * @param paths   ArrayList with all the paths (in correct order)
   */
  private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
      LinkedList<V> path, ArrayList<LinkedList<V>> paths) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Returns all paths from vOrig to vDest
   *
   * @param g     Graph instance
   * @param vOrig information of the Vertex origin
   * @param vDest information of the Vertex destination
   * @return paths ArrayList with all paths from vOrig to vDest
   */
  public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Computes shortest-path distance from a source vertex to all reachable
   * vertices of a graph g with non-negative edge weights
   * This implementation uses Dijkstra's algorithm
   *
   * @param g        Graph instance
   * @param vOrig    Vertex that will be the source of the path
   * @param visited  set of previously visited vertices
   * @param pathKeys minimum path vertices keys
   * @param dist     minimum distances
   */
  private static <V, E> void shortestPathDijkstra(Graph<V, E> g, V vOrig, Comparator<E> ce, BinaryOperator<E> sum,
      E zero, boolean[] visited, V[] pathKeys, E[] dist) {
    int vKey = g.key(vOrig);
    dist[vKey] = zero;
    pathKeys[vKey] = vOrig;

    while (vOrig != null) {
      vKey = g.key(vOrig);
      visited[vKey] = true;
      for (Edge<V, E> edge : g.outgoingEdges(vOrig)) {
        int vKeyAdj = g.key(edge.getVDest());
        if (!visited[vKeyAdj]) {
          E s = sum.apply(dist[vKey], edge.getWeight());
          if (dist[vKeyAdj] == null || ce.compare(dist[vKeyAdj], s) > 0) {
            dist[vKeyAdj] = s;
            pathKeys[vKeyAdj] = vOrig;
          }
        }
      }

      E minDist = null;
      vOrig = null;
      for (V vert : g.vertices()) {
        int i = g.key(vert);
        if (!visited[i] && dist[i] != null && (minDist == null || ce.compare(dist[i], minDist) < 0)) {
          minDist = dist[i];
          vOrig = vert;
        }
      }
    }
  }

  /**
   * Shortest-path between two vertices
   *
   * @param g         graph
   * @param vOrig     origin vertex
   * @param vDest     destination vertex
   * @param ce        comparator between elements of type E
   * @param sum       sum two elements of type E
   * @param zero      neutral element of the sum in elements of type E
   * @param shortPath returns the vertices which make the shortest path
   * @return if vertices exist in the graph and are connected, true, false
   *         otherwise
   */
  public static <V, E> E shortestPath(Graph<V, E> g, V vOrig, V vDest, Comparator<E> ce, BinaryOperator<E> sum, E zero,
      LinkedList<V> shortPath) {
    if (!g.validVertex(vOrig) || !g.validVertex(vDest))
      return null;

    shortPath.clear();
    int numVerts = g.numVertices();

    boolean[] visited = new boolean[numVerts];

    @SuppressWarnings("unchecked")
    V[] pathKeys = (V[]) new Object[numVerts];

    @SuppressWarnings("unchecked")
    E[] dist = (E[]) new Object[numVerts];

    for (int i = 0; i < numVerts; i++) {
      dist[i] = null;
      pathKeys[i] = null;
    }

    shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

    E lengthPath = dist[g.key(vDest)];

    if (lengthPath == null)
      return null;

    getPath(g, vOrig, vDest, pathKeys, shortPath);
    return lengthPath;
  }

  /**
   * Shortest-path between a vertex and all other vertices
   *
   * @param g     graph
   * @param vOrig start vertex
   * @param ce    comparator between elements of type E
   * @param sum   sum two elements of type E
   * @param zero  neutral element of the sum in elements of type E
   * @param paths returns all the minimum paths
   * @param dists returns the corresponding minimum distances
   * @return if vOrig exists in the graph true, false otherwise
   */
  public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig,
      Comparator<E> ce, BinaryOperator<E> sum, E zero,
      ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {
    int numVerts = g.numVertices();

    boolean[] visited = new boolean[numVerts];

    @SuppressWarnings("unchecked")
    V[] pathKeys = (V[]) new Object[numVerts];

    @SuppressWarnings("unchecked")
    E[] dist = (E[]) new Object[numVerts];

    for (int i = 0; i < numVerts; i++) {
      dist[i] = null;
      pathKeys[i] = null;
    }

    shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

    dists.clear();
    paths.clear();

    for (int i = 0; i < numVerts; i++) {
      dists.add(null);
      paths.add(null);
    }

    for (V vDest : g.vertices()) {
      int v = g.key(vDest);

      if (dist[v] != null) {
        LinkedList<V> shortPath = new LinkedList<>();
        getPath(g, vOrig, vDest, pathKeys, shortPath);
        paths.set(v, shortPath);
        dists.set(v, dist[v]);
      }
    }

    return true;
  }

  /**
   * Extracts from pathKeys the minimum path between voInf and vdInf
   * The path is constructed from the end to the beginning
   *
   * @param g        Graph instance
   * @param vOrig    information of the Vertex origin
   * @param vDest    information of the Vertex destination
   * @param pathKeys minimum path vertices keys
   * @param path     stack with the minimum path (correct order)
   */
  private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest, V[] pathKeys, LinkedList<V> path) {
    if (vOrig.equals(vDest))
      path.push(vOrig);

    else {
      path.push(vDest);
      int vKey = g.key(vDest);
      vDest = pathKeys[vKey];
      getPath(g, vOrig, vDest, pathKeys, path);
    }
  }

  /**
   * Calculates the minimum distance graph using Floyd-Warshall
   *
   * @param g   initial graph
   * @param ce  comparator between elements of type E
   * @param sum sum two elements of type E
   * @return the minimum distance graph
   */
  public static <V, E> AdjacencyMapGraph<V, E> minDistGraph(Graph<V, E> g, Comparator<E> ce, BinaryOperator<E> sum) {
    AdjacencyMapGraph<V, E> minDstGraph = new AdjacencyMapGraph<>(g.isDirected());

    for (Edge<V, E> e : g.edges()) {
      for (Edge<V, E> adj : g.incomingEdges(e.getVOrig())) {
        E newWeight = sum.apply(e.getWeight(), adj.getWeight());
        if (ce.compare(newWeight, e.getWeight()) < 0)
          minDstGraph.addEdge(e.getVOrig(), e.getVDest(), newWeight);
      }
    }

    return minDstGraph;
  }

  /**
   * Calculates the minimum distance graph using Kruskal algorithm
   *
   * @param <V> vertex type
   * @param <E> edge type
   * @param g   initial graph
   * @param ce  comparator between elements of type E
   * @return the minimum distance graph
   */
  public static <V, E> AdjacencyMapGraph<V, E> kruskall(Graph<V, E> g, Comparator<Edge<V, E>> ce) {
    AdjacencyMapGraph<V, E> mst = new AdjacencyMapGraph<>(false);

    List<Edge<V, E>> edges = new ArrayList<>();

    for (V v : g.vertices())
      mst.addVertex(v);

    for (Edge<V, E> e : g.edges())
      edges.add(e);

    edges = new MergeSort<Edge<V, E>>().sort(edges, ce);

    for (Edge<V, E> e : edges) {
      V vOrig = e.getVOrig();
      V vDest = e.getVDest();
      List<V> connectedVerts = DepthFirstSearch(mst, vOrig);
      if (!connectedVerts.contains(vDest))
        mst.addEdge(vOrig, vDest, e.getWeight());
    }

    return mst;
  }

  /**
   * Calculates the minimum number of edges between all pairs of vertices in a graph using Floyd-Warshall's algorithm.
   *
   * @param <V>   vertex type
   * @param <E>   edge type
   * @param g     initial graph
   * @param ce    comparator between elements of type E
   * @param sum   sum two elements of type E
   * @param zero  neutral element of the sum in elements of type E
   * @return      the minimum number of edges between all pairs of vertices in a graph
   */
  public static <V, E> int[][] minEdges(Graph<V, E> g, Comparator<E> ce, BinaryOperator<E> sum, E zero) {
    int numVerts = g.numVertices();
    int[][] minEdges = new int[numVerts][numVerts];

    for (int i = 0; i < numVerts; i++)
      for (int j = 0; j < numVerts; j++)
        minEdges[i][j] = -1;

    for (int i = 0; i < numVerts; i++)
      minEdges[i][i] = 0;

    for (Edge<V, E> e : g.edges()) {
      int vOrig = g.key(e.getVOrig());
      int vDest = g.key(e.getVDest());
      minEdges[vOrig][vDest] = 1;
    }

    for (int k = 0; k < numVerts; k++)
      for (int i = 0; i < numVerts; i++)
        for (int j = 0; j < numVerts; j++)
          if (minEdges[i][k] != -1 && minEdges[k][j] != -1)
            if (minEdges[i][j] == -1 || minEdges[i][j] > minEdges[i][k] + minEdges[k][j])
              minEdges[i][j] = minEdges[i][k] + minEdges[k][j];

    return minEdges;
  }
}
