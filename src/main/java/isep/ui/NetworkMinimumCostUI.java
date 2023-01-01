package isep.ui;

import isep.controller.App;
import isep.controller.ConnectedNetworkShortestPathController;
import isep.model.DistributionNetwork;
import isep.model.Entity;
import isep.shared.exceptions.NetworkNotConnectedException;
import isep.utils.graph.Graph;

public class NetworkMinimumCostUI implements Runnable {
  ConnectedNetworkShortestPathController controller;
  DistributionNetwork network;

  public NetworkMinimumCostUI() {
    network = App.getInstance().getCompany().getDistributionNetwork();
    controller = new ConnectedNetworkShortestPathController(network);
  }

  @Override
  public void run() {
    System.out.println("\nYour network minimum cost is:");
    try {
      controller.getConnectedNetworkShortestPath().toString();
    } catch (NetworkNotConnectedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void printNetworkMinimumCost(Graph<Entity, Integer> graph) {
    // TODO implement this method
  }

}
