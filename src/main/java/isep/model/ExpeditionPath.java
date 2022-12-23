package isep.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Expedition Path class. Holds information about US310:
 * - expedition path (entities list)
 * - baskets delivered at a given hub
 * - distance between all entities of path
 * - total distance of the path
 * 
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
public class ExpeditionPath {
  private DistributionNetwork distributionNetwork;
  private ExpeditionList expeditionList;
  private List<Entity> path;
  private Map<Enterprise, Integer> deliveredBaskets;
  private int totalDistance;

  /**
   * Constructor fot ExpeditionPath.
   * 
   * @param distributionNetwork The distribution network
   * @param path                The expedition path
   */
  public ExpeditionPath(DistributionNetwork distributionNetwork, ExpeditionList expeditionList) {
    this.distributionNetwork = distributionNetwork;
    this.expeditionList = expeditionList;
    buildPath();
  }

  /**
   * Builds the shortest path.
   */
  private void buildPath() {
    path = new ArrayList<>();

    // Get all producers in the expedition list
    List<Producer> producers = expeditionList.getProducers();

    // Pop (get and remove) first producer from the list
    Entity currentEntity = producers.get(0);
    producers.remove(currentEntity);

    // Find the shortest path between all producers
    while (!producers.isEmpty()) {
      int minDistance = Integer.MAX_VALUE;
      Producer minProducer = null;
      List<Entity> minPath = null;

      // Iterate over all other producers, in order to find the nearest one
      for (int i = 0; i < producers.size(); i++) {
        List<Entity> shortestPath = distributionNetwork.getShortestPathUsingAStar(currentEntity, producers.get(i));
        int distance = distributionNetwork.getWeightOfPath(shortestPath);

        if (distance < minDistance) {
          minDistance = distance;
          minProducer = producers.get(i);
          minPath = shortestPath;
          minPath.remove(minPath.size() - 1);
        }
      }

      currentEntity = minProducer;
      path.addAll(minPath);
      producers.remove(currentEntity);
    }

    // Get all hubs in the expedition list
    List<Enterprise> hubs = expeditionList.getHubs();

    // Find the shortest path between all hubs
    while (!hubs.isEmpty()) {
      int minDistance = Integer.MAX_VALUE;
      Enterprise minHub = null;
      List<Entity> minPath = null;

      // Iterate over all other hubs, in order to find the nearest one
      for (int i = 0; i < hubs.size(); i++) {
        List<Entity> shortestPath = distributionNetwork.getShortestPathUsingAStar(currentEntity, hubs.get(i));
        int distance = distributionNetwork.getWeightOfPath(shortestPath);

        if (distance < minDistance) {
          minDistance = distance;
          minHub = hubs.get(i);
          minPath = shortestPath;
          minPath.remove(minPath.size() - 1);
        }
      }

      currentEntity = minHub;
      path.addAll(minPath);
      hubs.remove(currentEntity);
    }

    path.add(currentEntity);

    setTotalDistance();
  }

  /**
   * Sets the totalDistance attribute.
   */
  private void setTotalDistance() {
    this.totalDistance = this.distributionNetwork.getWeightOfPath(path);
  }

  /**
   * Get the total distance of the expedition path.
   * 
   * @return The path's total distance
   */
  public int getTotalDistance() {
    return this.totalDistance;
  }

  /**
   * Get the number of baskets delivered at the given hub.
   * 
   * @param hub The hub to search for
   * @return Number of baskets delivered at the given hub, or {@code -1} if hub
   *         does not exist in the path
   */
  public int getNumberOfBasketsDeliveredAtHub(Enterprise hub) {
    if (deliveredBaskets.containsKey(hub))
      return this.deliveredBaskets.get(hub);
    return -1;
  }

  /**
   * Get the distance between two entities in the path
   * 
   * @param entity1 First entity
   * @param entity2 Second entity
   * @return The distance between entity1 and entity2, or {@code -1} if any of
   *         both entities does not exist in path
   */
  public int getDistanceBetweenEntities(Entity entity1, Entity entity2) {
    if (path.contains(entity1) && path.contains(entity2))
      return this.distributionNetwork.getDistanceBetweenConnectedEntities(entity1, entity2);
    return -1;
  }

  /**
   * Get the distance between all entities of the path.
   * 
   * @return A {@code Map} that associates a {@code Entry} of two Entities to
   *         it's distance
   */
  public Map<Map.Entry<Entity, Entity>, Integer> getDistanceBetweenAllEntities() {
    Map<Map.Entry<Entity, Entity>, Integer> map = new HashMap<>();

    for (int i = 0; i < path.size() - 1; i++) {
      int distance = this.distributionNetwork.getDistanceBetweenConnectedEntities(path.get(i), path.get(i + 1));
      map.put(Map.entry(path.get(i), path.get(i + 1)), distance);
    }

    return map;
  }

  /**
   * Prints the expedition path.
   */
  public void printPath() {
    System.out.println("Expedition path for day " + this.expeditionList.getDay() + ":");
    for (int i = 0; i < path.size(); i++) {
      String str = ">> Stop " + (i + 1) + ": ";
      if (path.get(i).getClass() == Producer.class)
        str = str.concat("Producer " + path.get(i).getId());
      else
        str = str.concat("Hub " + path.get(i).getId());
      System.out.println(str);
    }
  }
}
