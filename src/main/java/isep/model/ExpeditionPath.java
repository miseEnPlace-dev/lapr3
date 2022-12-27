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
  /**
   * Stop inner class. Holds information about a stop in the path.
   */
  class Stop {
    public Entity entity;
    public Integer basketsDelivered;

    public Stop(Enterprise hub, int basketsDelivered) {
      this.entity = hub;
      this.basketsDelivered = basketsDelivered;
    }

    public Stop(Producer producer) {
      this.entity = producer;
      this.basketsDelivered = null;
    }
  }

  private DistributionNetwork distributionNetwork;
  private ExpeditionList expeditionList;
  private List<Stop> path;
  private Map<Enterprise, Integer> deliveredBaskets;
  private Map<Enterprise, List<Producer>> hubsSuppliers;
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
    this.path = new ArrayList<>();
    setDeliveredBaskets();
    setHubsSuppliers();
    buildPath();
  }

  /**
   * Builds the shortest path.
   */
  private void buildPath() {
    List<Entity> entitiesPath = new ArrayList<>();

    // Get all producers in the expedition list
    List<Producer> producers = expeditionList.getProducers();

    // If there are no producers, return an empty path
    if (producers.isEmpty())
      return;

    // Get the first producer in the list, and remove it
    Entity start = producers.get(0);
    producers.remove(0);

    // Get the shortest path from the first producer to all other producers
    entitiesPath.addAll(distributionNetwork.getShortestPathUsingAStar(start, producers));

    // Get all hubs that can be supplied by the producers in the expedition list, as
    // well as generate the stops for the producers
    List<Enterprise> hubsSupplied = generateStopsForProducers(entitiesPath);

    // Get all hubs in the expedition list
    List<Enterprise> hubs = expeditionList.getHubs();
    // Remove the hubs that were already supplied
    hubs.removeAll(hubsSupplied);

    // Get the last stop in the path already calculated, and set it as the start
    start = entitiesPath.get(entitiesPath.size() - 1);
    entitiesPath.clear();

    // Get the shortest path from the last stop to all hubs in the expedition list
    entitiesPath.addAll(distributionNetwork.getShortestPathUsingAStar(start, hubs));
    // Remove repeated entity
    entitiesPath.remove(0);

    // Generate the stops for the hubs
    generateStopsForHubs(entitiesPath, hubsSupplied);

    // Set the total distance of the final path
    setTotalDistance();
  }

  /**
   * Generates the {@code List} of Stops for Producers. Checks for hubs that can
   * already be supplied, and returns them.
   * 
   * @param entities The path
   * @return A {@code List} of Hubs that were already supplied.
   */
  private List<Enterprise> generateStopsForProducers(List<Entity> entities) {
    List<Entity> visitedEntities = new ArrayList<>();
    List<Enterprise> suppliedHubs = new ArrayList<>();

    visitedEntities.add(entities.get(0));
    // First stop is guaranteed to be a Producer
    path.add(new Stop((Producer) entities.get(0)));

    int size = entities.size();
    for (int i = 1; i < size; i++) {
      Entity stop = entities.get(i);
      if (stop.getClass() == Enterprise.class) {
        // If the stop is a Hub, check if it can be supplied
        List<Producer> suppliers = hubsSuppliers.get(stop);
        if (suppliers != null && visitedEntities.containsAll(suppliers)) {
          // If the hub can be supplied, add it to the path
          path.add(new Stop((Enterprise) stop, getNumberOfBasketsDeliveredAtHub((Enterprise) stop)));
          suppliedHubs.add((Enterprise) stop);
        } else {
          // If the hub can't be supplied, add it to the path with 0 baskets
          path.add(new Stop((Enterprise) stop, 0));
        }
      } else {
        // If the stop is a Producer, add it to the path
        path.add(new Stop((Producer) stop));
      }
    }

    return suppliedHubs;
  }

  /**
   * Generates the {@code List} of Stops for Hubs. It checks for already supplied
   * hubs, and puts their delivered baskets to 0.
   * 
   * @param entities     The path
   * @param suppliedHubs A {@code List} of the already supplied hubs
   */
  private void generateStopsForHubs(List<Entity> entities, List<Enterprise> suppliedHubs) {
    for (Entity stop : entities) {
      if (stop.getClass() == Enterprise.class) {
        if (!suppliedHubs.contains((Enterprise) stop) && getNumberOfBasketsDeliveredAtHub((Enterprise) stop) != -1) {
          // If the hub is not already supplied, and it has baskets delivered, add it to
          // the path
          path.add(new Stop((Enterprise) stop, getNumberOfBasketsDeliveredAtHub((Enterprise) stop)));
          suppliedHubs.add((Enterprise) stop);
        } else {
          // If the hub is already supplied, or it has no baskets delivered, add it to the
          // path with 0 baskets
          path.add(new Stop((Enterprise) stop, 0));
        }
      } else {
        // If the stop is a Producer, add it to the path
        path.add(new Stop((Producer) stop));
      }
    }
  }

  /**
   * Sets the delivered baskets.
   */
  private void setDeliveredBaskets() {
    this.deliveredBaskets = this.expeditionList.getBasketsDeliveredAtHubs();
  }

  /**
   * Sets the hubs suppliers
   */
  private void setHubsSuppliers() {
    this.hubsSuppliers = this.expeditionList.getProducersThatSupplyHubs();
  }

  private List<Entity> convertStopListToEntityList() {
    List<Entity> list = new ArrayList<>();
    for (Stop stop : path) {
      list.add(stop.entity);
    }
    return list;
  }

  /**
   * Sets the totalDistance attribute.
   */
  private void setTotalDistance() {
    this.totalDistance = this.distributionNetwork.getWeightOfPath(convertStopListToEntityList());
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
   * @return The distance between entity1 and entity2
   */
  private int getDistanceBetweenEntities(Entity entity1, Entity entity2) {
    return this.distributionNetwork.getDistanceBetweenConnectedEntities(entity1, entity2);
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
      int distance = this.distributionNetwork.getDistanceBetweenConnectedEntities(path.get(i).entity,
          path.get(i + 1).entity);
      map.put(Map.entry(path.get(i).entity, path.get(i + 1).entity), distance);
    }

    return map;
  }

  /**
   * Prints the expedition path.
   */
  public void printPath() {
    System.out.println("Expedition path for day " + this.expeditionList.getDay() + ":\n----------");
    for (int i = 0; i < path.size(); i++) {
      String str = ">> Stop " + (i + 1) + " - ";
      if (path.get(i).entity.getClass() == Producer.class)
        // If the entity is a Producer, print it's ID
        str = str.concat("Producer " + path.get(i).entity.getId());
      else
        // If the entity is a Hub, print it's ID and the number of baskets delivered
        str = str
            .concat("Hub " + path.get(i).entity.getId() + " ("
                + (getNumberOfBasketsDeliveredAtHub((Enterprise) path.get(i).entity) == -1
                    ? "No baskets to deliver here"
                    : path.get(i).basketsDelivered
                        + " basket(s) delivered in this stop (Total: " + path.get(i).basketsDelivered + "/"
                        + getNumberOfBasketsDeliveredAtHub((Enterprise) path.get(i).entity) + ")")
                + ")");
      System.out.println(str);
      if (i != path.size() - 1) {
        // If it's not the last stop, print the distance to the next stop
        int distance = getDistanceBetweenEntities(path.get(i).entity, path.get(i + 1).entity);
        System.out.println("   : " + distance + "m");
      }
    }
    // Print the total distance
    System.out.println("----------\nTotal distance: " + getTotalDistance() + "m");
  }

  /**
   * Get the entities list of the path.
   * 
   * @return A {@code List} of all entities of the path.
   */
  public List<Entity> getPathList() {
    return convertStopListToEntityList();
  }
}
