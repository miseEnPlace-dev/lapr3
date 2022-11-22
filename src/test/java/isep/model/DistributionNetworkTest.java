package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    assertEquals(distance, network.getDistanceBetween(e1, e2));
  }

  @Test
  public void testAddDuplicatedRelation() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Entity("e1", 1, 1, "l1", Role.PRODUCER);
    Entity e2 = new Entity("e2", 2, 2, "l2", Role.CLIENT);
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    assertEquals(distance, network.getDistanceBetween(e1, e2));

    assertFalse(network.addRelation(e1, e2, 20));
    assertEquals(distance, network.getDistanceBetween(e1, e2));
  }

  @Test
  public void testDuplicatedRelationWithDifferentOrder() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Entity("e1", 1, 1, "l1", Role.PRODUCER);
    Entity e2 = new Entity("e2", 2, 2, "l2", Role.CLIENT);
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    assertEquals(distance, network.getDistanceBetween(e1, e2));

    assertFalse(network.addRelation(e2, e1, 20));
    assertEquals(distance, network.getDistanceBetween(e1, e2));
  }

  @Test
  public void testGetDistanceBetween() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Entity("e1", 1, 1, "l1", Role.PRODUCER);
    Entity e2 = new Entity("e2", 2, 2, "l2", Role.CLIENT);
    Integer distance = 10;
    network.addRelation(e1, e2, distance);
    assertEquals(distance, network.getDistanceBetween(e1, e2));
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

  @Test
  public void testGetNumberOfEntities() {
    DistributionNetwork network = new DistributionNetwork();
    Entity e1 = new Entity("e1", 1, 1, "l1", Role.PRODUCER);
    Entity e2 = new Entity("e2", 2, 2, "l2", Role.CLIENT);
    Entity e3 = new Entity("e3", 3, 3, "l3", Role.ENTERPRISE);

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
    Entity e1 = new Entity("e1", 1, 1, "l1", Role.PRODUCER);
    Entity e2 = new Entity("e2", 2, 2, "l2", Role.CLIENT);
    Entity e3 = new Entity("e3", 3, 3, "l3", Role.ENTERPRISE);

    network.addRelation(e1, e2, 10);

    assertEquals(1, network.getNumberOfRelations());
    network.addRelation(e2, e1, 20);
    assertEquals(1, network.getNumberOfRelations());

    network.addRelation(e2, e3, 10);
    assertEquals(2, network.getNumberOfRelations());
    network.addRelation(e1, e3, 10);
    assertEquals(3, network.getNumberOfRelations());
  }
}
