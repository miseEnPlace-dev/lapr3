package isep.model;

import java.util.List;
import java.util.Map;
import isep.shared.Hour;

public class IrrigationPlan {
  private List<Hour> hours;
  private Map<AgriculturalParcel, ParcelIrrigationWrapper> parcels;

  public IrrigationPlan(List<Hour> hours, Map<AgriculturalParcel, ParcelIrrigationWrapper> parcels) {
    this.hours = hours;
    this.parcels = parcels;
  }

  public List<Hour> getHours() {
    return hours;
  }

  public Map<AgriculturalParcel, ParcelIrrigationWrapper> getParcels() {
    return parcels;
  }
}
