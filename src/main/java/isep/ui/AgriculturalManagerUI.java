package isep.ui;

import java.util.ArrayList;
import java.util.List;

import isep.ui.utils.Utils;

public class AgriculturalManagerUI implements Runnable {

  public AgriculturalManagerUI() {
  }

  @Override
  public void run() {

    List<MenuItem> options = new ArrayList<>();

    options.add(new MenuItem("Load distribution network", new LoadDistributionNetworkUI()));
    options.add(new MenuItem("Find minimum number of connections", new GetMinNumberOfConnectionsUI()));
    options.add(new MenuItem("Set network hubs", new DefineHubsUI()));
    options.add(new MenuItem("Find nearest hub for all clients", new NearestHubUI()));
    options.add(new MenuItem("Find minimum cost network", new NetworkMinimumCostUI()));
    options.add(new MenuItem("Import baskets list", new ImportBasketListUI()));
    // options.add(new MenuItem("Generate expedition list", new
    // GenerateExpeditionListUI()));
    // options.add(new MenuItem("Generate expedition list with nearest N producers",
    // new
    // GenerateExpeditionListNUI()));
    // options.add(new MenuItem("Generate expedition path", new
    // MinimumTotalDistanceUI()));
    // options.add(new MenuItem("Find statistics", new StatisticsUI()));

    int option = 0;

    do {
      option = Utils.showAndSelectIndex(options, "\n\nAgricultural Manager Menu:");

      if ((option >= 0) && (option < options.size())) {
        options.get(option).run();
      }

    } while (option != -1);

  }
}
