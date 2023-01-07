package isep.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import isep.model.Client;
import isep.model.DistributionNetwork;
import isep.model.Enterprise;
import isep.model.Entity;

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
  public Map<Entity, Enterprise> findNearestHub() {
    Map<Entity, Enterprise> result = new HashMap<>();
    List<Entity> clientsAndEnterprises = new ArrayList<>();

    List<Client> clients = distributionNetwork.getClients();
    List<Enterprise> enterprises = distributionNetwork.getEnterprises();

    clientsAndEnterprises.addAll(clients);
    clientsAndEnterprises.addAll(enterprises);

    // For each client or enterprise, find the nearest hub
    for (Entity entity : clientsAndEnterprises) {
      // System.out.println("Getting nearest hub for " + entity.getId() + "...");
      result.put(entity, distributionNetwork.getNearestHub(entity));
      // System.out.println("Nearest hub found!");
    }

    return result;
  }
}
