package isep.model;

public class RegularityEven extends Regularity {
  public RegularityEven() {}

  @Override
  public boolean check(int day) {
    return day % 2 == 0;
  }
}
