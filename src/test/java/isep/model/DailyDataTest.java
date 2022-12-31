package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import isep.shared.exceptions.InvalidProductNameException;

public class DailyDataTest {

    @Test
    public void testAvailableDataWorks() throws InvalidProductNameException{
        DailyData dailyData = new DailyData();
        Product p = new Product("Banana");

        HashMap<Product, Integer> productsDay1 = new HashMap<>();
        productsDay1.put(p, 300);
        productsDay1.put(new Product("Apple"), 100);
        dailyData.addDayData(1, productsDay1);

        HashMap<Product, Integer> productsDay2 = new HashMap<>();
        productsDay2.put(p, 100);
        productsDay2.put(new Product("Tomato"), 200);
        dailyData.addDayData(2, productsDay2);

        HashMap<Product, Integer> productsDay3 = new HashMap<>();
        productsDay3.put(p, 500);
        dailyData.addDayData(3, productsDay3);

        HashMap<Product, Integer> productsDay4 = new HashMap<>();
        productsDay4.put(p, 500);
        dailyData.addDayData(4, productsDay3);

        assertEquals(1100, dailyData.getQuantityAvailable(p, 4));
    }

    @Test
    public void testAvailableDataWorksWithNoData() throws InvalidProductNameException{
        assertEquals(0, new DailyData().getQuantityAvailable(new Product("test"), 2));
    }
    

    @Test
    public void testAvailableDataWorksWithPartialData() throws InvalidProductNameException{
        DailyData dailyData = new DailyData();
        Product p = new Product("Banana");

        HashMap<Product, Integer> productsDay1 = new HashMap<>();
        productsDay1.put(p, 300);
        productsDay1.put(new Product("Apple"), 100);
        dailyData.addDayData(1, productsDay1);

        HashMap<Product, Integer> productsDay2 = new HashMap<>();
        productsDay2.put(p, 100);
        productsDay2.put(new Product("Tomato"), 200);
        dailyData.addDayData(2, productsDay2);

        assertEquals(400, dailyData.getQuantityAvailable(p, 3));
    }
}
