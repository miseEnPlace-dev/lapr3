package isep.model;

public class AgriculturalParcel {
  String designation;
  int area; // in hectares
  Cultivation cultivation;

  public AgriculturalParcel(String designation, int area, Cultivation cultivation) {
    this.designation = designation;
    this.area = area;
    this.cultivation = cultivation;
  }

  public String getDesignation() {
    return designation;
  }

  public int getArea() {
    return area;
  }

  public Cultivation getCultivation() {
    return cultivation;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (!(obj instanceof AgriculturalParcel))
      return false;

    AgriculturalParcel other = (AgriculturalParcel) obj;

    if (this.designation.equals(other.designation))
      return true;
    return false;
  }
}
