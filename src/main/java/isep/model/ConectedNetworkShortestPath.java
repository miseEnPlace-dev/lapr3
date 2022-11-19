package isep.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.BinaryOperator;

import isep.utils.graph.Graph;

import isep.utils.graph.GraphAlgorithms;

/*
 * Class that gets the shortest path between all entities in a network
 * @author André Barros <1211299@isep.ipp.pt>
 */
public class ConectedNetworkShortestPath {

  public ConectedNetworkShortestPath() {
  }

  private static Graph<Entity, Integer> graph;

  public Graph<Entity, Integer> execute(Entity source, DistributionNetwork network) {
    if (source == null || network == null) {
      System.out.println("Source or network is null");
      return null;
    }

    return getConnectedNetworkShortestPath(source, network);
  }

  public Graph<Entity, Integer> getConnectedNetworkShortestPath(Entity source, DistributionNetwork network) {

    graph = network.getNetwork();

    ArrayList<LinkedList<Entity>> paths = new ArrayList<>();
    ArrayList<Integer> dist = new ArrayList<>();

    // get shortest path from source to all other entities in the network
    // using shortest path algorithm from GraphAlgorithms

    Graph<Entity, Integer> shortestPathGraph = graph.clone();

    GraphAlgorithms.shortestPaths(shortestPathGraph, source, comparator, binaryOperator, 0, paths, dist);

    // TODO fix repetition of entities in paths

    return shortestPathGraph;

  }

  // comparator for shortest path algorithm
  private static Comparator<Integer> comparator = new Comparator<Integer>() {
    @Override
    public int compare(Integer o1, Integer o2) {
      return o1.compareTo(o2);
    }
  };

  // binary operator for shortest path algorithm
  private static BinaryOperator<Integer> binaryOperator = new BinaryOperator<Integer>() {
    @Override
    public Integer apply(Integer t, Integer u) {
      if (t == null)
        return u;
      if (u == null)
        return t;
      return t + u;
    }
  };

}
