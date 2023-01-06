package isep.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.StyledEditorKit.BoldAction;

import isep.utils.graph.AdjacencyMapGraph;

public class NetworkGraph<V, E> extends AdjacencyMapGraph<V, E> {
  public NetworkGraph(boolean directed) {
    super(directed);
  }

  public <T extends Entity> List<T> getEntitiesWithClass(Class<T> c) throws ClassCastException {
    List<T> result = new ArrayList<>();
    for (V vertex : super.mapVertices.keySet())
      if (vertex.getClass().equals(c))
        result.add((T) vertex);

    return result;
  }

  public List<Enterprise> getHubs() {
    List<Enterprise> hubs = new ArrayList<>();
    for (V vertex : super.mapVertices.keySet()) {
      if (vertex.getClass() == Enterprise.class && ((Enterprise) vertex).isHub())
        hubs.add((Enterprise) vertex);
    }
    return hubs;
  }

  public Map<Producer, DailyData> getProducersStockUntilDate(Integer day) {
    Map<Producer, DailyData> result = new HashMap<>();

    for (V vertex : super.mapVertices.keySet())
      if (vertex.getClass() == Producer.class) {
        Producer p = (Producer) vertex;
        result.put(p, p.getStockUntilDate(day));
      }

    return result;
  }

  public boolean hasHubs(){
    for (V vertex : super.mapVertices.keySet())
      if (vertex.getClass() == Enterprise.class && ((Enterprise) vertex).isHub()) 
        return true;
    return false;
  }
}
