package isep.mock;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import isep.controller.LoadDistributionNetworkController;
import isep.model.DistributionNetwork;
import isep.model.store.EntityStore;

public class DistributionNetworkMock {
  public static DistributionNetwork distributionNetworkMockWithBigFile() throws FileNotFoundException {
    EntityStore entity = new EntityStoreMock().mockEntityStoreWithBigFile();
    List<Map<String, String>> distances = new DistancesDataMock().mockDistancesDataWithBigFile();

    return new LoadDistributionNetworkController(entity, distances).loadDistributionNetwork();
  }

  public static DistributionNetwork distributionNetworkMockWithSmallFile() throws FileNotFoundException {
    EntityStore entity = new EntityStoreMock().mockEntityStoreWithSmallFile();
    List<Map<String, String>> distances = new DistancesDataMock().mockDistancesDataWithSmallFile();

    return new LoadDistributionNetworkController(entity, distances).loadDistributionNetwork();
  }
}
