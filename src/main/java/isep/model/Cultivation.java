package isep.model;

public class Cultivation {
  String designation;
  boolean isPermanent;

  public Cultivation(String designation, boolean isPermanent) {
    this.designation = designation;
    this.isPermanent = isPermanent;
  }

  public String getDesignation() {
    return designation;
  }

  public boolean isPermanent() {
    return isPermanent;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (!(obj instanceof Cultivation))
      return false;

    Cultivation other = (Cultivation) obj;

    if (this.designation.equals(other.designation))
      return true;
    return false;
  }
}
