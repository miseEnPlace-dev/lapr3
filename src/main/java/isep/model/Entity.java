package isep.model;

import java.util.Map;

import isep.utils.HaversineCalculator;

/*
 * Abstract class that represents an entity in the network
 *
 */
public abstract class Entity implements VertexHeuristic<Entity> {
  private String id;
  private double latitude;
  private double longitude;
  private String localizationId;
  protected DailyData dailyData;

  /*
   * Constructor
   */
  public Entity(String id, double latitude, double longitude, String localizationId) {
    validateId(id);
    validateLatitude(latitude);
    validateLongitude(longitude);
    validateLocalizationId(localizationId);
    this.dailyData = new DailyData();
  }

  /**
   * Calculates the distance between two entities using the Haversine formula This
   * method is used in
   * the A* algorithm
   *
   * @param target The entity to which the distance will be calculated
   * @return The distance between the two entities, in meters
   */
  @Override
  public int getHeuristicValue(Entity target) {
    return (int) (HaversineCalculator.getDistanceBetweenTwoCoordinates(this.latitude,
        this.longitude, target.latitude, target.longitude) * 1000);
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
    return "Entity [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude
        + ", localizationId=" + localizationId;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (!(obj instanceof Entity))
      return false;

    Entity entity = (Entity) obj;
    return this.id.equals(entity.id);
  }

  public void addDayData(Integer day, Map<Product, Integer> products) {
    this.dailyData.addDayData(day, products);
  }

  public void addProductInfoToDayData(Integer day, Product product, Integer quantity) {
    this.dailyData.addProductInfoToDayData(day, product, quantity);
  }

  public Integer getQuantityOfProductForDay(Integer day, Product product) {
    return this.dailyData.getQuantityOfProductForDay(day, product);
  }

  public void setDailyData(DailyData dailyData) {
    if (dailyData == null)
      throw new IllegalArgumentException("Daily Data cannot be null!");

    this.dailyData = dailyData;
  }

  public DailyData getDailyData() {
    return this.dailyData;
  }

  public void setDayData(Integer day, Map<Product, Integer> dayData) {
    if (day <= 0)
      throw new IllegalArgumentException("Day must be a positive number!");
    if (dayData == null)
      throw new IllegalArgumentException("Day data cannot be null!");

    this.dailyData.addDayData(day, dayData);
  }

  public Map<Product, Integer> getDayData(Integer day) {
    return this.dailyData.getDayData(day);
  }
}
