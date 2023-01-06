package isep.model.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import isep.model.Entity;
import isep.model.Product;
import isep.model.store.EntityStore;
import isep.utils.Field;

/**
 * This class takes a list and outputs the baskets data.
 */
public class BasketsMapper {
  private BasketsMapper() {
  }

  public static int toPlan(List<Map<String, String>> data, EntityStore entityStore) {
    // map has a variable number of products
    int count = 0;

    for (Map<String, String> map : data) {
      String entityId = map.get(Field.CLIENTPROD.name);
      int day = Integer.parseInt(map.get(Field.DAY.name));

      Entity foundEntity = entityStore.getEntityById(entityId);
      if (foundEntity == null) {
        System.out.println("Entity " + entityId + " not found.");
        continue;
      }

      Map<Product, Double> thisDayData = new HashMap<>();
      int i = 1;

      while (map.get(Field.PRODUCT.name + i) != null) {
        String productName = map.get(Field.PRODUCT.name + i);
        Double quantity = Double.parseDouble(map.get(Field.PRODUCT.name + i));

        try {
          Product product = new Product(productName);
          thisDayData.put(product, quantity);
        } catch (IllegalArgumentException e) {
          System.out.println("Invalid product name: " + productName);
        }
        i++;
      }
      foundEntity.getDailyData().addDayData(day, thisDayData);
      count++;
    }

    return count;
  }
}
