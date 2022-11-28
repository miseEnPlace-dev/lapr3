package isep.model;

import isep.shared.exceptions.NetworkNotConnectedException;
import isep.utils.graph.Graph;
import isep.utils.graph.GraphAlgorithms;

/*
 * Class that gets the shortest path between all entities in a network
 *
 * @author André Barros <1211299@isep.ipp.pt>
 */
public class ConnectedNetworkShortestPath {

  /**
   * Gets the shortest path between all entities in the network
   *
   * @param network network to get the shortest path
   * @return graph of the shortest path from the distribution network
   * @throws NetworkNotConnectedException if the network is not connected
   */
  public Graph<Entity, Integer> getConnectedNetworkShortestPath(DistributionNetwork network)
      throws NetworkNotConnectedException {

    if (GraphAlgorithms.isConnected(network.getNetwork())) {
      return network.getMinimumShortestPathNetwork(Integer::compare);
    }

    throw new NetworkNotConnectedException("Network not connected to calculate shortest path");
  }
}
