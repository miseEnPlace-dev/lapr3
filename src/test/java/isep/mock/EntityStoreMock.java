package isep.mock;

import java.io.FileNotFoundException;
import isep.model.Role;
import isep.model.store.EntityStore;
import isep.utils.CSVReader;

public class EntityStoreMock {
  private final String MOCK_FILE_PATH = "src/test/resources/entitiesSample.csv";
  private final String BIG_FILE_PATH = "data/big/clientes-produtores_big.csv";
  private final String SMALL_FILE_PATH = "data/small/clientes-produtores_small.csv";

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
    store.addEntity("P2", 11, 20, "CT6", Role.PRODUCER);
    store.addEntity("C4", 30, 10, "CT7", Role.CLIENT);
    store.addEntity("E2", 20, 20, "CT8", Role.ENTERPRISE);
    store.addEntity("C5", 40, 10, "CT9", Role.CLIENT);
    store.addEntity("C6", 50, 10, "CT10", Role.CLIENT);
    store.addEntity("C7", 60, 10, "CT11", Role.CLIENT);
    store.addEntity("C8", 70, 10, "CT12", Role.CLIENT);
    store.addEntity("C9", 80, 10, "CT13", Role.CLIENT);
    store.addEntity("C10", 80, 10, "CT14", Role.CLIENT);
    store.addEntity("C11", 80, 10, "CT15", Role.CLIENT);
    store.addEntity("C12", 80, 10, "CT16", Role.CLIENT);
    store.addEntity("C13", 80, 10, "CT17", Role.CLIENT);

    return store;
  }

  public EntityStore mockEntityStoreWithBigFile() throws FileNotFoundException {
    CSVReader reader = new CSVReader(BIG_FILE_PATH);
    EntityStore store = new EntityStore();
    store.addEntitiesFromList(reader.read());

    return store;
  }

  public EntityStore mockEntityStoreWithSmallFile() throws FileNotFoundException {
    CSVReader reader = new CSVReader(SMALL_FILE_PATH);
    EntityStore store = new EntityStore();
    store.addEntitiesFromList(reader.read());

    return store;
  }

  public EntityStore mockSimpleEntityStoreSmall() {
    EntityStore store = new EntityStore();

    store.addEntity("C1", 10.0, 0.0, "CT1", Role.CLIENT);
    store.addEntity("C2", 20.0, 0.0, "CT2", Role.CLIENT);
    store.addEntity("C3", 30.0, 10.0, "CT4", Role.CLIENT);
    store.addEntity("E1", 20.0, 10.0, "CT3", Role.ENTERPRISE);
    store.addEntity("P1", 10.0, 10.0, "CT5", Role.PRODUCER);
    store.addEntity("P2", 11.0, 20.0, "CT6", Role.PRODUCER);
    store.addEntity("C4", 30.0, 10.0, "CT7", Role.CLIENT);
    store.addEntity("P3", 10.0, 10.0, "CT8", Role.PRODUCER);
    store.addEntity("E2", 10.0, -20.0, "CT9", Role.ENTERPRISE);

    return store;
  }

}
