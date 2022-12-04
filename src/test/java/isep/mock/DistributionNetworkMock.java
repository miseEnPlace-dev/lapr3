package isep.mock;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import isep.controller.LoadDistributionNetworkController;
import isep.model.DistributionNetwork;
import isep.model.store.EntityStore;

public class DistributionNetworkMock {
  public DistributionNetwork mockEntityStoreWithBigFile() throws FileNotFoundException {
    EntityStore entity = new EntityStoreMock().mockEntityStoreWithBigFile();
    List<Map<String, String>> distances = new DistancesDataMock().mockDistancesDataWithBigFile();

    return new LoadDistributionNetworkController(entity, distances).loadDistributionNetwork();
  }
}
