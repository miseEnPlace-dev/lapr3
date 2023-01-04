package isep.ui;

import isep.controller.ExpeditionListStatisticsController;
import isep.ui.utils.Utils;

public class StatisticsUI implements Runnable {
  private ExpeditionListStatisticsController controller;

  public StatisticsUI() {
  }

  @Override
  public void run() {

    // TODO Auto-generated method stub

    Utils.readLineFromConsole("Press any key to continue... ");

  }

}
