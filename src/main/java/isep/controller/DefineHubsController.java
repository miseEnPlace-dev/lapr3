package isep.controller;

import java.util.List;

import isep.model.DistributionNetwork;
import isep.model.Enterprise;
import isep.shared.exceptions.InvalidNumberOfHubsException;

public class DefineHubsController {
  private DistributionNetwork network;

  public DefineHubsController(DistributionNetwork network) {
    this.network = network;
  }

  public List<Enterprise> defineHubs(int numberOfHubs) throws InvalidNumberOfHubsException {
    return network.defineHubs(numberOfHubs);
  }
}
