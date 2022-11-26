package isep.model;

public class Entity {
  private String id;
  private double latitude;
  private double longitude;
  private String localizationId;
  private Role role;

  public Entity(String id, double latitude, double longitude, String localizationId, Role role) {
    // TODO: verifications
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
    this.localizationId = localizationId;

    // this attribute may change to other classes extending Entity
    this.role = role;
  }

  public String getLocalizationId() {
    return localizationId;
  }

  /**
   * @return String return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return double return the latitude
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * @param latitude the latitude to set
   */
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  /**
   * @return double return the longitude
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * @param longitude the longitude to set
   */
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /**
   * @param localizationId the localizationId to set
   */
  public void setLocalizationId(String localizationId) {
    this.localizationId = localizationId;
  }

  /**
   * @return Role return the role
   */
  public Role getRole() {
    return role;
  }

  /**
   * @param role the role to set
   */
  public void setRole(Role role) {
    this.role = role;
  }

  public <E extends Comparable<E>> Integer compare(Entity o1, Entity o2) {
    return o1.getId().compareTo(o2.getId());
  }

  @Override
  public String toString() {
    return "Entity [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", localizationId="
        + localizationId + ", role=" + role + "]";
  }

}
