package isep.model;

/**
 * Interface that represents a vertex heuristic.
 * This heuristic is used in the A* algorithm.
 * 
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
public interface VertexHeuristic<V> {
  public int getHeuristicValue(V v);
}
