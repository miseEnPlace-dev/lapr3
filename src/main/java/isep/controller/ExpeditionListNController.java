package isep.controller;

import isep.model.DistributionNetwork;
import isep.model.ExpeditionList;

/*
 * US309 - Controller that generates an expedition list for a given network and day with N producers
 *
 * @author André Barros <1211299@isep.ipp.pt>
 */
public class ExpeditionListNController {
  private DistributionNetwork network;
  private int n;
  private ExpeditionList expeditionList;

  /*
   * Constructor
   */
  public ExpeditionListNController(DistributionNetwork network, int n) {
    setNetwork(network);
    setN(n);
    this.expeditionList = new ExpeditionList(n);
  }

  /*
   * Validates n and sets it
   */
  private void setN(int n) {
    if (n < 0)
      throw new IllegalArgumentException("N must be positive number");
    this.n = n;
  }

  /*
   * Validates network and sets it
   */
  private void setNetwork(DistributionNetwork network) {
    if (network == null)
      throw new IllegalArgumentException("Network is null");
    this.network = network;
  }
}
