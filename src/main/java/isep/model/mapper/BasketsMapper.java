package isep.model.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import isep.utils.Field;

/**
 * This class takes a list and outputs the baskets data.
 */
public class BasketsMapper {
  private BasketsMapper() {
  }

  public static Map<Integer, Map<String, Map<Integer, Double>>> toPlan(List<Map<String, String>> data) {
    // map has a variable number of products

    Map<Integer, Map<String, Map<Integer, Double>>> baskets = new HashMap<>();

    for (Map<String, String> map : data) {
      String clientprod = map.get(Field.CLIENTPROD.name);
      int day = Integer.parseInt(map.get(Field.DAY.name));

      if (baskets.get(day) != null)
        baskets.put(day, new HashMap<>());
      if (baskets.get(day).get(clientprod) != null)
        baskets.get(day).put(clientprod, new HashMap<>());

      int i = 1;
      while (map.get(Field.PRODUCT.name + i) != null) {
        String qtyStr = map.get(Field.PRODUCT.name + i);
        Double qty = Double.parseDouble(qtyStr);
        baskets.get(day).get(clientprod).put(i, qty);
        i++;
      }
    }

    return baskets;
  }
}
