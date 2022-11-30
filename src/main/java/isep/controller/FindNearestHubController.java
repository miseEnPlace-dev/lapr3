package isep.controller;

import java.util.HashMap;
import java.util.Map;

import isep.model.Client;
import isep.model.DistributionNetwork;
import isep.model.Enterprise;

/**
 * Controller for US304 - for each client, find the nearest hub.
 *
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
public class FindNearestHubController {
  private DistributionNetwork distributionNetwork;

  /**
   * Constructor for FindNearestHubController.
   *
   * @param distributionNetwork The distribution network
   */
  public FindNearestHubController(DistributionNetwork distributionNetwork) {
    this.distributionNetwork = distributionNetwork;
  }

  /**
   * For each client in the distribution network, find the nearest hub.
   *
   * @return a map with the client as key and the nearest hub as value
   */
  public Map<Client, Enterprise> findNearestHub() {
    Map<Client, Enterprise> result = new HashMap<>();

    // For each client, find the nearest hub
    for (Client client : distributionNetwork.getClients()) {
      result.put(client, distributionNetwork.getNearestHub(client));
    }

    return result;
  }
}
