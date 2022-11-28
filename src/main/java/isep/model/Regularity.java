package isep.model;

public abstract class Regularity {
  public abstract boolean check(int day);

  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;

    if (obj.getClass().getName().equals(this.getClass().getName()))
      return true;

    return false;
  }
}
