package isep.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Graph;
import isep.utils.graph.GraphAlgorithms;

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

  public List<Enterprise> getEnterprises(){
    List<Enterprise> enterprises = new ArrayList<>();
    List<Entity> entities = network.vertices();
    for (int i = 0; i < entities.size(); i++) {
      Entity e = entities.get(i);
      if(entities.get(i).getClass() == Enterprise.class)
        enterprises.add((Enterprise) e);
    }
    return enterprises;
  }

  public List<Entity> getNonEnterprises(){
    List<Entity> nonEnterprises = new ArrayList<>();
    List<Entity> entities = network.vertices();
    for (int i = 0; i < entities.size(); i++) {
      Entity e = entities.get(i);
      if(entities.get(i).getClass() != Enterprise.class)
        nonEnterprises.add(e);
    }
    return nonEnterprises;
  }

  public int shortestPathDistance(Entity e1, Entity e2){
    ArrayList<Integer> shortPath = new ArrayList<>();
    GraphAlgorithms.shortestPath(network, e1, e2, Integer :: compareTo, Integer :: sum, null, new LinkedList<>());
    int dist = 0;
    for (int i = 0; i < shortPath.size(); i++) {
      dist += shortPath.get(i);
    }
    return dist;
  }

}
