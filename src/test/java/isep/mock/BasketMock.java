package isep.mock;

import isep.model.Basket;
import isep.model.Client;
import isep.model.Enterprise;
import isep.model.Producer;
import isep.model.Product;
import isep.shared.exceptions.InvalidHubException;

public class BasketMock {
  public Basket mockFullyFulfilledWithOneProducerBasket() throws InvalidHubException {
    Enterprise hub = new Enterprise("id", 0, 0, "loc");
    hub.makeHub();
    Client client = new Client("id1", 0, 0, "loc1");
    Producer producer = new Producer("id2", 0, 0, "loc2");

    Basket basket = new Basket(hub, client);
    Product product1 = new Product("Apples");
    Product product2 = new Product("Bananas");
    Product product3 = new Product("Oranges");

    basket.addOrderedProduct(product1, 100);
    basket.addOrderedProduct(product2, 100);
    basket.addOrderedProduct(product3, 100);

    basket.addReceivedProduct(producer, product1, 100);
    basket.addReceivedProduct(producer, product2, 100);
    basket.addReceivedProduct(producer, product3, 100);

    return basket;
  }

  public Basket mockFullyFulfilledWithTwoProducersBasket() throws InvalidHubException {
    Enterprise hub = new Enterprise("id", 0, 0, "loc");
    hub.makeHub();
    Client client = new Client("id1", 0, 0, "loc1");
    Producer producer1 = new Producer("id2", 0, 0, "loc2");
    Producer producer2 = new Producer("id3", 0, 0, "loc3");

    Basket basket = new Basket(hub, client);
    Product product1 = new Product("Apples");
    Product product2 = new Product("Bananas");
    Product product3 = new Product("Oranges");

    basket.addOrderedProduct(product1, 100);
    basket.addOrderedProduct(product2, 100);
    basket.addOrderedProduct(product3, 100);

    basket.addReceivedProduct(producer1, product1, 100);
    basket.addReceivedProduct(producer1, product2, 100);
    basket.addReceivedProduct(producer2, product3, 100);

    return basket;
  }

  public Basket mockPartiallyFulfilledBasket() throws InvalidHubException {
    Enterprise hub = new Enterprise("id", 0, 0, "loc");
    hub.makeHub();
    Client client = new Client("id1", 0, 0, "loc1");
    Producer producer = new Producer("id2", 0, 0, "loc2");

    Basket basket = new Basket(hub, client);
    Product product1 = new Product("Apples");
    Product product2 = new Product("Bananas");
    Product product3 = new Product("Oranges");

    basket.addOrderedProduct(product1, 100);
    basket.addOrderedProduct(product2, 100);
    basket.addOrderedProduct(product3, 100);

    basket.addReceivedProduct(producer, product1, 100);
    basket.addReceivedProduct(producer, product2, 100);

    return basket;
  }
}
