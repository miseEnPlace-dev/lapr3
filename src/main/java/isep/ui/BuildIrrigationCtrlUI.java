package isep.ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NameNotFoundException;
import isep.controller.BuildIrrigationCtrlController;
import isep.model.IrrigationController;
import isep.model.IrrigationPlan;
import isep.ui.utils.Utils;

public class BuildIrrigationCtrlUI implements Runnable {
  BuildIrrigationCtrlController ctrl;
  IrrigationController irrigationCtrl;

  public BuildIrrigationCtrlUI() {}

  @Override
  public void run() {
    List<String> options = new ArrayList<>();
    options.add("Build irrigation controller from a file");
    options.add("Insert mock parcels");
    options.add("Get irrigation status from the controller");

    int option = 0;

    do {
      option = Utils.showAndSelectIndex(options, "\nBuild Irrigation Controller Menu:");

      if ((option >= 0) && (option < options.size())) {
        switch (option) {
          case 0:
            buildIrrigationCtrl();
            break;
          case 1:
            insertMockParcels();
            break;
          case 2:
            getIrrigationStatus();
            break;
        }
        Utils.readLineFromConsole("\nPress any key to continue... ");
      }
    } while (option != -1);
  }

  private void buildIrrigationCtrl() {
    String filePath = Utils.readLineFromConsole("\nInsert the path of the file containing an irrigation plan: ");

    try {
      ctrl = new BuildIrrigationCtrlController(filePath);
      List<String> data = ctrl.readIrrigationPlanFile();
      IrrigationPlan plan = ctrl.mapIrrigationPlanData(data);
      irrigationCtrl = ctrl.createIrrigationController(plan);
      System.out.println("Irrigation controller created successfully.");


    } catch (FileNotFoundException e) {
      System.out.println("The file does not exist.");
    } catch (NameNotFoundException e) {
      System.out.println("Error: " + e.getMessage());
      System.out.println("Perhaps you may want to load mock parcels first.");
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private void insertMockParcels() {}

  private void getIrrigationStatus() {

  }
}
