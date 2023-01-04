package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import isep.shared.exceptions.InvalidNumberOfHubsException;
import isep.utils.graph.AdjacencyMapGraph;
import isep.utils.graph.Graph;

public class DistributionNetworkTest {
  @Test
  public void testAddRelation() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    assertEquals(distance, network.getDistanceBetweenConnectedEntities(e1, e2));
  }

  @Test
  public void testAddDuplicatedRelation() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    assertEquals(distance, network.getDistanceBetweenConnectedEntities(e1, e2));

    assertFalse(network.addRelation(e1, e2, 20));
    assertEquals(distance, network.getDistanceBetweenConnectedEntities(e1, e2));
  }

  @Test
  public void testDuplicatedRelationWithDifferentOrder() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    assertEquals(distance, network.getDistanceBetweenConnectedEntities(e1, e2));

    assertFalse(network.addRelation(e2, e1, 20));
    assertEquals(distance, network.getDistanceBetweenConnectedEntities(e1, e2));
  }

  @Test
  public void testGetDistanceBetween() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    assertEquals(network.getDistanceBetweenConnectedEntities(e1, e2), distance);
  }

  @Test
  public void testGetDistanceBetweenWithInvalidNodes() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");

    assertNull(network.getDistanceBetweenConnectedEntities(e1, e2));
  }

  @Test
  public void testGetDistanceBetweenWithNullNodes() {
    DistributionNetwork network = new DistributionNetwork();
    assertNull(network.getDistanceBetweenConnectedEntities(null, null));
  }

  @Test
  public void testGetDistanceBetweenWithNullFirstNode() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);

    assertNull(network.getDistanceBetweenConnectedEntities(null, e2));
  }

  @Test
  public void testGetDistanceBetweenWithNullSecondNode() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);

    assertNull(network.getDistanceBetweenConnectedEntities(e1, null));
  }

  @Test
  public void testGetNumberOfEntities() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Entity e3 = new Enterprise("e3", 3, 3, "l3");

    network.addRelation(e1, e2, 10);

    assertEquals(2, network.getNumberOfEntities());
    network.addRelation(e2, e1, 20);
    assertEquals(2, network.getNumberOfEntities());

    network.addRelation(e2, e3, 10);
    assertEquals(3, network.getNumberOfEntities());
    network.addRelation(e1, e3, 10);
    assertEquals(3, network.getNumberOfEntities());
  }

  @Test
  public void testGetNumberOfEdges() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Entity e3 = new Enterprise("e3", 3, 3, "l3");

    network.addRelation(e1, e2, 10);

    assertEquals(1, network.getNumberOfRelations());
    network.addRelation(e2, e1, 20);
    assertEquals(1, network.getNumberOfRelations());

    network.addRelation(e2, e3, 10);
    assertEquals(2, network.getNumberOfRelations());
    network.addRelation(e1, e3, 10);
    assertEquals(3, network.getNumberOfRelations());
  }

  public void testGetEnterprises() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Producer e2 = new Producer("e2", 2, 2, "l2");
    Client e3 = new Client("e3", 3, 3, "l3");
    Enterprise e4 = new Enterprise("e4", 4, 4, "l4");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    network.addRelation(e3, e4, distance);

    List<Enterprise> expected = new ArrayList<>();
    expected.add(e1);
    expected.add(e4);

    List<Enterprise> actual = network.getEnterprises();

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
    assertEquals(expected.get(1), actual.get(1));
  }

  @Test
  public void testGetEnterprisesForEmptyNetwork() {
    DistributionNetwork network = new DistributionNetwork();

    assertEquals(network.getEnterprises(), new ArrayList<>());
  }

  @Test
  public void testGetEntities() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Producer e2 = new Producer("e2", 2, 2, "l2");
    Client e3 = new Client("e3", 3, 3, "l3");
    Enterprise e4 = new Enterprise("e4", 4, 4, "l4");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    network.addRelation(e3, e4, distance);

    List<Entity> expected = new ArrayList<>();
    expected.add(e1);
    expected.add(e2);
    expected.add(e3);
    expected.add(e4);

    List<Entity> actual = network.getEntities();

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.contains(e1));
    assertTrue(actual.contains(e2));
    assertTrue(actual.contains(e3));
    assertTrue(actual.contains(e4));
  }

  @Test
  public void testGetEntitiesForEmptyNetwork() {
    DistributionNetwork network = new DistributionNetwork();

    assertEquals(network.getEntities(), new ArrayList<>());
  }

  @Test
  public void testShortestPathDistance() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Enterprise("e1", 1, 1, "l1");
    Entity e2 = new Producer("e2", 2, 2, "l2");
    Entity e3 = new Client("e3", 3, 3, "l3");
    network.addRelation(e1, e2, 100);
    network.addRelation(e2, e3, 200);
    network.addRelation(e1, e3, 350);

    assertEquals(300, network.shortestPathDistance(e1, e3));
  }

  @Test
  public void testShortestPathDistanceBetweenSameEntities() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Enterprise("e1", 1, 1, "l1");
    Entity e2 = new Producer("e2", 2, 2, "l2");
    Entity e3 = new Client("e3", 3, 3, "l3");
    network.addRelation(e1, e2, 100);
    network.addRelation(e2, e3, 200);
    network.addRelation(e1, e3, 350);

    assertEquals(0, network.shortestPathDistance(e1, e1));
  }

  @Test
  public void testDefineHubsWorks() throws InvalidNumberOfHubsException {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Producer p1 = new Producer("p1", 2, 2, "l2");
    Client c1 = new Client("c1", 3, 3, "l3");
    Enterprise e2 = new Enterprise("e2", 4, 4, "l4");
    Enterprise e3 = new Enterprise("e3", 5, 5, "l5");
    network.addRelation(e1, p1, 100);
    network.addRelation(c1, e2, 200);
    network.addRelation(c1, p1, 100);
    network.addRelation(c1, e3, 50);

    List<Enterprise> actual = network.defineHubs(2);

    List<Enterprise> expected = new ArrayList<>();
    expected.add(e3);
    expected.add(e1);

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
    assertEquals(expected.get(1), actual.get(1));
    assertTrue(e3.isHub());
    assertTrue(e1.isHub());
    assertFalse(e2.isHub());
  }

  @Test
  public void testDefineHubsForInvalidN() {
    DistributionNetwork network = new DistributionNetwork();
    assertThrows(InvalidNumberOfHubsException.class, () -> network.defineHubs(0));
  }

  @Test
  public void testDefineHubsForNonConnectedNetwork() throws InvalidNumberOfHubsException {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Producer e2 = new Producer("e2", 2, 2, "l2");
    Client e3 = new Client("e3", 3, 3, "l3");
    Enterprise e4 = new Enterprise("e4", 4, 4, "l4");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    network.addRelation(e3, e4, distance);

    assertNull(network.defineHubs(2));
  }

  @Test
  public void testDefineHubsForLowerNumberOfEnterprises() throws InvalidNumberOfHubsException {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Producer p1 = new Producer("p1", 2, 2, "l2");
    network.addRelation(e1, p1, 100);

    List<Enterprise> expected = new ArrayList<>();
    expected.add(e1);

    List<Enterprise> actual = network.defineHubs(2);

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
    assertTrue(e1.isHub());
  }

  @Test
  public void testAverageMinPath() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Producer p1 = new Producer("p1", 2, 2, "l2");
    Producer p2 = new Producer("p2", 2, 2, "l2");
    network.addRelation(e1, p1, 100);
    network.addRelation(e1, p2, 300);

    assertEquals(400 / 3, network.getAveragePathDistanceBetweenGroupOfEntities(e1));
  }

  @Test
  public void testIsConnectedForConnectedGraph() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Producer p1 = new Producer("p1", 2, 2, "l2");
    Producer p2 = new Producer("p2", 2, 2, "l2");
    network.addRelation(e1, p1, 100);
    network.addRelation(e1, p2, 300);

    assertEquals(true, network.isConnected());
  }

  @Test
  public void testIsConnectedForNonConnectedGraph() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Producer p1 = new Producer("p1", 2, 2, "l2");
    Producer p2 = new Producer("p2", 2, 2, "l2");
    Producer p3 = new Producer("p3", 3, 3, "l3");
    network.addRelation(e1, p1, 100);
    network.addRelation(p2, p3, 100);

    assertEquals(false, network.isConnected());
  }

  @Test
  public void testGetHubs() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    e1.makeHub();
    Enterprise e2 = new Enterprise("e2", 2, 2, "l2");
    Enterprise e3 = new Enterprise("e3", 3, 3, "l3");
    e3.makeHub();

    network.addRelation(e1, e2, 100);
    network.addRelation(e3, e2, 300);

    List<Enterprise> expected = new ArrayList<>();
    expected.add(e1);
    expected.add(e3);

    List<Enterprise> actual = network.getHubs();

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
    assertEquals(expected.get(1), actual.get(1));
  }

  @Test
  public void testGetHubsWithNoHubs() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Enterprise e2 = new Enterprise("e2", 2, 2, "l2");
    Enterprise e3 = new Enterprise("e3", 3, 3, "l3");

    network.addRelation(e1, e2, 100);
    network.addRelation(e3, e2, 300);

    assertEquals(0, network.getHubs().size());
  }

  @Test
  public void testGetHubsEmptyNetwork() {
    assertEquals(0, new DistributionNetwork().getHubs().size());
  }

  @Test
  public void testGetClients() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Client e2 = new Client("e2", 2, 2, "l2");
    Enterprise e3 = new Enterprise("e3", 3, 3, "l3");
    Client e4 = new Client("e4", 4, 4, "l4");

    network.addRelation(e1, e2, 100);
    network.addRelation(e3, e4, 300);

    List<Client> expected = new ArrayList<>();
    expected.add(e2);
    expected.add(e4);

    List<Client> actual = network.getClients();

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
    assertEquals(expected.get(1), actual.get(1));
  }

  @Test
  public void testGetClientsWithNoClients() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Enterprise e2 = new Enterprise("e2", 2, 2, "l2");
    Enterprise e3 = new Enterprise("e3", 3, 3, "l3");

    network.addRelation(e1, e2, 100);
    network.addRelation(e3, e2, 300);

    assertEquals(0, network.getClients().size());
  }

  @Test
  public void testGetClientsEmptyNetwork() {
    assertEquals(0, new DistributionNetwork().getClients().size());
  }

  @Test
  public void testGetMinimumShortestPathNetwork() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Enterprise e2 = new Enterprise("e2", 2, 2, "l2");
    Enterprise e3 = new Enterprise("e3", 3, 3, "l3");
    Enterprise e4 = new Enterprise("e4", 4, 4, "l4");
    Enterprise e5 = new Enterprise("e5", 5, 5, "l5");
    Enterprise e6 = new Enterprise("e6", 6, 6, "l6");

    network.addRelation(e1, e2, 100);
    network.addRelation(e3, e2, 300);
    network.addRelation(e3, e4, 200);
    network.addRelation(e1, e5, 500);
    network.addRelation(e2, e6, 600);
    network.addRelation(e6, e3, 400);

    Graph<Entity, Integer> expected = new AdjacencyMapGraph<>(false);
    expected.addEdge(e1, e2, 100);
    expected.addEdge(e3, e4, 200);
    expected.addEdge(e3, e2, 300);
    expected.addEdge(e6, e3, 400);
    expected.addEdge(e1, e5, 500);

    Graph<Entity, Integer> actual = network.getMinimumShortestPathNetwork();

    assertEquals(expected.edges().size(), actual.edges().size());
    assertTrue(expected.edges().containsAll(actual.edges()));
  }

  @Test
  public void testGetNNearestProducers() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Client c1 = new Client("c1", 2, 2, "l2");
    Producer p1 = new Producer("p1", 3, 3, "l3");
    Producer p2 = new Producer("p2", 4, 4, "l4");
    Producer p3 = new Producer("p3", 5, 5, "l5");

    network.addRelation(e1, c1, 100);
    network.addRelation(e1, p1, 50);
    network.addRelation(e1, p2, 200);
    network.addRelation(e1, p3, 150);

    List<Producer> expected = new ArrayList<>();
    expected.add(p1);

    List<Producer> actual = network.getNNearestProducers(e1, 1);

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));

  }

  @Test
  public void testGetNNearestProducersWithNoProducers() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Client c1 = new Client("c1", 2, 2, "l2");

    network.addRelation(e1, c1, 100);

    assertNull(network.getNNearestProducers(e1, 1));
  }

  @Test
  public void testGetNNearestProducersEmptyNetwork() {
    assertNull(new DistributionNetwork().getNNearestProducers(new Enterprise("e1", 1, 1, "l1"), 1));
  }

  @Test
  public void testGetNNearestProducersWithMoreProducersThanN() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Client c1 = new Client("c1", 2, 2, "l2");
    Producer p1 = new Producer("p1", 3, 3, "l3");
    Producer p2 = new Producer("p2", 4, 4, "l4");
    Producer p3 = new Producer("p3", 5, 5, "l5");

    network.addRelation(e1, c1, 100);
    network.addRelation(e1, p1, 50);
    network.addRelation(e1, p2, 200);
    network.addRelation(e1, p3, 150);

    List<Producer> expected = new ArrayList<>();
    expected.add(p1);
    expected.add(p3);

    List<Producer> actual = network.getNNearestProducers(e1, 2);

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0)); // p1
    assertEquals(expected.get(1), actual.get(1)); // p3
  }
}
