package isep.controller;

import isep.model.DistributionNetwork;
import isep.model.ExpeditionList;
import isep.shared.exceptions.InvalidHubException;
import isep.shared.exceptions.InvalidOrderException;
import isep.shared.exceptions.UndefinedHubsException;

public class ExpeditionListController {
  private DistributionNetwork network;

  public ExpeditionListController() {
    network = App.getInstance().getCompany().getDistributionNetwork();
  }

  public ExpeditionListController(DistributionNetwork network) {
    this.network = network;
  }

  public ExpeditionList getExpeditionList(Integer day)
      throws InvalidOrderException, InvalidHubException, UndefinedHubsException {
    if (!network.hasHub())
      return null;

    return network.getExpeditionList(day);
  }

  public ExpeditionList getExpeditionList(Integer day, Integer nProducers)
      throws InvalidOrderException, InvalidHubException, UndefinedHubsException {
    if (!network.hasHub())
      return null;

    return network.getExpeditionListForNNearestProducers(day, nProducers);
  }

  public void printExpeditionList(ExpeditionList expeditionList) {
    if (expeditionList == null)
      System.out.println("There is no data for this day");
    else
      System.out.println(expeditionList.toString());
  }
}
