package isep.controller;

import java.util.List;

import isep.model.DistributionNetwork;
import isep.model.Enterprise;
import isep.shared.exceptions.InvalidNumberOfHubsException;

/*
 * Controller that defines hubs in a network
 *
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
public class DefineHubsController {
  private DistributionNetwork network;

  /*
   * Constructor
   */
  public DefineHubsController(DistributionNetwork network) {
    this.network = network;
  }

  /*
   * Defines hubs in a network
   */
  public List<Enterprise> defineHubs(int numberOfHubs) throws InvalidNumberOfHubsException {
    return network.defineHubs(numberOfHubs);
  }
}
