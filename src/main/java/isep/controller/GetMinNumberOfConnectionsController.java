package isep.controller;

import isep.model.DistributionNetwork;

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

    int[][] shortestPaths = distributionNetwork.shortestPathsBetweenAllNodes();

    int max = shortestPaths[0][0];

    for (int i = 0; i < shortestPaths.length; i++) {
      for (int j = 0; j < shortestPaths[i].length; j++) {
        if (shortestPaths[i][j] > max)
          max = shortestPaths[i][j];
      }
    }

    return max;
  }

  public boolean isConnected() {
    return distributionNetwork.isConnected();
  }
}
