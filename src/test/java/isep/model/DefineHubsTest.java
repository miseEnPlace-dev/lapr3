package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DefineHubsTest {

    @Test
    public void testDefineHubsWorks(){
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

        DefineHubs dh = new DefineHubs(2, network);
        List<Enterprise> actual = dh.execute();

        List<Enterprise> expected = new ArrayList<>();
        expected.add(e3);
        expected.add(e1);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
    }

    @Test
    public void testDefineHubsForNullNetwork(){
        assertThrows(NullPointerException.class, () -> new DefineHubs(2, null));
    }

    @Test
    public void testDefineHubsForInvalidN(){
        DistributionNetwork network = new DistributionNetwork();
        assertThrows(NumberFormatException.class, () -> new DefineHubs(0, network));
    }

    @Test
    public void testDefineHubsForNonConnectedNetwork(){
        DistributionNetwork network = new DistributionNetwork();
        Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
        Producer e2 = new Producer("e2", 2, 2, "l2");
        Client e3 = new Client("e3", 3, 3, "l3");
        Enterprise e4 = new Enterprise("e4", 4, 4, "l4");
        Integer distance = 10;
        network.addRelation(e1, e2, distance);
        network.addRelation(e3, e4, distance);

        DefineHubs dh = new DefineHubs(2, network);

        assertNull(dh.execute());
    }

    @Test
    public void testDefineHubsForLowerNumberOfEnterprises(){
        DistributionNetwork network = new DistributionNetwork();
        Enterprise e1 = new Enterprise("e1", 1, 1, "l1");
        Producer p1 = new Producer("p1", 2, 2, "l2");
        network.addRelation(e1, p1, 100);


        DefineHubs dh = new DefineHubs(2, network);

        List<Enterprise> expected = new ArrayList<>();
        expected.add(e1);

        List<Enterprise> actual = dh.execute();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));
    }
    
}
