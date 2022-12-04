package isep.controller;

import java.util.LinkedList;
import isep.model.DistributionNetwork;
import isep.model.Entity;

/**
 * US 302: Get the minimum number of connections between any two nodes.
 *
 * @author Ricardo Moreira <1211285@isep.ipp.pt>
 */
public class GetMinNumberOfConnectionsController {
  private DistributionNetwork distributionNetwork;

  /**
   * Constructor for FindNearestHubController.
   *
   * @param distributionNetwork The distribution network
   */
  public GetMinNumberOfConnectionsController(DistributionNetwork distributionNetwork) {
    this.distributionNetwork = distributionNetwork;
  }

  public int getMinimumNumOfConnections() {
    if (distributionNetwork == null)
      throw new IllegalArgumentException("Invalid distribution network");

    if (!distributionNetwork.isConnected())
      throw new IllegalArgumentException("Distribution network is not connected");

    LinkedList<Entity> shortestPath = distributionNetwork.shortestPathBetweenFarthestNodes();
    return shortestPath.size() - 1;
  }

  public boolean isConnected() {
    return distributionNetwork.isConnected();
  }
}
