package isep.controller;

import java.util.ArrayList;
import java.util.List;

import isep.model.DistributionNetwork;
import isep.model.ExpeditionList;
import isep.model.Entity;

/**
 * Controller for US310 - find the shortest expedition path, given a daily
 * expedition list.
 * 
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
public class ShortestExpeditionPathController {
  private DistributionNetwork distributionNetwork;
  private ExpeditionList expeditionList;

  /**
   * Constructor for ShortestExpeditionPathController.
   */
  public ShortestExpeditionPathController(DistributionNetwork distributionNetwork, ExpeditionList expeditionList) {
    this.distributionNetwork = distributionNetwork;
    this.expeditionList = expeditionList;
  }

  /**
   * Finds the shortest expedition path for the given daily expedition list.
   * 
   * @return A list of entities representing the shortest expedition path.
   */
  public List<Entity> findShortestExpeditionPath() {
    // Declare return list
    List<Entity> shortestPath = new ArrayList<>();

    // Get all producers and hubs in the expedition list
    List<Entity> producersAndHubs = new ArrayList<>();
    producersAndHubs.addAll(this.expeditionList.getProducers());
    producersAndHubs.addAll(this.expeditionList.getHubs());

    return shortestPath;
  }
}
