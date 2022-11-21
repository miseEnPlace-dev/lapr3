package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DistributionNetworkTest {
  @Test
  public void testAddRelation() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Entity("e1", 1, 1, "l1", Role.PRODUCER);
    Entity e2 = new Entity("e2", 2, 2, "l2", Role.CLIENT);
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    assertEquals(network.getDistanceBetween(e1, e2), distance);
  }

  @Test
  public void testGetDistanceBetween() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Entity("e1", 1, 1, "l1", Role.PRODUCER);
    Entity e2 = new Entity("e2", 2, 2, "l2", Role.CLIENT);
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    assertEquals(network.getDistanceBetween(e1, e2), distance);
  }

  @Test
  public void testGetDistanceBetweenWithInvalidNodes() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Entity("e1", 1, 1, "l1", Role.PRODUCER);
    Entity e2 = new Entity("e2", 2, 2, "l2", Role.CLIENT);

    assertNull(network.getDistanceBetween(e1, e2));
  }

  @Test
  public void testGetDistanceBetweenWithNullNodes() {
    DistributionNetwork network = new DistributionNetwork();
    assertNull(network.getDistanceBetween(null, null));
  }

  @Test
  public void testGetDistanceBetweenWithNullFirstNode() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Entity("e1", 1, 1, "l1", Role.PRODUCER);
    Entity e2 = new Entity("e2", 2, 2, "l2", Role.CLIENT);
    Integer distance = 10;
    network.addRelation(e1, e2, distance);

    assertNull(network.getDistanceBetween(null, e2));
  }

  @Test
  public void testGetDistanceBetweenWithNullSecondNode() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Entity("e1", 1, 1, "l1", Role.PRODUCER);
    Entity e2 = new Entity("e2", 2, 2, "l2", Role.CLIENT);
    Integer distance = 10;
    network.addRelation(e1, e2, distance);

    assertNull(network.getDistanceBetween(e1, null));
  }
}
