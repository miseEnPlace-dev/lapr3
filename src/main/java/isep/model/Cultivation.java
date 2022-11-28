package isep.model;

/**
 * Cultivation class.
 *
 * @author Ricardo Moreira <1211285@isep.ipp.pt>
 */
public class Cultivation {
  /**
   * The name of the cultivation.
   */
  private String designation;

  /**
   * The type (temporary or permanent) of the cultivation.
   */
  private boolean isPermanent;

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
