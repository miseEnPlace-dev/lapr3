package isep.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import isep.utils.graph.AdjacencyMapGraph;

public class NetworkGraph<V, E> extends AdjacencyMapGraph<V, E> {
  public NetworkGraph(boolean directed) {
    super(directed);
  }

  public <T extends Entity> List<T> getEntitiesWithClass(Class c) {
    List<T> result = new ArrayList<>();
    for (V vertice : super.mapVertices.keySet())
      if (vertice.getClass() == c)
        result.add((T) vertice);

    return result;
  }

  public List<Enterprise> getHubs() {
    List<Enterprise> hubs = new ArrayList<>();
    for (V vertice : super.mapVertices.keySet()) {
      if (vertice.getClass() == Enterprise.class && ((Enterprise) vertice).isHub())
        hubs.add((Enterprise) vertice);
    }
    return hubs;
  }

  public HashMap<Producer, DailyData> getProducersStockUntilDate(Integer day) {
    HashMap<Producer, DailyData> result = new HashMap<>();
    for (V vertice : super.mapVertices.keySet())
      if (vertice.getClass() == Producer.class) {
        Producer p = (Producer) vertice;
        result.put(p, p.getStockUntilDate(day));
      }

    return result;
  }
}
