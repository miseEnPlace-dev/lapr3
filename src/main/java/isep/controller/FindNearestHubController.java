package isep.controller;

import isep.model.DistributionNetwork;

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
}
