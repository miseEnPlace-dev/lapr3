package isep.ui;

import java.io.FileNotFoundException;
import java.util.List;
import javax.naming.NameNotFoundException;
import isep.controller.BuildIrrigationCtrlController;
import isep.model.IrrigationController;
import isep.model.IrrigationPlan;
import isep.shared.exceptions.InvalidFileFormatException;
import isep.shared.exceptions.InvalidHourFormatException;
import isep.ui.utils.Utils;

public class BuildIrrigationCtrlUI implements Runnable {
  BuildIrrigationCtrlController ctrl;
  IrrigationController irrigationCtrl;

  public BuildIrrigationCtrlUI() {}

  @Override
  public void run() {
    String filePath = Utils.readLineFromConsole("\nInsert the path of the file containing an irrigation plan: ");

    try {
      ctrl = new BuildIrrigationCtrlController(filePath);
      List<String> data = ctrl.readIrrigationPlanFile();
      IrrigationPlan plan = ctrl.mapIrrigationPlanData(data);
      irrigationCtrl = ctrl.createIrrigationController(plan);
      System.out.println("Irrigation controller created successfully.");
    } catch (FileNotFoundException e) {
      System.out.println("The file does not exist.");
    } catch (NameNotFoundException | InvalidHourFormatException | InvalidFileFormatException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

}
