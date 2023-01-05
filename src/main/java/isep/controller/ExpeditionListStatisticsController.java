package isep.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

  public List<Map<String, String>> getBasketsStatistics() {
    List<Map<String, String>> result = new ArrayList<>();

    for (Basket basket : expeditionList.getBaskets()) {
      Map<String, String> current = new LinkedHashMap<>();

      int fullySatisfiedProducts = expeditionList.getNumberOfFullySatisfiedProducts(basket);
      int partiallySatisfiedProducts = expeditionList.getNumberOfPartiallySatisfiedProducts(basket);
      double percentageOfFullySatisfiedProducts = expeditionList.getPercentageOfFullySatisfiedProducts(basket);
      int numberOfDistinctProducers = expeditionList.getNumberOfDistinctProducersForBasket(basket);

      current.put("No. of products totally fulfilled", Integer.toString(fullySatisfiedProducts));
      current.put("No. of products partially fulfilled", Integer.toString(partiallySatisfiedProducts));
      current.put("Basket fulfillment percentage", Double.toString(percentageOfFullySatisfiedProducts));
      current.put("No. of producers that supply basket", Integer.toString(numberOfDistinctProducers));

      result.add(current);
    }

    return result;
  }

  public List<Map<String, String>> getClientsStatistics() {
    List<Map<String, String>> result = new ArrayList<>();

    for (Client client : expeditionList.getClients()) {
      Map<String, String> current = new LinkedHashMap<>();
      int numberOfFullyFulfilledBaskets = expeditionList.getNumberOfFullyFulfilledBasketsByClient(client);
      int numberOfPartiallyFulfilledBaskets = expeditionList.getNumberOfPartiallyFulfilledBasketsByClient(client);
      int numberOfDistinctProducers = expeditionList.getNumberOfDistinctProducersThatSupplyAllClientsBasket(client);

      current.put("No. of totally fulfilled baskets", Integer.toString(numberOfFullyFulfilledBaskets));
      current.put("No. of partially fulfilled baskets", Integer.toString(numberOfPartiallyFulfilledBaskets));
      current.put("No. of distinct producers that supply all baskets", Integer.toString(numberOfDistinctProducers));

      result.add(current);
    }

    return result;
  }

  public List<Map<String, String>> getProducersStatistics() {
    List<Map<String, String>> result = new ArrayList<>();

    for (Producer producer : expeditionList.getProducers()) {
      Map<String, String> current = new LinkedHashMap<>();
      int numberOfFullySuppliedBaskets = expeditionList.getNumberOfFullySuppliedBasketsByProducer(producer);
      int numberOfPartiallySuppliedBaskets = expeditionList.getNumberOfPartiallySuppliedBasketsByProducer(producer);
      int numberOfDistinctClients = expeditionList.getNumberOfDistinctClients(producer);
      int numberOfOutOfStockProducts = expeditionList.getNumberOfOutOfStockProducts(producer);
      int numberOfSuppliedHubs = expeditionList.getNumberOfDistinctHubs(producer);

      current.put("No. of totally supplied baskets", Integer.toString(numberOfFullySuppliedBaskets));
      current.put("No. of partially supplied baskets", Integer.toString(numberOfPartiallySuppliedBaskets));
      current.put("No. of distinct clients", Integer.toString(numberOfDistinctClients));
      current.put("No. of out of stock products", Integer.toString(numberOfOutOfStockProducts));
      current.put("No. of supplied hubs", Integer.toString(numberOfSuppliedHubs));

      result.add(current);
    }

    return result;
  }

  public List<Map<String, String>> getHubsStatistics() {
    List<Map<String, String>> result = new ArrayList<>();

    for (Enterprise hub : expeditionList.getHubs()) {
      Map<String, String> current = new LinkedHashMap<>();

      int numberOfDistinctClients = expeditionList.getNumberOfDistinctClients(hub);
      int numberOfDistinctProducers = expeditionList.getNumberOfDistinctProducers(hub);

      current.put("No. of distinct clients", Integer.toString(numberOfDistinctClients));
      current.put("No. of distinct producers", Integer.toString(numberOfDistinctProducers));

      result.add(current);
    }

    return result;
  }
}
