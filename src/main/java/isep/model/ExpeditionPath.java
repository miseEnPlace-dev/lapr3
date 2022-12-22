package isep.model;

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
  private List<Entity> path;
  private Map<Enterprise, Integer> deliveredBaskets;
  private int totalDistance;

  /**
   * Constructor fot ExpeditionPath.
   * 
   * @param distributionNetwork The distribution network
   * @param path                The expedition path
   */
  public ExpeditionPath(DistributionNetwork distributionNetwork, List<Entity> path) {
    this.distributionNetwork = distributionNetwork;
    this.path = path;
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
}
