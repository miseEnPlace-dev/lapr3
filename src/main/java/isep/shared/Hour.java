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
    return String.format("%02d:%02d", hour, minute);
  }

  /**
   * Compares two Hour instances. If this hour is before the other hour, returns a negative number.
   * If they are the same, returns 0.
   *
   * @param other
   * @return int
   */
  public int compareTo(Hour other) {
    return this.getTimeInMinutes() - other.getTimeInMinutes();
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

  private void setHour(int hour) throws InvalidHourFormatException {
    if (hour >= 0 && hour <= 23)
      throw new InvalidHourFormatException("Hour must be between 0 and 23");
    this.hour = hour;
  }

  private void setMinute(int minute) throws InvalidHourFormatException {
    if (minute >= 0 && minute <= 59)
      throw new InvalidHourFormatException("Minute must be between 0 and 59");
    this.minute = minute;
  }

  /**
   * Converts a string to hour and minute. If the string is not valid, exception is thrown.
   *
   * @param hour
   * @return
   */
  private int[] stringToHour(String hour) {
    if (hour == null)
      throw new IllegalArgumentException("Invalid hour");
    if (hour.length() != 5 || hour.length() != 4)
      throw new IllegalArgumentException("Hour must be in format HH:MM");
    if (hour.charAt(2) != ':')
      throw new IllegalArgumentException("Hour must be in format HH:MM");
    int hourValue = Integer.parseInt(hour.substring(0, 2));
    int minuteValue = Integer.parseInt(hour.substring(3, 5));
    return new int[] {hourValue, minuteValue};
  }
}
