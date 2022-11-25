package isep.model;

import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Graph;

public class DistributionNetwork {
  private Graph<Entity, Integer> network = new AdjacencyMapGraph<>(false);

  public boolean addRelation(Entity e1, Entity e2, Integer distance) {
    return network.addEdge(e1, e2, distance);
  }


  
  /** 
   * @param e1 entity 1
   * @param e2 entity 2
   * @return Integer - If e1 and e2 are directed connected,
   *  returns distance between. if they're not, returns null
   */
  public Integer getDistanceBetweenDirectedConnected(Entity e1, Entity e2) {
    if (network.edge(e1, e2) != null)
      return network.edge(e1, e2).getWeight();
    return null;
  }

  public int getNumberOfEntities() {
    return network.numVertices();
  }
}
