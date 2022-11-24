package isep.model;

import isep.utils.graph.Graph;

/*
 * Class that gets the shortest path between all entities in a network
 * @author André Barros <1211299@isep.ipp.pt>
 */
public class ConnectedNetworkShortestPath {
  public Graph<Entity, Integer> getConnectedNetworkShortestPath(DistributionNetwork network) {

    return network.getMinimumShortestPathNetwork(Integer::compare);
  }
}
