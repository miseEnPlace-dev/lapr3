package isep.model;

public class ParcelIrrigationWrapper {
  private int duration;
  private Regularity regularity;

  public ParcelIrrigationWrapper(int duration, Regularity regularity) {
    this.duration = duration;
    this.regularity = regularity;
  }

  public int getDuration() {
    return duration;
  }

  public Regularity getRegularity() {
    return regularity;
  }
}
