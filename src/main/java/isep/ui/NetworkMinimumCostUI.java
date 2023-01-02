package isep.ui;

import isep.controller.App;
import isep.controller.ConnectedNetworkShortestPathController;
import isep.model.DistributionNetwork;
import isep.model.Entity;
import isep.shared.exceptions.NetworkNotConnectedException;
import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Graph;
import isep.utils.graph.MapVertex;

public class NetworkMinimumCostUI implements Runnable {
  ConnectedNetworkShortestPathController controller;
  DistributionNetwork network;

  public NetworkMinimumCostUI() {
    network = App.getInstance().getCompany().getDistributionNetwork();
    controller = new ConnectedNetworkShortestPathController(network);
  }

  @Override
  public void run() {
    try {
      System.out.println(printNetworkMinimumCost(controller.getConnectedNetworkShortestPath()));
    } catch (NetworkNotConnectedException e) {
      System.out.println("Missing data for this action");
    }
  }

  private String printNetworkMinimumCost(Graph<Entity, Integer> graph) {
    String s;

    s = "\n\nNetwork minimum cost between all entities:\nnumber of vertices =  " + graph.numVertices()
        + "\nnumber of edges = " + graph.numEdges();

    for (Entity v : graph.vertices()) {
      s += "\n" + v.getId() + ":\n";
      for (Entity w : graph.vertices()) {
        if (v != w) {
          s += "  " + w.getId() + ": " + graph.edge(v, w) + "\n";
        }
      }
    }

    s += "\nTotal cost: " + getNetworkMinimumCost(graph);
    return s;
  }

  private int getNetworkMinimumCost(Graph<Entity, Integer> graph) {
    int cost = 0;

    for (Entity v : graph.vertices()) {
      for (Entity w : graph.vertices()) {
        if (v != w) {
          cost += graph.edge(v, w).getWeight();
        }
      }
    }
    return cost;
  }

}
