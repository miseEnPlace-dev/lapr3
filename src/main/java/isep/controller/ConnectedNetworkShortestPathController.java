package isep.controller;

import isep.model.DistributionNetwork;
import isep.utils.graph.Graph;
import isep.model.Entity;
import isep.shared.exceptions.NetworkNotConnectedException;

/*
 * Class that gets the shortest path between all entities in a network
 *
 * @author André Barros <1211299@isep.ipp.pt>
 */
public class ConnectedNetworkShortestPathController {
  private DistributionNetwork network;

  /*
   * Constructor
   */
  public ConnectedNetworkShortestPathController(DistributionNetwork network) {
    if (network == null)
      throw new IllegalArgumentException("Network is null");
    this.network = network;
  }

  /*
   * Get shortest path between all entities in a network
   */
  public Graph<Entity, Integer> getConnectedNetworkShortestPath() throws NetworkNotConnectedException {
    return network.getMinimumShortestPathNetwork();
  }

}
