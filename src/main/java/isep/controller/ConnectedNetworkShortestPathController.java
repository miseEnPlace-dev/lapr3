package isep.controller;

import isep.model.DistributionNetwork;
import isep.utils.graph.Graph;
import isep.model.Entity;

import isep.model.ConnectedNetworkShortestPath;

/*
 * Class that gets the shortest path between all entities in a network
 *
 * @author André Barros <1211299@isep.ipp.pt>
 */
public class ConnectedNetworkShortestPathController {
  private DistributionNetwork network;
  private Entity source;

  /*
   * Constructor
   */
  public ConnectedNetworkShortestPathController(DistributionNetwork network, Entity source) {
    this.network = network;
    this.source = source;
  }

  /*
   * Get shortest path between all entities in a network
   */
  public Graph<Entity, Integer> getConnectedNetworkShortestPath() {
    ConnectedNetworkShortestPath conectedNetworkShortestPath = new ConnectedNetworkShortestPath();

    return conectedNetworkShortestPath.getConnectedNetworkShortestPath(this.source, this.network);
  }

}
