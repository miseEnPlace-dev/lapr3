package isep.model;

import java.util.Comparator;
import isep.utils.graph.Graph;

/*
 * Class that gets the shortest path between all entities in a network
 * @author André Barros <1211299@isep.ipp.pt>
 */
public class ConnectedNetworkShortestPath {
  public Graph<Entity, Integer> getConnectedNetworkShortestPath(DistributionNetwork network) {

    return network.getMinimumShortestPathNetwork(comparator);
  }

  /*
   * Comparator to compare two integers
   */
  private static Comparator<Integer> comparator = new Comparator<Integer>() {
    @Override
    public int compare(Integer o1, Integer o2) {
      return o1.compareTo(o2);
    }
  };

}
