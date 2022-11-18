package isep.mock;

import java.io.FileNotFoundException;

import isep.model.Role;
import isep.model.store.EntityStore;
import isep.utils.CSVReader;

public class EntityStoreMock {
  private final String MOCK_FILE_PATH = "src/test/resources/entitiesSample.csv";

  public EntityStore mockEntityStoreFromSampleFile() throws FileNotFoundException {
    CSVReader reader = new CSVReader(MOCK_FILE_PATH);
    EntityStore store = new EntityStore();
    store.addEntitiesFromList(reader.read());

    return store;
  }

  public EntityStore mockSimpleEntityStore() {
    EntityStore store = new EntityStore();

    store.addEntity("C1", 10.0, 0.0, "CT1", Role.CLIENT);
    store.addEntity("C2", 20.0, 0.0, "CT2", Role.CLIENT);
    store.addEntity("C3", 30.0, 10.0, "CT4", Role.CLIENT);
    store.addEntity("E1", 20.0, 10.0, "CT3", Role.ENTERPRISE);
    store.addEntity("P1", 10.0, 10.0, "CT5", Role.PRODUCER);

    return store;
  }
}
