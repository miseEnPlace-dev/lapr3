package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import isep.mock.EntityStoreMock;
import isep.model.Company;
import isep.model.store.EntityStore;

public class LoadBasketsControllerTest {
  private static final String BASKETS_TEST_FILEPATH = "src/test/resources/cabazesSmall.csv";
  private static LoadBasketsController ctrl;

  @BeforeAll
  public static void setup() {
    Company company = App.getInstance().getCompany();
    ctrl = new LoadBasketsController(BASKETS_TEST_FILEPATH, company);
  }

  @Test
  public void testWrongFile() {
    ctrl.setFilename("wrong.csv");
    assertThrows(FileNotFoundException.class, () -> {
      ctrl.readData();
    });
  }

  @Test
  public void testMapBaskets() throws FileNotFoundException {
    EntityStore store = new EntityStoreMock().mockEntityStoreWithSmallFile();
    ctrl.setFilename(BASKETS_TEST_FILEPATH);
    List<Map<String, String>> data = ctrl.readData();
    int count = ctrl.mapBaskets(data, store);
    assertEquals(62, count);
  }
}
