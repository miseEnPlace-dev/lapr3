package isep.ui;

import java.util.ArrayList;
import java.util.List;
import isep.controller.App;
import isep.controller.ExpeditionListStatisticsController;
import isep.ui.utils.Utils;

public class StatisticsUI implements Runnable {
  private ExpeditionListStatisticsController controller;

  public StatisticsUI() {}

  private void showStatisticsOfOptions(int option) {
    switch (option) {
      case 1:
        Utils.showTable(controller.getBasketsStatistics(), "Baskets' Statistics");
        break;
      case 2:
        Utils.showTable(controller.getClientsStatistics(), "Clients' Statistics");
        break;
      case 3:
        Utils.showTable(controller.getProducersStatistics(), "Producers' Statistics");
        break;
      case 4:
        Utils.showTable(controller.getHubsStatistics(), "Hubs' Statistics");
        break;
      default:
        break;
    }
  }

  @Override
  public void run() {
    controller = new ExpeditionListStatisticsController(
        App.getInstance().getCompany().getCurrentExpeditionList());

    if (!controller.isExpeditionListLoaded()) {
      System.out.println("\nPlease load an expedition list first...");
      System.out.println("Going back to main menu...");
      return;
    }

    List<String> options = new ArrayList<>();
    options.add("Basket");
    options.add("Client");
    options.add("Producer");
    options.add("Hub");

    int option = 0;

    do {
      Utils.showList(options, "Statistics");
      option = Utils.readIntegerFromConsole("Option: ");

      showStatisticsOfOptions(option);
    } while (option < 0 || option > 4);
  }
}
