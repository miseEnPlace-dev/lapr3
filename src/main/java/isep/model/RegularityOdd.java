package isep.model;

public class RegularityOdd extends Regularity {
  public RegularityOdd() {}

  @Override
  public boolean check(int day) {
    return day % 2 == 1;
  }
}
