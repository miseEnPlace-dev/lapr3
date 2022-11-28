package isep.model;

/**
 * Agricultural sector.
 *
 * @author Ricardo Moreira <1211285@isep.ipp.pt>
 */
public class AgriculturalParcel {
  /**
   * The name of the agricultural parcel.
   */
  String designation;

  /**
   * The area of the agricultural parcel, in hectares.
   */
  int area;

  /**
   * The cultivation of the agricultural parcel.
   */
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
