package isep.ui;

import java.util.ArrayList;
import java.util.List;
import isep.controller.App;
import isep.controller.ExpeditionListController;
import isep.model.ExpeditionList;
import isep.shared.exceptions.InvalidHubException;
import isep.shared.exceptions.InvalidOrderException;
import isep.shared.exceptions.UndefinedHubsException;
import isep.ui.utils.Utils;

public class GenerateExpeditionListUI implements Runnable {
  private ExpeditionListController controller;
  private int day;
  private int nProducers;
  private ExpeditionList expeditionList;

  public GenerateExpeditionListUI() {
  }

  @Override
  public void run() {

    List<String> options = new ArrayList<>();

    options.add("Without restrictions");
    options.add("With N Producers");

    int option = 0;

    do {

      Utils.showList(options, "\nGenerate expedition list:");
      option = Utils.readIntegerFromConsole("Select an option: ");

      if (option == 1) {
        try {
          expeditionListWithNoRestrictions();
        } catch (InvalidOrderException | InvalidHubException | UndefinedHubsException e) {
          System.out.println("Invalid data");
          System.out.println(e.getMessage());
        }
      } else if (option == 2) {
        try {
          expeditionListWithNProducers();
        } catch (InvalidOrderException | InvalidHubException | UndefinedHubsException e) {
          System.out.println("Invalid data");
          System.out.println(e.getMessage());
        }
      }
    } while (option < 1 && option > 2);
  }

  private void expeditionListWithNoRestrictions()
      throws InvalidOrderException, InvalidHubException, UndefinedHubsException {
    readDays();

    controller = new ExpeditionListController();
    expeditionList = controller.getExpeditionList(day);

    if (expeditionList == null) {
      System.out.println(
          "There is no data for this day\nTip: Before generating an expedition list, you may want to set the network hubs.");
    } else {
      App.getInstance().getCompany().setCurrentExpeditionList(expeditionList);
      App.getInstance().getCompany().setCurrentExpeditionListDay(day);

      controller.printExpeditionList(expeditionList);
    }
  }

  private void expeditionListWithNProducers()
      throws InvalidOrderException, InvalidHubException, UndefinedHubsException {
    readDays();
    readNProducers();

    controller = new ExpeditionListController();
    expeditionList = controller.getExpeditionList(day, nProducers);

    if (expeditionList == null) {
      System.out.println(
          "There is no data for this day\nTip: Before generating an expedition list, you may want to set the network hubs.");
    } else {
      App.getInstance().getCompany().setCurrentExpeditionList(expeditionList);
      App.getInstance().getCompany().setCurrentExpeditionListDay(day);

      controller.printExpeditionList(expeditionList);
    }

  }

  private void readDays() {
    day = Utils.readPositiveIntegerFromConsole("Insert the number of day: ");
  }

  private void readNProducers() {
    nProducers = Utils.readPositiveIntegerFromConsole("Insert the number of producers: ");
  }

}
