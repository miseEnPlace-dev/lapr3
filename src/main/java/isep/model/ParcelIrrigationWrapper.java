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

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (!(obj instanceof ParcelIrrigationWrapper))
      return false;

    ParcelIrrigationWrapper wrapper = (ParcelIrrigationWrapper) obj;
    return wrapper.getParcel().equals(this.getParcel())
        && wrapper.getDuration() == this.getDuration()
        && wrapper.getRegularity().equals(this.getRegularity());
  }
}
