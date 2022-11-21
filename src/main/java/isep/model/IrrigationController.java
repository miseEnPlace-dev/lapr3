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
    // TODO is irrigating; can two parcels be irrigating at the same time?
    return plan.getIrrigationStatus(date);
  }
}
