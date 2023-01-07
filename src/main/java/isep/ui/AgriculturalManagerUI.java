package isep.ui;

import java.util.ArrayList;
import java.util.List;

import isep.controller.App;
import isep.ui.utils.Utils;

public class AgriculturalManagerUI implements Runnable {

  public AgriculturalManagerUI() {
  }

  @Override
  public void run() {
    List<MenuItem> options = new ArrayList<>();
    List<MenuItem> networkOptions = new ArrayList<>();

    options.add(new MenuItem("Load distribution network", new LoadDistributionNetworkUI()));
    options.add(new MenuItem("Build irrigation controller", new BuildIrrigationCtrlUI(), true));

    networkOptions.add(new MenuItem("Find minimum number of connections", new GetMinNumberOfConnectionsUI()));
    networkOptions.add(new MenuItem("Set network hubs", new DefineHubsUI()));
    networkOptions.add(new MenuItem("Find nearest hub for all clients", new NearestHubUI()));
    networkOptions.add(new MenuItem("Find minimum cost network", new NetworkMinimumCostUI()));
    networkOptions.add(new MenuItem("Import baskets list", new ImportBasketListUI()));
    networkOptions.add(new MenuItem("Generate expedition list", new GenerateExpeditionListUI()));
    networkOptions.add(new MenuItem("Generate expedition path", new MinimumTotalDistanceUI()));
    networkOptions.add(new MenuItem("Get statistics for an expedition list", new StatisticsUI()));

    int option = 0;

    do {
      if (!App.getInstance().getCompany().getDistributionNetwork().getEntities().isEmpty() && options.size() == 2)
        options.addAll(networkOptions);

      showFilePathsSelected();

      option = Utils.showAndSelectIndex(options, "\nAgricultural Manager Menu:");

      if ((option >= 0) && (option < options.size())) {
        options.get(option).run();
        if (!options.get(option).skipEnterToContinue())
          Utils.readLineFromConsole("\nPress any key to continue... ");
      }

    } while (option != -1);
  }

  private void showFilePathsSelected() {
    if (App.getInstance().getCompany().getCurrentDistancesFilePath() != null)
      Utils.showRightToLeftText("Loaded distances", App.getInstance().getCompany().getCurrentDistancesFilePath());
    if (App.getInstance().getCompany().getCurrentEntitiesFilePath() != null)
      Utils.showRightToLeftText("Loaded entities", App.getInstance().getCompany().getCurrentEntitiesFilePath());
    if (App.getInstance().getCompany().getCurrentBasketsFilePath() != null)
      Utils.showRightToLeftText("Loaded baskets", App.getInstance().getCompany().getCurrentBasketsFilePath());
  }
}
