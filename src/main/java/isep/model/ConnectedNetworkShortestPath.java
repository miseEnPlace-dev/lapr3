package isep.model;

import isep.utils.graph.Graph;

/*
 * Class that gets the shortest path between all entities in a network
 *
 * @author André Barros <1211299@isep.ipp.pt>
 */
public class ConnectedNetworkShortestPath {
  public Graph<Entity, Integer> getConnectedNetworkShortestPath(DistributionNetwork network) {

    /*
     * if (networkConnected(network))
     * return network.getMinimumShortestPathNetwork(Integer::compare);
     * else
     * throw new IllegalArgumentException("Network is not connected");
     *
     */
    return network.getMinimumShortestPathNetwork(Integer::compare);
  }

  /*
   * Check if the network is connected (waiting for implementation)
   */
  // TODO: implement this method

}
