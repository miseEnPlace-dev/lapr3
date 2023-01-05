package isep.ui;

import java.util.ArrayList;
import java.util.List;

import isep.model.ExpeditionList;
import isep.ui.utils.Utils;

public class GenerateExpeditionListUI implements Runnable {
  private ExpeditionList expeditionList;

  public GenerateExpeditionListUI() {
  }

  @Override
  public void run() {

    List<String> options = new ArrayList<>();

    options.add("With zero restritions");
    options.add("With N Producers");

    int option = 0;

    do {

      Utils.showList(options, "\nMenu:");
      option = Utils.readIntegerFromConsole("Select an option: ");

      if (option == 1) {
        expeditionList = expeditionListWithNullRestritions();
      } else if (option == 2) {
        expeditionList = expeditionListWithNProducers();
      }
    } while (option >= 1 && option <= 2);
  }

  private ExpeditionList expeditionListWithNullRestritions() {
    ExpeditionList eList = new ExpeditionList(0);
    System.out.println("\nexpedition list without Restritions");

    return eList;
  }

  private ExpeditionList expeditionListWithNProducers() {
    ExpeditionList eList = new ExpeditionList(0);
    System.out.println("\nexpedition list with n producers");

    return eList;
  }

}
