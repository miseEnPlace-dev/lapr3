package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import isep.shared.exceptions.InvalidProductNameException;

public class DailyDataTest {

    @Test
    public void testQuantityAvailableWorks() throws InvalidProductNameException{
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
        dailyData.addDayData(4, productsDay4);

        assertEquals(1100, dailyData.getNonExpiredProductQuantity(p, 4));
    }

    @Test
    public void testQuantityAvailableWorksWithNoData() throws InvalidProductNameException{
        assertEquals(0, new DailyData().getNonExpiredProductQuantity(new Product("test"), 2));
    }
    

    @Test
    public void testQuantityAvailableWorksWithPartialData() throws InvalidProductNameException{
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

        assertEquals(400, dailyData.getNonExpiredProductQuantity(p, 3));
    }

    @Test
    public void testRemoveValidQuantityWorks() throws InvalidProductNameException{
        DailyData dailyData = new DailyData();
        Product p = new Product("Banana");

        dailyData.addProductInfoToDayData(1, p, 300);
        dailyData.addProductInfoToDayData(1, new Product("Apple"), 100);

        dailyData.addProductInfoToDayData(2, p, 100);
        dailyData.addProductInfoToDayData(2, new Product("Tomato"), 200);

        dailyData.addProductInfoToDayData(3, p, 500);

        dailyData.addProductInfoToDayData(4, p, 500);

        dailyData.removeValidProductQuantity(p, 700, 4);
        assertEquals(300, dailyData.getQuantityOfProductForDay(1, p));
        assertEquals(0, dailyData.getQuantityOfProductForDay(2, p));
        assertEquals(0, dailyData.getQuantityOfProductForDay(3, p));
        assertEquals(400, dailyData.getQuantityOfProductForDay(4, p));

    }
}
