package isep.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;

import isep.shared.exceptions.InvalidNumberOfHubsException;
import isep.utils.MergeSort;
import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Edge;
import isep.utils.graph.Graph;
import isep.utils.graph.GraphAlgorithms;

public class DistributionNetwork {
  private Graph<Entity, Integer> network = new AdjacencyMapGraph<>(false);

  public boolean addRelation(Entity e1, Entity e2, Integer distance) {
    return network.addEdge(e1, e2, distance);
  }

  /**
   * @param e1 entity 1
   * @param e2 entity 2
   * @return Integer - If e1 and e2 are directed connected, returns distance
   *         between, null otherwise
   */
  public Integer getDistanceBetweenConnectedEntities(Entity e1, Entity e2) {
    if (network.edge(e1, e2) != null)
      return network.edge(e1, e2).getWeight();
    return null;
  }

  public int getNumberOfEntities() {
    return network.numVertices();
  }

  /**
   * Gets the shortest path between all entities in the network
   *
   * @param ce comparator to sort the entities by the distance
   * @return graph of the shortest path from the distribution network
   */
  public AdjacencyMapGraph<Entity, Integer> getMinimumShortestPathNetwork() {
    final Comparator<Edge<Entity, Integer>> ce = new Comparator<Edge<Entity, Integer>>() {
      @Override
      public int compare(Edge<Entity, Integer> arg0, Edge<Entity, Integer> arg1) {
        return arg0.getWeight() - arg1.getWeight();
      }
    };

    return getConnectedNetworkShortestPath(network, ce);
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
  private AdjacencyMapGraph<Entity, Integer> getConnectedNetworkShortestPath(Graph<Entity, Integer> g,
      Comparator<Edge<Entity, Integer>> ce) {
    AdjacencyMapGraph<Entity, Integer> mst = new AdjacencyMapGraph<>(false);

    List<Edge<Entity, Integer>> edges = new ArrayList<>();

    for (Entity v : g.vertices())
      mst.addVertex(v);

    for (Edge<Entity, Integer> e : g.edges())
      edges.add(e);

    edges = new MergeSort<Edge<Entity, Integer>>().sort(edges, ce);

    for (Edge<Entity, Integer> e : edges) {
      Entity vOrig = e.getVOrig();
      Entity vDest = e.getVDest();
      List<Entity> connectedVerts = GraphAlgorithms.DepthFirstSearch(mst, vOrig);
      if (!connectedVerts.contains(vDest))
        mst.addEdge(vOrig, vDest, e.getWeight());
    }

    return mst;

  }

  public List<Enterprise> getEnterprises() {
    List<Enterprise> enterprises = new ArrayList<>();
    List<Entity> entities = network.vertices();

    for (int i = 0; i < entities.size(); i++) {
      Entity e = entities.get(i);
      if (entities.get(i).getClass() == Enterprise.class)
        enterprises.add((Enterprise) e);
    }

    return enterprises;
  }

  public List<Entity> getNonEnterprises() {
    List<Entity> nonEnterprises = new ArrayList<>();
    List<Entity> entities = network.vertices();

    for (int i = 0; i < entities.size(); i++) {
      Entity e = entities.get(i);
      if (entities.get(i).getClass() != Enterprise.class)
        nonEnterprises.add(e);
    }

    return nonEnterprises;
  }

  public int shortestPathDistance(Entity e1, Entity e2) {
    return GraphAlgorithms.shortestPath(network, e1, e2, Integer::compareTo, Integer::sum, 0, new LinkedList<>());
  }

  public boolean isConnected() {
    return GraphAlgorithms.isConnected(network);
  }

  public List<Enterprise> defineHubs(int numberOfHubs) throws InvalidNumberOfHubsException {
    if (numberOfHubs <= 0)
      throw new InvalidNumberOfHubsException();

    List<Map.Entry<Enterprise, Integer>> list = new ArrayList<>();

    if (!this.isConnected())
      return null;

    List<Enterprise> enterprises = this.getEnterprises();
    List<Entity> nonEnterprises = this.getNonEnterprises();

    for (int i = 0; i < enterprises.size(); i++) {
      Enterprise e1 = enterprises.get(i);

      // if e1 was a Hub before unMakes it
      e1.unMakeHub();

      int average = this.getAveragePathDistanceBetweenGroupOfEntities(e1, nonEnterprises);

      list.add(new AbstractMap.SimpleEntry<Enterprise, Integer>(e1, average));
    }

    final Comparator<Map.Entry<Enterprise, Integer>> cmp = new Comparator<Map.Entry<Enterprise, Integer>>() {
      @Override
      public int compare(Map.Entry<Enterprise, Integer> o1, Map.Entry<Enterprise, Integer> o2) {
        return (int) (o1.getValue() - o2.getValue());
      }
    };

    // order list
    list = new MergeSort<Map.Entry<Enterprise, Integer>>().sort(list, cmp);

    // make N enterprises Hubs and return them in a list
    List<Enterprise> result = new ArrayList<>();
    for (int i = 0; i < numberOfHubs; i++) {
      try {
        Enterprise hub = list.get(i).getKey();
        hub.makeHub();
        result.add(hub);
      } catch (Exception E) {
        System.out.printf("There are only %d number of Enterprises\n", i);
        break;
      }
    }
    return result;
  }

  public int getAveragePathDistanceBetweenGroupOfEntities(Entity e1, List<Entity> entities) {
    int count = 0;
    int sum = 0;

    // sums the shortest path size to from e1 to all entities
    // count paths between e1 and all entities
    for (int i = 0; i < entities.size(); i++) {
      sum += this.shortestPathDistance(e1, entities.get(i));
      count++;
    }

    return sum / count;
  }
}
