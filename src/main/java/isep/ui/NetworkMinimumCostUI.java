package isep.ui;

import isep.controller.App;
import isep.controller.ConnectedNetworkShortestPathController;
import isep.model.DistributionNetwork;
import isep.model.Entity;
import isep.shared.exceptions.NetworkNotConnectedException;
import isep.utils.graph.Edge;
import isep.utils.graph.Graph;

public class NetworkMinimumCostUI implements Runnable {
  private ConnectedNetworkShortestPathController controller;
  private DistributionNetwork network;

  public NetworkMinimumCostUI() {
  }

  @Override
  public void run() {
    network = App.getInstance().getCompany().getDistributionNetwork();
    if (network.getEntities().isEmpty()) {
      System.out.println("\nYou must load a distribution network first");
      return;
    }

    controller = new ConnectedNetworkShortestPathController(network);

    try {
      Graph<Entity, Integer> graph = controller.getConnectedNetworkShortestPath();
      System.out.println(printNetworkMinimumCost(graph));
    } catch (NetworkNotConnectedException e) {
      System.out.println("Missing data for this action");
    }
  }

  private String printNetworkMinimumCost(Graph<Entity, Integer> graph) {
    String s;

    s = "\n\nNetwork minimum cost between all entities:\n\nNumber of vertices = " + graph.numVertices()
        + "\nNumber of edges = " + graph.numEdges();

    s += "\nTotal cost: " + getNetworkMinimumCost(graph);
    return s;
  }

  private int getNetworkMinimumCost(Graph<Entity, Integer> graph) {
    int cost = 0;

    for (Edge<Entity, Integer> v : graph.edges())
      cost += v.getWeight();

    return cost;
  }
}
