package isep.model;

import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Graph;

public class DistributionNetwork {
  private Graph<Entity, Integer> network = new AdjacencyMapGraph<>(false);

  /**
   *
   * @param e1       First entity
   * @param e2       Second entity
   * @param distance Distance between them (in meters)
   * @return True if the relation was added successfully, false otherwise
   */
  public boolean addRelation(Entity e1, Entity e2, Integer distance) {
    return network.addEdge(e1, e2, distance);
  }

  /**
   *
   * @param e1 The first entity
   * @param e2 The second entity
   * @return The distance between them (in meters)
   */
  public Integer getDistanceBetween(Entity e1, Entity e2) {
    if (network.edge(e1, e2) != null)
      return network.edge(e1, e2).getWeight();
    return null;
  }

  /**
   *
   * @return The number of entities that have a minimum of one relation
   *         represented in the network
   */
  public int getNumberOfEntities() {
    return network.numVertices();
  }

  /**
   *
   * @return The number of relations between entities
   */
  public int getNumberOfRelations() {
    // divide by two because is not directed, every vertex has 2 relations
    return network.numEdges() / 2;
  }
}
