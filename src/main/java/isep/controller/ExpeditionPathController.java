package isep.controller;

import java.util.ArrayList;
import java.util.List;

import isep.model.DistributionNetwork;
import isep.model.ExpeditionList;
import isep.model.ExpeditionPath;
import isep.model.Entity;

/**
 * Controller for US310 - find the shortest expedition path, given a daily
 * expedition list.
 * 
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
public class ExpeditionPathController {
  private DistributionNetwork distributionNetwork;
  private ExpeditionList expeditionList;

  /**
   * Constructor for ShortestExpeditionPathController.
   */
  public ExpeditionPathController(DistributionNetwork distributionNetwork, ExpeditionList expeditionList) {
    this.distributionNetwork = distributionNetwork;
    this.expeditionList = expeditionList;
  }

  /**
   * Finds the shortest expedition path for the given daily expedition list.
   * 
   * @return An ExpeditionPath object containing the shortest path.
   */
  public ExpeditionPath findExpeditionPath() {
    return new ExpeditionPath(distributionNetwork, expeditionList);
  }
}
