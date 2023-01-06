package isep.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import isep.model.Company;

public class LoadBasketsControllerTest {
  private static final String BASKETS_TEST_FILEPATH = "src/test/resources/cabazesSmall.csv";
  private static LoadBasketsController ctrl;

  @BeforeAll
  public static void setup() {
    Company company = App.getInstance().getCompany();
    ctrl = new LoadBasketsController(BASKETS_TEST_FILEPATH, company);
  }

  @Test
  public void test() {

  }
}
