package isep.model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import isep.shared.exceptions.InvalidNumberOfHubsException;
import isep.utils.MergeSort;
import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Edge;
import isep.utils.graph.GraphAlgorithms;

public class DistributionNetwork {
  private NetworkGraph<Entity, Integer> network = new NetworkGraph<>(false);

  /**
   *
   * @param e1       First entity
   * @param e2       Second entity
   * @param distance Distance between them (in meters)
   * @return True if the relation was added successfully, false otherwise
   */
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

  /**
   *
   * @return The number of entities that have a minimum of one relation
   *         represented in the network
   */
  public int getNumberOfEntities() {
    return network.numVertices();
  }

  public int getNumberOfRelations() {
    return network.numEdges() / 2;
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

    return GraphAlgorithms.kruskall(network, ce);
  }

  public List<Enterprise> getEnterprises() {
    return network.getEntitiesWithClass(Enterprise.class);
  }

  public List<Entity> getEntities() {
    return network.vertices();
  }

  public List<Client> getClients() {
    return network.getEntitiesWithClass(Client.class);
  }

  public List<Enterprise> getHubs() {
    return network.getHubs();
  }

  public int shortestPathDistance(Entity e1, Entity e2) {
    return GraphAlgorithms.shortestPath(network, e1, e2, Integer::compareTo, Integer::sum, 0, new LinkedList<>());
  }

  public ArrayList<Integer> shortestPathsDistances(Entity e1) {
    ArrayList<Integer> distances = new ArrayList<>();
    GraphAlgorithms.shortestPaths(network, e1, Integer::compareTo, Integer::sum, 0, new ArrayList<>(), distances);
    return distances;
  }

  public boolean isConnected() {
    return GraphAlgorithms.isConnected(network);
  }

  public int[][] shortestPathsBetweenAllNodes() {
    return GraphAlgorithms.minEdges(network, Integer::compare, Integer::sum, 0);
  }

  public List<Enterprise> defineHubs(int numberOfHubs) throws InvalidNumberOfHubsException {
    if (numberOfHubs <= 0)
      throw new InvalidNumberOfHubsException();

    if (!this.isConnected())
      return null;

    List<Map.Entry<Enterprise, Integer>> list = new ArrayList<>();
    List<Enterprise> enterprises = this.getEnterprises();

    for (int i = 0; i < enterprises.size(); i++) {
      Enterprise e1 = enterprises.get(i);

      // if e1 was a Hub before unMakes it
      e1.unMakeHub();

      int average = this.getAveragePathDistanceBetweenGroupOfEntities(e1);

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

  public int getAveragePathDistanceBetweenGroupOfEntities(Entity e1) {

    ArrayList<Integer> distances = new ArrayList<>();
    GraphAlgorithms.shortestPaths(network, e1, Integer::compareTo, Integer::sum, 0, new ArrayList<>(), distances);

    int sum = 0;
    int count = distances.size();
    for (int i = 0; i < count; i++) {
      sum += distances.get(i);
    }

    return sum / count;
  }

  public Enterprise getNearestHub(Entity entity) {
    // If entity is a hub, return it
    if (entity instanceof Enterprise && ((Enterprise) entity).isHub())
      return (Enterprise) entity;

    List<Enterprise> hubs = this.getHubs();

    // If there are no hubs, return null
    if (hubs.size() == 0)
      return null;

    // If there is only one hub, return it
    if (hubs.size() == 1)
      return hubs.get(0);

    ArrayList<Integer> distancesToOtherVertices = this.shortestPathsDistances(entity);

    Enterprise nearestHub = hubs.get(0);
    int minDistance = distancesToOtherVertices.get(network.key(hubs.get(0)));

    // If there is more than one hub, find the nearest one
    for (int i = 1; i < hubs.size(); i++) {
      Enterprise hub = hubs.get(i);
      int distance = distancesToOtherVertices.get(network.key(hub));

      if (distance < minDistance) {
        minDistance = distance;
        nearestHub = hub;
      }
    }

    return nearestHub;
  }

  public List<Producer> getNNearestProducers(Enterprise hub, int n) {
    List<Producer> producers = network.getEntitiesWithClass(Producer.class);

    if (producers.size() < n)
      return null;

    List<Producer> result = new ArrayList<>();

    ArrayList<Integer> distancesToOtherVertices = this.shortestPathsDistances(hub);

    for (int i = 0; i < n; i++) {
      Producer nearestProducer = producers.get(0);
      int minDistance = distancesToOtherVertices.get(network.key(producers.get(0)));

      for (int j = 1; j < producers.size(); j++) {
        Producer producer = producers.get(j);
        int distance = distancesToOtherVertices.get(network.key(producer));

        if (distance < minDistance) {
          minDistance = distance;
          nearestProducer = producer;
        }
      }

      result.add(nearestProducer);
      producers.remove(nearestProducer);
    }

    return result;
  }
}
