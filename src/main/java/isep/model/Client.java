package isep.model;

public class Client extends Entity {
  private Enterprise nearestHub;

  /*
   * Constructor
   */
  public Client(String id, double latitude, double longitude, String localizationId) {
    super(id, latitude, longitude, localizationId);
    nearestHub = null;
  }

  public Enterprise getNearestHub() {
    return nearestHub;
  }

  public void setNearestHub(Enterprise nearestHub) {
    this.nearestHub = nearestHub;
  }
}
