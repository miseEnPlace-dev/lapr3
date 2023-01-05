package isep.ui;

import java.util.List;

import isep.controller.App;
import isep.controller.DefineHubsController;
import isep.model.DistributionNetwork;
import isep.model.Enterprise;
import isep.shared.exceptions.InvalidNumberOfHubsException;
import isep.ui.utils.Utils;

public class DefineHubsUI implements Runnable {
  private DefineHubsController controller;
  private DistributionNetwork network;
  private List<Enterprise> hubs;

  public DefineHubsUI() {
  }

  @Override
  public void run() {
    network = App.getInstance().getCompany().getDistributionNetwork();
    controller = new DefineHubsController(network);

    int numberOfHubs = Utils.readIntegerFromConsole("\n\nNumber of hubs: ");
    try {
      hubs = controller.defineHubs(numberOfHubs);
    } catch (InvalidNumberOfHubsException e) {
      System.out.println("Invalid number of hubs");
    }

    Utils.showList(hubs, "\nHubs defined: ");

    Utils.readLineFromConsole("\nPress any key to continue... ");

  }

}
