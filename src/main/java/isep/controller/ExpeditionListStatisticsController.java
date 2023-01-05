package isep.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import isep.model.Basket;
import isep.model.Client;
import isep.model.Enterprise;
import isep.model.ExpeditionList;
import isep.model.Producer;

public class ExpeditionListStatisticsController {
  private final ExpeditionList expeditionList;

  public ExpeditionListStatisticsController(ExpeditionList expeditionList) {
    this.expeditionList = expeditionList;
  }

  public Map<String, String> getBasketStatistics() {
    Map<String, String> result = new LinkedHashMap<>();

    for (Basket basket : expeditionList.getBaskets()) {
      int fullySatisfiedProducts = expeditionList.getNumberOfFullySatisfiedProducts(basket);
      int partiallySatisfiedProducts = expeditionList.getNumberOfPartiallySatisfiedProducts(basket);
      double percentageOfFullySatisfiedProducts = expeditionList.getPercentageOfFullySatisfiedProducts(basket);
      int numberOfDistinctProducers = expeditionList.getNumberOfDistinctProducersForBasket(basket);

      result.put("Nº de produtos totalmente satisfeitos", Integer.toString(fullySatisfiedProducts));
      result.put("Nº de produtos parcialmente satisfeitos", Integer.toString(partiallySatisfiedProducts));
      result.put("Percentagem total do cabaz satisfeito", Double.toString(percentageOfFullySatisfiedProducts));
      result.put("Nº de produtores que fornecem o cabaz", Integer.toString(numberOfDistinctProducers));
    }

    return result;
  }

  public Map<String, String> getClientStatistics() {
    Map<String, String> result = new LinkedHashMap<>();

    for (Client client : expeditionList.getClients()) {
      // TODO
    }

    return result;
  }

  public Map<String, String> getProducers() {
    Map<String, String> result = new LinkedHashMap<>();

    for (Producer producer : expeditionList.getProducers()) {
      // TODO
    }

    return result;
  }

  public Map<String, String> getHubs() {
    Map<String, String> result = new LinkedHashMap<>();

    for (Enterprise hub : expeditionList.getHubs()) {
      // TODO
    }

    return result;
  }
}
