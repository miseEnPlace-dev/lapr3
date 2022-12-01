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
    if (id == null)
      throw new IllegalArgumentException("Id cannot be null");

    this.id = id;
  }

  private void validateLatitude(double latitude) {
    final int MAX_LATITUDE = 90;
    if (latitude < -MAX_LATITUDE || latitude > MAX_LATITUDE)
      throw new IllegalArgumentException("Latitude must be between -90 and 90");

    this.latitude = latitude;
  }

  private void validateLongitude(double longitude) {
    final int MAX_LONGITUDE = 180;
    if (longitude < -MAX_LONGITUDE || longitude > MAX_LONGITUDE)
      throw new IllegalArgumentException("Longitude must be between -180 and 180");

    this.longitude = longitude;
  }

  private void validateLocalizationId(String localizationId) {
    if (localizationId == null)
      throw new IllegalArgumentException("LocalizationId cannot be null");

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
   * @return double return the latitude
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * @return double return the longitude
   */
  public double getLongitude() {
    return longitude;
  }

  @Override
  public String toString() {
    return "Entity [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", localizationId="
        + localizationId;
  }
}
