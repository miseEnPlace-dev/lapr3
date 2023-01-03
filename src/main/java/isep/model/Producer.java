package isep.model;

public class Producer extends Entity implements Comparable<Object> {
  public Producer(String id, double latitude, double longitude, String localizationId) {
    super(id, latitude, longitude, localizationId);
  }

  public DailyData getStockUntilDate(Integer day) {
    return this.dailyData.getDailyDataUntilDate(day);
  }

  public int getNonExpiredQuantityUntilDate(Product product, int day) {
    return this.dailyData.getNonExpiredProductQuantity(product, day);
  }

  @Override
  public int compareTo(Object o) {
    return this.getId().compareTo(((Producer) o).getId());
  }
}
