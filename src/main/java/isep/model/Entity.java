package isep.model;

public abstract class Entity {
  private String id;
  private double latitude;
  private double longitude;
  private String localizationId;

  public Entity(String id, double latitude, double longitude, String localizationId) {
    validateId(id);
    validateLatitude(latitude);
    validateLongitude(longitude);
    validateLocalizationId(localizationId);

  }

  private void validateId(String id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    this.id = id;
  }

  private void validateLatitude(double latitude) {
    if (latitude < -90 || latitude > 90) {
      throw new IllegalArgumentException("Latitude must be between -90 and 90");
    }
    this.latitude = latitude;
  }

  private void validateLongitude(double longitude) {
    if (longitude < -180 || longitude > 180) {
      throw new IllegalArgumentException("Longitude must be between -180 and 180");
    }
    this.longitude = longitude;
  }

  private void validateLocalizationId(String localizationId) {
    if (localizationId == null) {
      throw new IllegalArgumentException("LocalizationId cannot be null");
    }
    this.localizationId = localizationId;
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

  @Override
  public String toString() {
    return "Entity [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", localizationId="
        + localizationId;
  }

}
