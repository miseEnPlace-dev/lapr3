package isep.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Edge;
import isep.utils.graph.Graph;
import isep.utils.graph.GraphAlgorithms;

public class DistributionNetwork {
  private Graph<Entity, Integer> network = new AdjacencyMapGraph<>(false);

  public boolean addRelation(Entity e1, Entity e2, Integer distance) {
    return network.addEdge(e1, e2, distance);
  }

  public Integer getDistanceBetween(Entity e1, Entity e2) {
    if (network.edge(e1, e2) != null)
      return network.edge(e1, e2).getWeight();
    return null;
  }

  private Graph<Entity, Integer> getNetwork() {
    return network;
  }

  public int getNumberOfEntities() {
    return network.numVertices();
  }

  public AdjacencyMapGraph<Entity, Integer> getMinimumShortestPathNetwork(Comparator<Integer> ce) {
    return kruskall(getNetwork(), ce);
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
  private AdjacencyMapGraph<Entity, Integer> kruskall(Graph<Entity, Integer> g, Comparator<Integer> ce) {
    AdjacencyMapGraph<Entity, Integer> mst = new AdjacencyMapGraph<>(false);

    List<Edge<Entity, Integer>> edges = new ArrayList<>();

    for (Entity v : g.vertices())
      mst.addVertex(v);

    for (Edge<Entity, Integer> e : g.edges())
      edges.add(e);

    edges.sort(new Comparator<Edge<Entity, Integer>>() {
      @Override
      public int compare(Edge<Entity, Integer> e1, Edge<Entity, Integer> e2) {
        return ce.compare(e1.getWeight(), e2.getWeight());
      }
    });

    for (Edge<Entity, Integer> e : edges) {
      Entity vOrig = e.getVOrig();
      Entity vDest = e.getVDest();
      List<Entity> connectedVerts = GraphAlgorithms.DepthFirstSearch(mst, vOrig);
      if (!connectedVerts.contains(vDest))
        mst.addEdge(vOrig, vDest, e.getWeight());
    }

    return mst;

  }
}
