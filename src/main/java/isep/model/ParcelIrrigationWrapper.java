package isep.model;

/**
 * Wrapper for irrigation data for a parcel (duration and regularity).
 */
public class ParcelIrrigationWrapper {
  private AgriculturalParcel parcel;
  private int duration;
  private Regularity regularity;

  public ParcelIrrigationWrapper(AgriculturalParcel parcel, int duration, Regularity regularity) {
    this.parcel = parcel;
    this.duration = duration;
    this.regularity = regularity;
  }

  public AgriculturalParcel getParcel() {
    return parcel;
  }

  public int getDuration() {
    return duration;
  }

  public Regularity getRegularity() {
    return regularity;
  }
}
