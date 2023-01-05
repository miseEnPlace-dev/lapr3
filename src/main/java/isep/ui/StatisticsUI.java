package isep.ui;

import java.util.ArrayList;
import java.util.List;

import isep.controller.App;
import isep.controller.ExpeditionListStatisticsController;
import isep.ui.utils.Utils;

public class StatisticsUI implements Runnable {
  private ExpeditionListStatisticsController controller;

  public StatisticsUI() {
  }

  @Override
  public void run() {
    controller = new ExpeditionListStatisticsController(App.getInstance().getCompany().getCurrentExpeditionList());

    List<String> options = new ArrayList<>();
    options.add("Basket");
    options.add("Client");
    options.add("Producer");
    options.add("Hub");

    Utils.showAndSelectIndex(options, "Statistics");
  }
}
