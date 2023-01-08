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
    if (network.getEntities().isEmpty()) {
      System.out.println("\nYou must load a distribution network first");
      return;
    }

    controller = new DefineHubsController(network);

    int numberOfHubs = Utils.readIntegerFromConsole("\nNumber of hubs: ");
    try {
      hubs = controller.defineHubs(numberOfHubs);

      System.out.println("\nHubs defined successfully");

      App.getInstance().getCompany().setCurrentDefinedHubs(hubs.size());

      Utils.showList(hubs, "\nHubs defined: ");
    } catch (InvalidNumberOfHubsException e) {
      System.out.println("\nInvalid number of hubs");
    }
  }
}
