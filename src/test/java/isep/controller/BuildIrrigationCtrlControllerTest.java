package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.FileNotFoundException;
import java.util.List;
import javax.naming.NameNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import isep.mock.IrrigationPlanMock;
import isep.model.Company;
import isep.model.IrrigationPlan;
import isep.shared.exceptions.InvalidFileFormatException;
import isep.shared.exceptions.InvalidHourFormatException;

public class BuildIrrigationCtrlControllerTest {
  static BuildIrrigationCtrlController ctrl;
  static final String IRRIGATION_PLAN_1_FILEPATH = "./src/test/resources/irrigationPlan1.txt";
  static final String IRRIGATION_PLAN_2_FILEPATH = "./src/test/resources/irrigationPlan2.txt";

  @BeforeAll
  public static void setup() throws FileNotFoundException {
    Company company = App.getInstance().getCompany();
    ctrl = new BuildIrrigationCtrlController(IRRIGATION_PLAN_1_FILEPATH, company.getAgriculturalParcelStore());
  }

  @Test
  public void testReadIrrigationPlanFile() {
    List<String> data = ctrl.readIrrigationPlanFile();
    List<String> expected = isep.mock.IrrigationPlanMock.mockIrrigationPlanFile1();
    assertEquals(expected, data);
  }

  @Test
  public void testIrrigationPlan1()
      throws NameNotFoundException, InvalidHourFormatException, InvalidFileFormatException {
    IrrigationPlan irrigationPlan = IrrigationPlanMock.mockIrrigationPlan();
    IrrigationPlan actual = ctrl.mapIrrigationPlanData(ctrl.readIrrigationPlanFile());
    assertEquals(irrigationPlan, actual);
  }

  @Test
  public void testSlightlyDifferentIrrigationPlan1() throws NameNotFoundException,
      InvalidHourFormatException, InvalidFileFormatException, FileNotFoundException {
    BuildIrrigationCtrlController ctrl2 = new BuildIrrigationCtrlController(IRRIGATION_PLAN_2_FILEPATH, App.getInstance().getCompany().getAgriculturalParcelStore());
    IrrigationPlan irrigationPlan = IrrigationPlanMock.mockIrrigationPlan();
    IrrigationPlan actual = ctrl2.mapIrrigationPlanData(ctrl2.readIrrigationPlanFile());
    assertNotEquals(irrigationPlan, actual);
  }

  /**
   * Tests if the ctrler does not accept files that do not exist.
   */
  @Test
  public void testInvalidIrrigationPlanFile() {
    assertThrows(FileNotFoundException.class,
        () -> new BuildIrrigationCtrlController(
            "invalid_file_path", App.getInstance().getCompany().getAgriculturalParcelStore()));
  }
}
