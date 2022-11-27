package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DistributionNetworkTest {
  @Test
  public void testAddRelation() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    assertEquals(network.getDistanceBetweenDirectedConnected(e1, e2), distance);
  }

  @Test
  public void testGetDistanceBetween() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    assertEquals(network.getDistanceBetweenDirectedConnected(e1, e2), distance);
  }

  @Test
  public void testGetDistanceBetweenWithInvalidNodes() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");

    assertNull(network.getDistanceBetweenDirectedConnected(e1, e2));
  }

  @Test
  public void testGetDistanceBetweenWithNullNodes() {
    DistributionNetwork network = new DistributionNetwork();
    assertNull(network.getDistanceBetweenDirectedConnected(null, null));
  }

  @Test
  public void testGetDistanceBetweenWithNullFirstNode() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);

    assertNull(network.getDistanceBetweenDirectedConnected(null, e2));
  }

  @Test
  public void testGetDistanceBetweenWithNullSecondNode() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Producer("e1", 1, 1, "l1");
    Entity e2 = new Client("e2", 2, 2, "l2");
    Integer distance = 10;
    network.addRelation(e1, e2, distance);

    assertNull(network.getDistanceBetweenDirectedConnected(e1, null));
  }

  @Test
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
    assertEquals(expected.get(0), actual.get(0));
    assertEquals(expected.get(1), actual.get(1));
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

}
