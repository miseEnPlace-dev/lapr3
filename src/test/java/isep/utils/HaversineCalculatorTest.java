package isep.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for HaversineCalculator class
 * 
 * All expected values were calculated using https://www.vcalc.com/
 */
public class HaversineCalculatorTest {
  @Test
  public void testWithEqualCoordinates() {
    double lat1 = 45.2562;
    double lon1 = -8.1234;
    double lat2 = 45.2562;
    double lon2 = -8.1234;

    double expected = 0;

    assertEquals(expected, HaversineCalculator.getDistanceBetweenTwoCoordinates(lat1, lon1, lat2, lon2));
  }

  @Test
  public void testWithOportoLisbonCoordinates() {
    double lat1 = 41.1579;
    double lon1 = -8.6291;
    double lat2 = 38.7222;
    double lon2 = -9.1393;

    double expected = 274.31;
    double actual = Math.round(HaversineCalculator.getDistanceBetweenTwoCoordinates(lat1, lon1, lat2, lon2) * 100.0)
        / 100.0;

    assertEquals(expected, actual);
  }

  @Test
  public void testWithOportoParisCoordinates() {
    double lat1 = 41.1579;
    double lon1 = -8.6291;
    double lat2 = 48.8566;
    double lon2 = 2.3522;

    double expected = 1213.47;
    double actual = Math.round(HaversineCalculator.getDistanceBetweenTwoCoordinates(lat1, lon1, lat2, lon2) * 100.0)
        / 100.0;

    assertEquals(expected, actual);
  }

  /**
   * Coordinates of CT32 and CT160 were taken from the big data file at
   * data\Big\clientes-produtores_big.csv
   */
  @Test
  public void testWithCT32AndCT160() {
    double lat1 = 40.4333;
    double lon1 = -8.4333;
    double lat2 = 40.3781;
    double lon2 = -8.4514;

    double expected = 6.33;
    double actual = Math.round(HaversineCalculator.getDistanceBetweenTwoCoordinates(lat1, lon1, lat2, lon2) * 100.0)
        / 100.0;

    assertEquals(expected, actual);
  }
}
