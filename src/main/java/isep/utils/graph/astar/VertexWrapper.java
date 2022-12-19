package isep.utils.graph.astar;

/**
 * A wrapper for a vertex in the graph. It contains the vertex itself, the
 * predecessor vertex, the total
 * cost from the start vertex to this vertex, the minimum remaining cost to the
 * target vertex and the sum of
 * the total cost and the minimum remaining cost.
 * 
 * Addapted from https://www.happycoders.eu/algorithms/a-star-algorithm-java/
 * 
 * @param <V> the type of the vertex
 * @author Tomás Russo <121288@isep.ipp.pt>
 */
public class VertexWrapper<V> implements Comparable<VertexWrapper<V>> {
  private final V vertex;
  private VertexWrapper<V> predecessor;
  private int totalCostFromStart;
  private final int minimumRemainingCostToTarget;
  private int costSum;

  public VertexWrapper(V vertex, VertexWrapper<V> predecessor, int totalCostFromStart,
      int minimumRemainingCostToTarget) {
    this.vertex = vertex;
    this.predecessor = predecessor;
    this.totalCostFromStart = totalCostFromStart;
    this.minimumRemainingCostToTarget = minimumRemainingCostToTarget;
    calculateCostSum();
  }

  private void calculateCostSum() {
    this.costSum = totalCostFromStart + minimumRemainingCostToTarget;
  }

  public void setTotalCostFromStart(int totalCostFromStart) {
    this.totalCostFromStart = totalCostFromStart;
    calculateCostSum();
  }

  public void setPredecessor(VertexWrapper<V> predecessor) {
    this.predecessor = predecessor;
  }

  public V getVertex() {
    return vertex;
  }

  public VertexWrapper<V> getPredecessor() {
    return predecessor;
  }

  public int getTotalCostFromStart() {
    return totalCostFromStart;
  }

  @Override
  public int compareTo(VertexWrapper<V> o) {
    return Integer.compare(this.costSum, o.costSum);
  }
}
