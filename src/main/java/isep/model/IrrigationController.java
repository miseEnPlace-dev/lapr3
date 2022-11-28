package isep.model;

import java.util.Calendar;

public class IrrigationController {
  private IrrigationPlan plan;

  public IrrigationController(IrrigationPlan plan) {
    this.plan = plan;
  }

  public IrrigationPlan getPlan() {
    return plan;
  }

  public CurrentIrrigationWrapper currentIrrigation(Calendar date) {
    return plan.getIrrigationStatus(date);
  }
}
