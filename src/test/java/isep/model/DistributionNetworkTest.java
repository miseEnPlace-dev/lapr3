package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}
