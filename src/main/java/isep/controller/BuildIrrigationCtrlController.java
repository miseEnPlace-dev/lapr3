package isep.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NameNotFoundException;
import isep.model.Company;
import isep.model.IrrigationController;
import isep.model.IrrigationPlan;
import isep.model.mapper.IrrigationPlanMapper;
import isep.shared.exceptions.InvalidFileFormatException;
import isep.shared.exceptions.InvalidHourFormatException;
import isep.utils.CustomScanner;

public class BuildIrrigationCtrlController {
  Company company;
  CustomScanner scanner;

  public BuildIrrigationCtrlController(Company company, String fileName)
      throws FileNotFoundException {
    this.company = company;
    this.scanner = new CustomScanner(fileName);
  }

  // read the irrigation plan file
  public List<String> readIrrigationPlanFile() {
    List<String> data = new ArrayList<>();

    while (scanner.hasNextLine())
      data.add(scanner.nextLine());

    return data;
  }

  // map the contents of the file to an irrigation plan object
  public IrrigationPlan mapIrrigationPlanData(List<String> data)
      throws NameNotFoundException, InvalidHourFormatException, InvalidFileFormatException {
    return IrrigationPlanMapper.toPlan(data, this.company);
  }

  // create a new irrigation controller
  public IrrigationController createIrrigationController(IrrigationPlan plan) {
    // TODO
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
