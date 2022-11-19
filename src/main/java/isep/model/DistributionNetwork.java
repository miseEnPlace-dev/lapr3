package isep.model;

import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Graph;

public class DistributionNetwork {
  private Graph<Entity, Integer> network = new AdjacencyMapGraph<>(false);

  public boolean addRelation(Entity e1, Entity e2, Integer distance) {
    return network.addEdge(e1, e2, distance);
  }

  public Integer getDistanceBetween(Entity e1, Entity e2) {
    if (network.edge(e1, e2) != null)
      return network.edge(e1, e2).getWeight();
    return null;
  }

  public Graph<Entity, Integer> getNetwork() {
    return network;
  }
}
