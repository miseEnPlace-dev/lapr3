package isep.shared;

import java.util.Calendar;
import isep.shared.exceptions.InvalidHourFormatException;

/**
 * Hour class.
 *
 * @author Ricardo Moreira <1211285@isep.ipp.pt>
 */
public class Hour implements Comparable<Hour> {
  private int hour;
  private int minute;

  public Hour(String hourStr) throws InvalidHourFormatException {
    int[] validHour = stringToHour(hourStr);
    setHour(validHour[0]);
    setMinute(validHour[1]);
  }

  public Hour(int hour, int minute) throws InvalidHourFormatException {
    setHour(hour);
    setMinute(minute);
  }

  public Hour(int minutes) throws InvalidHourFormatException {
    int hour = minutes / 60;
    int minute = minutes % 60;
    setHour(hour);
    setMinute(minute);
  }

  public Hour() throws InvalidHourFormatException {
    Calendar calendar = Calendar.getInstance();
    setHour(calendar.get(Calendar.HOUR_OF_DAY));
    setMinute(calendar.get(Calendar.MINUTE));
  }

  public int getHour() {
    return hour;
  }

  public int getMinute() {
    return minute;
  }

  /**
   * Calculates the number of minutes since midnight.
   *
   * @return int
   */
  public int getTimeInMinutes() {
    return hour * 60 + minute;
  }

  public String toString() {
    return String.format("%02dh%02d", hour, minute);
  }

  /**
   * Compares two Hour instances. If this hour is before the other hour, returns a
   * negative number.
   * If they are the same, returns 0.
   *
   * @param other
   * @return int
   */
  public int compareTo(Hour other) {
    return this.getTimeInMinutes() - other.getTimeInMinutes();
  }

  /**
   * Checks if the given hour is between this hour and the other hour.
   *
   * @param other
   * @param hour
   * @return boolean
   */
  public boolean isBetween(Hour other, Hour hour) {
    return this.compareTo(other) >= 0 && this.compareTo(hour) <= 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;

    if (getClass() != obj.getClass())
      return false;

    Hour other = (Hour) obj;
    if (this.hour != other.hour || this.minute != other.minute)
      return false;

    return true;
  }

  @Override
  public Hour clone() {
    Hour hour = null;
    try {
      hour = new Hour(this.hour, this.minute);
    } catch (InvalidHourFormatException e) {
      e.printStackTrace();
    }
    return hour;
  }

  private void setHour(int hour) throws InvalidHourFormatException {
    if (hour < 0 || hour > 23)
      throw new InvalidHourFormatException("Hour must be between 0 and 23");
    this.hour = hour;
  }

  private void setMinute(int minute) throws InvalidHourFormatException {
    if (minute < 0 || minute > 59)
      throw new InvalidHourFormatException("Minute must be between 0 and 59");
    this.minute = minute;
  }

  /**
   * Converts a string to hour and minute. If the string is not valid, exception
   * is thrown.
   *
   * @param hour
   * @return
   * @throws InvalidHourFormatException
   */
  private int[] stringToHour(String hour) throws InvalidHourFormatException {
    if (hour == null)
      throw new InvalidHourFormatException("Invalid hour");
    if (hour.length() < 3 || hour.length() > 5)
      throw new InvalidHourFormatException("Hour must be in format HHhMM");
    if (!hour.contains("h"))
      throw new InvalidHourFormatException("Hour must be in format HHhMM");
    String[] hourParts = hour.split("h");
    int hourValue = Integer.parseInt(hourParts[0]);
    int minuteValue = Integer.parseInt(hourParts[1]);
    return new int[] { hourValue, minuteValue };
  }
}
