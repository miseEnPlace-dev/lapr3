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
  public void testGetNonEnterprises() {
    DistributionNetwork network = new DistributionNetwork();
    Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
    Producer e2 = new Producer("e2", 2, 2, "l2");
    Client e3 = new Client("e3", 3, 3, "l3");
    Enterprise e4 = new Enterprise("e4", 4, 4, "l4");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    network.addRelation(e3, e4, distance);

    List<Entity> expected = new ArrayList<>();
    expected.add(e2);
    expected.add(e3);

    List<Entity> actual = network.getNonEnterprises();

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.contains(e2));
    assertTrue(actual.contains(e3));
  }

  @Test
  public void testGetNonEnterprisesForEmptyNetwork() {
    DistributionNetwork network = new DistributionNetwork();

    assertEquals(network.getNonEnterprises(), new ArrayList<>());
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
    assertEquals(true, e3.isHub());
    assertEquals(true, e1.isHub());
    assertEquals(false, e2.isHub());

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
    assertEquals(true, e1.isHub());
  }
}
