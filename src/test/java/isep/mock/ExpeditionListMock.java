package isep.mock;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import isep.controller.ExpeditionListController;
import isep.controller.LoadBasketsController;
import isep.controller.LoadDistributionNetworkController;
import isep.model.Basket;
import isep.model.Client;
import isep.model.Company;
import isep.model.DistributionNetwork;
import isep.model.Enterprise;
import isep.model.ExpeditionList;
import isep.model.Producer;
import isep.model.Product;
import isep.shared.exceptions.InvalidHubException;
import isep.shared.exceptions.InvalidOrderException;
import isep.shared.exceptions.UndefinedHubsException;
import isep.utils.CSVReader;

public class ExpeditionListMock {
  private static final String DISTANCES_BIG_FILE_PATH = "data/big/distancias_big.csv";
  private static final String ENTITIES_BIG_FILE_PATH = "data/big/clientes-produtores_big.csv";
  private static final String BASKETS_BIG_FILE_PATH = "data/big/cabazes_big.csv";

  private static final String DISTANCES_SMALL_FILE_PATH = "data/small/distancias_small.csv";
  private static final String ENTITIES_SMALL_FILE_PATH = "data/small/clientes-produtores_small.csv";
  private static final String BASKETS_SMALL_FILE_PATH = "data/small/cabazes_small.csv";

  public ExpeditionList mockSimpleExpeditionList() throws InvalidHubException {
    ExpeditionList expList = new ExpeditionList(1);
    Enterprise hub = new Enterprise("id1", 0, 0, "E01");
    hub.makeHub();
    Client client = new Client("id2", 0, 0, "C01");
    Producer producer = new Producer("id3", 0, 0, "P01");
    producer.addProductInfoToDayData(1, new Product("Apple"), 500.);
    producer.addProductInfoToDayData(1, new Product("Banana"), 1000.);
    producer.addProductInfoToDayData(1, new Product("Orange"), 500.);

    Producer secondProducer = new Producer("id4", 0, 0, "P02");
    secondProducer.addProductInfoToDayData(1, new Product("Apple"), 1000.);
    secondProducer.addProductInfoToDayData(1, new Product("Banana"), 200.);
    secondProducer.addProductInfoToDayData(1, new Product("Orange"), 500.);

    Basket firstBasket = new Basket(hub, client);
    firstBasket.addOrderedProduct(new Product("Apple"), 500.);
    firstBasket.addOrderedProduct(new Product("Banana"), 1000.);
    firstBasket.addOrderedProduct(new Product("Orange"), 10.);

    firstBasket.addReceivedProduct(producer, new Product("Apple"), 500.);
    firstBasket.addReceivedProduct(producer, new Product("Banana"), 800.);
    firstBasket.addReceivedProduct(producer, new Product("Orange"), 10.);

    Client secondClient = new Client("id3", 0, 0, "C02");
    Basket secondBasket = new Basket(hub, secondClient);
    secondBasket.addOrderedProduct(new Product("Apple"), 10000.);
    secondBasket.addOrderedProduct(new Product("Banana"), 200.);
    secondBasket.addOrderedProduct(new Product("Orange"), 100.);

    secondBasket.addReceivedProduct(secondProducer, new Product("Apple"), 500.);
    secondBasket.addReceivedProduct(producer, new Product("Banana"), 200.);
    secondBasket.addReceivedProduct(producer, new Product("Orange"), .0);

    expList.addBasket(firstBasket);
    expList.addBasket(secondBasket);

    return expList;
  }

  public ExpeditionList mockExpeditionListWithBigFile()
      throws FileNotFoundException, InvalidOrderException, InvalidHubException, UndefinedHubsException {
    final int DAY = 3;

    Company company = new Company();
    List<Map<String, String>> entities = new CSVReader(DISTANCES_BIG_FILE_PATH).read();
    LoadDistributionNetworkController controller = new LoadDistributionNetworkController(company.getEntityStore(),
        entities);

    DistributionNetwork network = controller.loadDistributionNetwork();
    company.setDistributionNetwork(network);

    LoadBasketsController loadBasketsController = new LoadBasketsController(BASKETS_BIG_FILE_PATH, company);
    List<Map<String, String>> data = loadBasketsController.readData();
    loadBasketsController.mapBaskets(data, company.getEntityStore());

    ExpeditionListController expeditionListController = new ExpeditionListController(company.getDistributionNetwork());
    return expeditionListController.getExpeditionList(DAY);
  }
}
