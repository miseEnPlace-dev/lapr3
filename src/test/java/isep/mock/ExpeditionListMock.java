package isep.mock;

import isep.model.Basket;
import isep.model.Client;
import isep.model.Enterprise;
import isep.model.ExpeditionList;
import isep.model.Producer;
import isep.model.Product;
import isep.shared.exceptions.InvalidHubException;

public class ExpeditionListMock {
  public ExpeditionList mockSimpleExpeditionList() throws InvalidHubException {
    ExpeditionList expList = new ExpeditionList(1);
    Enterprise hub = new Enterprise("id1", 0, 0, "E01");
    hub.makeHub();
    Client client = new Client("id2", 0, 0, "C01");
    Producer producer = new Producer("id3", 0, 0, "P01");
    Producer secondProducer = new Producer("id4", 0, 0, "P02");

    Basket firstBasket = new Basket(hub, client);
    firstBasket.addOrderedProduct(new Product("Apple"), 500);
    firstBasket.addOrderedProduct(new Product("Banana"), 1000);
    firstBasket.addOrderedProduct(new Product("Orange"), 10);

    Client secondClient = new Client("id2", 0, 0, "C02");
    Basket secondBasket = new Basket(hub, secondClient);
    secondBasket.addOrderedProduct(new Product("Apple"), 10000);
    secondBasket.addOrderedProduct(new Product("Banana"), 200);
    secondBasket.addOrderedProduct(new Product("Orange"), 100);

    firstBasket.addReceivedProduct(producer, new Product("Apple"), 500);
    firstBasket.addReceivedProduct(producer, new Product("Banana"), 800);
    firstBasket.addReceivedProduct(producer, new Product("Orange"), 10);

    secondBasket.addReceivedProduct(secondProducer, new Product("Apple"), 500);
    secondBasket.addReceivedProduct(producer, new Product("Banana"), 200);
    secondBasket.addReceivedProduct(producer, new Product("Orange"), 0);

    expList.addBasket(firstBasket);
    expList.addBasket(secondBasket);

    return expList;
  }
}
