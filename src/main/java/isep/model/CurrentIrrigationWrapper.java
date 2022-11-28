package isep.model;

public class CurrentIrrigationWrapper {
  private AgriculturalParcel parcel;
  private int irrigationDuration;

  public CurrentIrrigationWrapper(AgriculturalParcel parcel, int irrigationDuration) {
    this.parcel = parcel;
    this.irrigationDuration = irrigationDuration;
  }

  public AgriculturalParcel getParcel() {
    return parcel;
  }

  public int getIrrigationDuration() {
    return irrigationDuration;
  }
}
