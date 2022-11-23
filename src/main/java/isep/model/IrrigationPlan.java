package isep.model;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import isep.shared.Hour;
import isep.shared.exceptions.InvalidHourFormatException;

public class IrrigationPlan {
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

  public CurrentIrrigationWrapper getIrrigationStatus(Calendar date) {
    // TODO: change this
    // compare number of days between creationDate and date
    // check if the plan duration is not exceeded
    Calendar tmp = (Calendar) date.clone();
    tmp.set(Calendar.HOUR_OF_DAY, 0);
    tmp.set(Calendar.MINUTE, 0);
    tmp.set(Calendar.SECOND, 0);
    tmp.set(Calendar.MILLISECOND, 0);

    tmp.add(Calendar.DAY_OF_MONTH, -planDuration);

    if (creationDate.after(tmp)) return null;

    tmp.add(Calendar.DAY_OF_MONTH, planDuration);

    // iterate through the parcels
    for (AgriculturalParcel parcel : parcels.keySet()) {
      ParcelIrrigationWrapper wrapper = parcels.get(parcel);

      Hour hour = null;
      try {
        hour = new Hour(date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
      } catch (InvalidHourFormatException e) {
        e.printStackTrace();
      }

      // check regularity
      // get difference of days between creationDate and date
      int diff = (int) ((date.getTimeInMillis() - creationDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
      if (wrapper.getRegularity().check(diff)) break;

      for (Hour h : hours) {
        int timeRemaining = h.getTimeInMinutes() + wrapper.getDuration();
        if (h.getTimeInMinutes() >= hour.getTimeInMinutes() && timeRemaining > 0) {
          return new CurrentIrrigationWrapper(parcel, timeRemaining);
        }
      }
    }

    return null;
  }
}
