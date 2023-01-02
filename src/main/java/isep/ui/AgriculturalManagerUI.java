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
    options.add(new MenuItem("Minimum number of connections", new GetMinNumberOfConnectionsUI()));
    options.add(new MenuItem("Add hubs to network", new DefineHubsUI()));
    options.add(new MenuItem("Find Nearest Hub", new NearestHubUI()));
    options.add(new MenuItem("Find network minimum cost", new NetworkMinimumCostUI()));
    // options.add(new MenuItem("Import basket list", new ImportBasketListUI()));
    // options.add(new MenuItem("Generate expedition list", new
    // GenerateExpeditionListUI()));
    // options.add(new MenuItem("Generate expedition list with N producers", new
    // GenerateExpeditionListNUI()));
    // options.add(new MenuItem("Minimum total distance for expedition list", new
    // MinimumTotalDistanceUI()));
    // options.add(new MenuItem("Statistics", new StatisticsUI()));

    int option = 0;

    do {
      option = Utils.showAndSelectIndex(options, "\n\nAgricultural Manager Menu:");

      if ((option >= 0) && (option < options.size())) {
        options.get(option).run();
      }

    } while (option != -1);

  }
}
