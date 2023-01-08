package isep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import isep.mock.DistancesDataMock;
import isep.mock.EntityStoreMock;
import isep.model.DistributionNetwork;
import isep.model.Enterprise;
import isep.model.store.EntityStore;
import isep.shared.exceptions.InvalidNumberOfHubsException;

public class DefineHubsControllerTest {

  @Test
  public void testDefineHubsWithSmallTestFile() throws FileNotFoundException, InvalidNumberOfHubsException {
    EntityStore entity = new EntityStoreMock().mockEntityStoreWithSmallFile();
    List<Map<String, String>> distances = new DistancesDataMock().mockDistancesDataWithSmallFile();

    DistributionNetwork network = new LoadDistributionNetworkController(entity, distances).loadDistributionNetwork();

    DefineHubsController ctrl = new DefineHubsController(network);

    List<Enterprise> actual = ctrl.defineHubs(3);
    List<Enterprise> expected = new ArrayList<>();
    expected.add((Enterprise) entity.getEntityByLocalizationId("CT5"));
    expected.add((Enterprise) entity.getEntityByLocalizationId("CT9"));
    expected.add((Enterprise) entity.getEntityByLocalizationId("CT11"));
    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
    assertEquals(expected.get(1), actual.get(1));
    assertEquals(expected.get(2), actual.get(2));
  }

  @Test
  public void testDefineHubsWithBigTestFile() throws FileNotFoundException, InvalidNumberOfHubsException {
    EntityStore entity = new EntityStoreMock().mockEntityStoreWithBigFile();
    List<Map<String, String>> distances = new DistancesDataMock().mockDistancesDataWithBigFile();

    DistributionNetwork network = new LoadDistributionNetworkController(entity, distances).loadDistributionNetwork();

    DefineHubsController ctrl = new DefineHubsController(network);

    List<Enterprise> actual = ctrl.defineHubs(2);
    List<Enterprise> expected = new ArrayList<>();
    expected.add((Enterprise) entity.getEntityByLocalizationId("CT146"));
    expected.add((Enterprise) entity.getEntityByLocalizationId("CT142"));
    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
    assertEquals(expected.get(1), actual.get(1));
  }
}
