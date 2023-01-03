package isep.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NameNotFoundException;

import isep.model.IrrigationController;
import isep.model.IrrigationPlan;
import isep.model.mapper.IrrigationPlanMapper;
import isep.model.store.AgriculturalParcelStore;
import isep.shared.exceptions.InvalidFileFormatException;
import isep.shared.exceptions.InvalidHourFormatException;
import isep.utils.CustomScanner;

/*
 * US 310 - Controller that builds an irrigation controller
 *
 * @author Ricardo Moreira <1211285@isep.ipp.pt>
 */
public class BuildIrrigationCtrlController {
  private AgriculturalParcelStore store;
  private CustomScanner scanner;
  private List<String> data;

  /*
   * Constructor for the controller with file name and store
   */
  public BuildIrrigationCtrlController(String fileName, AgriculturalParcelStore store)
      throws FileNotFoundException {
    this.store = store;
    this.scanner = new CustomScanner(fileName);
    this.data = new ArrayList<>();
  }

  /*
   * Constructor for the controller with file name
   */
  public BuildIrrigationCtrlController(String fileName) throws FileNotFoundException {
    this.store = App.getInstance().getCompany().getAgriculturalParcelStore();
    this.scanner = new CustomScanner(fileName);
    this.data = new ArrayList<>();
  }

  // read the irrigation plan file
  public List<String> readIrrigationPlanFile() {
    if (this.data.isEmpty())
      while (scanner.hasNextLine())
        data.add(scanner.nextLine());

    return this.data;
  }

  // map the contents of the file to an irrigation plan object
  public IrrigationPlan mapIrrigationPlanData(List<String> data)
      throws NameNotFoundException, InvalidHourFormatException, InvalidFileFormatException {
    Calendar today = Calendar.getInstance();
    return IrrigationPlanMapper.toPlan(data, today, this.store);
  }

  // create a new irrigation controller
  public IrrigationController createIrrigationController(IrrigationPlan plan) {
    IrrigationController controller = new IrrigationController(plan);
    return controller;
  }
}
