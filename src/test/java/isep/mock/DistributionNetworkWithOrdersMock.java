package isep.mock;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import isep.controller.LoadDistributionNetworkController;
import isep.model.DailyData;
import isep.model.DistributionNetwork;
import isep.model.Product;
import isep.model.store.EntityStore;
import isep.shared.exceptions.InvalidProductNameException;
import isep.utils.CSVReader;

public class DistributionNetworkWithOrdersMock {

  private final static String FILE_PATH = "./src/test/resources/distancesSample.csv";

  public static DistributionNetwork distributionNetworkWithOrdersMockSmall()
      throws FileNotFoundException, InvalidProductNameException {

    EntityStore store = new EntityStoreMock().mockSimpleEntityStoreSmall();

    DailyData dataProducer = new DailyData();
    DailyData dataProducer1 = new DailyData();
    DailyData dataProducer2 = new DailyData();

    Map<Product, Integer> mapProducer = new HashMap<>();
    mapProducer.put(new Product("banana"), 100);
    mapProducer.put(new Product("orange"), 200);
    mapProducer.put(new Product("lemon"), 300);

    dataProducer.addDayData(1, mapProducer);
    dataProducer.addDayData(2, mapProducer);
    dataProducer.addDayData(3, mapProducer);

    Map<Product, Integer> mapProducer1 = new HashMap<>();
    mapProducer.put(new Product("banana"), 300);
    mapProducer.put(new Product("orange"), 200);
    mapProducer.put(new Product("lemon"), 100);

    dataProducer1.addDayData(1, mapProducer1);
    dataProducer1.addDayData(2, mapProducer1);
    dataProducer1.addDayData(3, mapProducer1);

    Map<Product, Integer> mapProducer2 = new HashMap<>();
    mapProducer.put(new Product("banana"), 300);
    mapProducer.put(new Product("orange"), 200);
    mapProducer.put(new Product("lemon"), 100);

    dataProducer2.addDayData(1, mapProducer2);
    dataProducer2.addDayData(2, mapProducer2);
    dataProducer2.addDayData(3, mapProducer2);

    store.getEntityByLocalizationId("CT5").setDailyData(dataProducer);
    store.getEntityByLocalizationId("CT6").setDailyData(dataProducer1);
    store.getEntityByLocalizationId("CT8").setDailyData(dataProducer2);

    DailyData dataClient = new DailyData();
    DailyData dataClient1 = new DailyData();
    DailyData dataClient2 = new DailyData();

    Map<Product, Integer> mapClient = new HashMap<>();
    mapClient.put(new Product("banana"), 100);

    dataClient.addDayData(1, mapClient);

    Map<Product, Integer> mapClient1 = new HashMap<>();
    mapClient1.put(new Product("orange"), 200);
    mapClient1.put(new Product("lemon"), 100);

    dataClient1.addDayData(1, mapClient1);

    Map<Product, Integer> mapClient2 = new HashMap<>();
    mapClient2.put(new Product("orange"), 200);
    mapClient2.put(new Product("lemon"), 100);
    mapClient2.put(new Product("banana"), 150);

    dataClient2.addDayData(3, mapClient2);

    store.getEntityByLocalizationId("CT1").setDailyData(dataClient);
    store.getEntityByLocalizationId("CT2").setDailyData(dataClient1);
    store.getEntityByLocalizationId("CT4").setDailyData(dataClient2);

    CSVReader csvReader = new CSVReader(FILE_PATH);
    List<Map<String, String>> list = csvReader.read();

    return new LoadDistributionNetworkController(store, list).loadDistributionNetwork();
  }

}
