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
}
