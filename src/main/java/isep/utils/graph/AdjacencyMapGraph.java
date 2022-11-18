package isep.utils.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @param <V> Vertex value type
 * @param <E> Edge value type
 *
 * @author DEI-ESINF
 * @author Tomás Lopes <1211289@isep.ipp.pt>
 */
public class AdjacencyMapGraph<V, E> extends CommonGraph<V, E> {
  final private Map<V, MapVertex<V, E>> mapVertices; // all the Vertices of the graph

  // Constructs an empty graph (either undirected or directed)
  public AdjacencyMapGraph(boolean directed) {
    super(directed);
    mapVertices = new LinkedHashMap<>();
  }

  public AdjacencyMapGraph(Graph<V, E> g) {
    this(g.isDirected());
    copy(g, this);
  }

  @Override
  public boolean validVertex(V vert) {
    return (mapVertices.get(vert) != null);
  }

  @Override
  public Collection<V> adjVertices(V vert) {
    MapVertex<V, E> vertixMap = mapVertices.get(vert);

    if (vertixMap == null)
      return null;

    return vertixMap.getAllAdjVerts();
  }

  @Override
  public Collection<Edge<V, E>> edges() {
    ArrayList<Edge<V, E>> le = new ArrayList<>(numEdges);

    for (MapVertex<V, E> mv : mapVertices.values())
      le.addAll(mv.getAllOutEdges());

    return le;
  }

  @Override
  public Edge<V, E> edge(V vOrig, V vDest) {
    if (!validVertex(vOrig) || !validVertex(vDest))
      return null;

    MapVertex<V, E> mv = mapVertices.get(vOrig);

    return mv.getEdge(vDest);
  }

  @Override
  public Edge<V, E> edge(int vOrigKey, int vDestKey) {
    V vOrig = vertex(vOrigKey);
    V vDest = vertex(vDestKey);

    return edge(vOrig, vDest);
  }

  @Override
  public int outDegree(V vert) {
    if (!validVertex(vert))
      return -1;

    MapVertex<V, E> mv = mapVertices.get(vert);

    return mv.numAdjVerts();
  }

  @Override
  public int inDegree(V vert) {
    if (!validVertex(vert))
      return -1;

    int degree = 0;
    for (V otherVert : mapVertices.keySet())
      if (edge(otherVert, vert) != null)
        degree++;

    return degree;
  }

  @Override
  public Collection<Edge<V, E>> outgoingEdges(V vert) {
    if (!validVertex(vert))
      return null;

    MapVertex<V, E> mv = mapVertices.get(vert);

    return mv.getAllOutEdges();
  }

  @Override
  public Collection<Edge<V, E>> incomingEdges(V vert) {
    if (!validVertex(vert))
      return null;

    ArrayList<Edge<V, E>> le = new ArrayList<>();

    for (V otherVert : mapVertices.keySet()) {
      Edge<V, E> e = edge(otherVert, vert);
      if (e != null)
        le.add(e);
    }

    return le;
  }

  @Override
  public boolean addVertex(V vert) {
    if (vert == null)
      throw new RuntimeException("Vertices cannot be null!");
    if (validVertex(vert))
      return false;

    MapVertex<V, E> mv = new MapVertex<>(vert);
    vertices.add(vert);
    mapVertices.put(vert, mv);
    numVerts++;

    return true;
  }

  @Override
  public boolean addEdge(V vOrig, V vDest, E weight) {
    if (vOrig == null || vDest == null)
      throw new RuntimeException("Vertices cannot be null!");
    if (edge(vOrig, vDest) != null)
      return false;

    if (!validVertex(vOrig))
      addVertex(vOrig);

    if (!validVertex(vDest))
      addVertex(vDest);

    MapVertex<V, E> mvo = mapVertices.get(vOrig);
    MapVertex<V, E> mvd = mapVertices.get(vDest);

    Edge<V, E> newEdge = new Edge<>(mvo.getElement(), mvd.getElement(), weight);
    mvo.addAdjVert(mvd.getElement(), newEdge);
    numEdges++;

    // if graph is not direct insert other edge in the opposite direction
    if (!isDirected)
      // if vDest different vOrig
      if (edge(vDest, vOrig) == null) {
        Edge<V, E> otherEdge = new Edge<>(mvd.getElement(), mvo.getElement(), weight);
        mvd.addAdjVert(mvo.getElement(), otherEdge);
        numEdges++;
      }

    return true;
  }

  @Override
  public boolean removeVertex(V vert) {
    if (vert == null)
      throw new RuntimeException("Vertices cannot be null!");
    if (!validVertex(vert))
      return false;

    // remove all edges that point to vert
    for (Edge<V, E> edge : incomingEdges(vert))
      removeEdge(edge.getVOrig(), vert);

    MapVertex<V, E> mv = mapVertices.get(vert);

    // The edges that live from vert are removed with the vertex
    numEdges -= mv.numAdjVerts();
    mapVertices.remove(vert);
    vertices.remove(vert);

    numVerts--;

    return true;
  }

  @Override
  public boolean removeEdge(V vOrig, V vDest) {
    if (vOrig == null || vDest == null)
      throw new RuntimeException("Vertices cannot be null!");
    if (!validVertex(vOrig) || !validVertex(vDest))
      return false;

    Edge<V, E> edge = edge(vOrig, vDest);

    if (edge == null)
      return false;

    MapVertex<V, E> mvo = mapVertices.get(vOrig);

    mvo.remAdjVert(vDest);
    numEdges--;

    // if graph is not directed
    if (!isDirected) {
      edge = edge(vDest, vOrig);
      if (edge != null) {
        MapVertex<V, E> mvd = mapVertices.get(vDest);
        mvd.remAdjVert(vOrig);
        numEdges--;
      }
    }
    return true;
  }

  // Returns a clone of the graph
  @Override
  public AdjacencyMapGraph<V, E> clone() {
    AdjacencyMapGraph<V, E> g = new AdjacencyMapGraph<>(this.isDirected);

    copy(this, g);

    return g;
  }

  // string representation
  @Override
  public String toString() {
    String s;

    if (numVerts == 0) {
      s = "\nGraph not defined!!";
    } else {
      s = "Graph: " + numVerts + " vertices, " + numEdges + " edges\n";
      for (MapVertex<V, E> mv : mapVertices.values())
        s += mv + "\n";
    }
    return s;
  }
}
