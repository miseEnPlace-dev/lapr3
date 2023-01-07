package isep.model;

import java.util.Calendar;
import java.util.List;
import isep.shared.Hour;
import isep.shared.exceptions.InvalidHourFormatException;

public class IrrigationPlan {
  private final int SECONDS_IN_DAY = 1000 * 60 * 60 * 24;

  private List<Hour> hours;
  private List<ParcelIrrigationWrapper> parcelIrrigations;
  private Calendar creationDate;
  private int planDuration;

  public IrrigationPlan(List<Hour> hours, List<ParcelIrrigationWrapper> parcelIrrigations,
      Calendar creationDate, int planDuration) {
    this.hours = hours;
    this.parcelIrrigations = parcelIrrigations;
    this.creationDate = creationDate;
    this.planDuration = planDuration;
  }

  public List<Hour> getHours() {
    return hours;
  }

  public List<ParcelIrrigationWrapper> getParcelIrrigations() {
    return parcelIrrigations;
  }

  public Calendar getCreationDate() {
    return creationDate;
  }

  public int getPlanDuration() {
    return planDuration;
  }

  public CurrentIrrigationWrapper getIrrigationStatus(Calendar date) {
    // check if the plan duration is not exceeded
    if (isExceeded(date)) return null;

    // get difference of days between creationDate and date for regularity
    int diff =
        (int) ((date.getTimeInMillis() - creationDate.getTimeInMillis()) / SECONDS_IN_DAY);

    // create an hour for the current date to be checked
    Hour hour = null;
    try {
      hour = new Hour(date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
    } catch (InvalidHourFormatException e) {
      e.printStackTrace();
    }

    /**
     * cycle: 8h30
     *   - parcel 1 > 15min
     *   - parcel 2 > 8min
     * cycle: 15h00
     *   (same)
     */

    int parcelOffset = 0;

    // iterate through the parcels
    for (ParcelIrrigationWrapper wrapper : parcelIrrigations) {
      // check regularity
      if (!wrapper.getRegularity().check(diff))
        break;

      for (Hour cycleHour : hours) {
        // cycle hour: 8h30
        // current hour: 8h40
        // current parcel duration offset: 8
        int fixedOffset = cycleHour.getTimeInMinutes() + parcelOffset; // 8h30 + 8min = 8h38

        if (fixedOffset <= hour.getTimeInMinutes() // 8h38 <= 8h40
            && wrapper.getDuration() + fixedOffset > hour.getTimeInMinutes()) { // 15min + 8h38 (cycle end) > 8h40
          int timeRemaining = wrapper.getDuration() + fixedOffset - hour.getTimeInMinutes();
          return new CurrentIrrigationWrapper(wrapper.getParcel(), timeRemaining);
        }
      }

      parcelOffset += wrapper.getDuration();
    }

    return null;
  }

  private boolean isExceeded(Calendar date) {
    Calendar endDate = (Calendar) creationDate.clone();
    endDate.set(Calendar.HOUR_OF_DAY, 0);
    endDate.set(Calendar.MINUTE, 0);
    endDate.set(Calendar.SECOND, 0);
    endDate.set(Calendar.MILLISECOND, 0);
    endDate.add(Calendar.DAY_OF_MONTH, planDuration + 1);
    return date.after(endDate) || date.before(creationDate);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (!(obj instanceof IrrigationPlan))
      return false;

    IrrigationPlan other = (IrrigationPlan) obj;

    return other.getPlanDuration() == this.getPlanDuration()
        && other.getParcelIrrigations().equals(this.getParcelIrrigations())
        && other.getHours().equals(this.getHours())
        && other.getCreationDate().get(Calendar.YEAR) == this.getCreationDate().get(Calendar.YEAR)
        && other.getCreationDate().get(Calendar.MONTH) == this.getCreationDate().get(Calendar.MONTH)
        && other.getCreationDate().get(Calendar.DAY_OF_MONTH) == this.getCreationDate()
            .get(Calendar.DAY_OF_MONTH);
  }
}
