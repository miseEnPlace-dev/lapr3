package isep.model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Exceptions.InvalidNumberOfHubsException;
import isep.utils.MergeSort;
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
   * @return Integer - If e1 and e2 are directed connected, returns distance between, null otherwise
   */
  public Integer getDistanceBetweenConnectedEntities(Entity e1, Entity e2) {
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
    return GraphAlgorithms.shortestPath(network, e1, e2, Integer :: compareTo, Integer :: sum, 0, new LinkedList<>());
  }

  public boolean isConnected(){
    return GraphAlgorithms.isConnected(network);
  }

  public List<Enterprise> defineHubs(int numberOfHubs) throws InvalidNumberOfHubsException{
    if(numberOfHubs <= 0) throw(new InvalidNumberOfHubsException());

    List<Map.Entry<Enterprise, Integer>> list = new ArrayList<>();
    
    if(!this.isConnected()) return null;

    List<Enterprise> enterprises = this.getEnterprises();
    List<Entity> nonEnterprises = this.getNonEnterprises();

    for (int i = 0; i < enterprises.size(); i++) {

        Enterprise e1 = enterprises.get(i);

        // if e1 was a Hub before unMakes it
        e1.unMakeHub();

        int average = this.getAveragePathDistanceBetweenGroupOfEntities(e1, nonEnterprises);

        list.add(new AbstractMap.SimpleEntry<Enterprise, Integer>(e1, average));
    }

    final Comparator<Map.Entry<Enterprise, Integer>> cmp = new Comparator<Map.Entry<Enterprise, Integer>>(){
      @Override
      public int compare(Map.Entry<Enterprise, Integer> o1, Map.Entry<Enterprise, Integer> o2) {
        return (int) (o1.getValue()-o2.getValue());
      }
    };

    // order list
    list = new MergeSort<Map.Entry<Enterprise, Integer>>().sort(list, cmp);

    // make N enterprises Hubs and return them in a list
    List<Enterprise> result = new ArrayList<>();
    for (int i = 0; i < numberOfHubs; i++) {
        try{
        Enterprise hub = list.get(i).getKey();
        hub.makeHub();
        result.add(hub);
        } catch (Exception E){
            System.out.printf("There are only %d number of Enterprises\n", i);
            break;
        }
    } 
    return result;
  }

  public int getAveragePathDistanceBetweenGroupOfEntities(Entity e1, List<Entity> entities){
    int count = 0;
    int sum = 0;

    // sums the shortest path size to from e1 to all entities
    // count paths between e1 and all entities
    for (int i = 0; i < entities.size(); i++) {
      sum += this.shortestPathDistance(e1, entities.get(i));
      count++;
    }

    return sum / count;
  }

}
