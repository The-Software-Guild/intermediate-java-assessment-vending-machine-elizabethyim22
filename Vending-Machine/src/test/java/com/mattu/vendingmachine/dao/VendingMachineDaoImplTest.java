package com.mattu.vendingmachine.dao;

import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
*
* @author Elizabeth Yim
*
* */
public class VendingMachineDaoImplTest {
    String testFile="testItem.txt";
    VendingMachineDao testDao=new VendingMachineDaoImpl(testFile);
    public VendingMachineDaoImplTest(){

    }
    @BeforeEach
    public void setUp() throws Exception{

    }

    @Test
    public void testLoadItemsFromFile() throws Exception{
        System.out.println("LoadItemFromFile");
        //ARRANGE
        Item item1 = new Item("1", "Water", BigDecimal.valueOf(1.75), 9 );
        Item item2 = new Item("2", "Cake", BigDecimal.valueOf(4.26), 10);
        //ACT
        Map<String, Item> result = testDao.loadItemsFromFile();
        Map<String, Item> expResult = new HashMap<>();
        expResult.put("1", item1);
        expResult.put("2", item2);
        //ASSERT
        assertEquals(expResult, result, "Test items loaded from file.");
    }
    @Test
    public void testAddItem() throws Exception{
        System.out.println("addItem");
        //ARRANGE
        Item item1 = new Item("1", "Water", BigDecimal.valueOf(1.75), 9 );
        Item item2 = new Item("2", "Cake", BigDecimal.valueOf(4.26), 10);
        Map<String, Item> expResult = new HashMap<>();
        expResult.put(item1.getId(), item1);
        expResult.put(item2.getId(), item2);
        //ACT
        testDao.addItem(item1.getId(),item1);
        testDao.addItem(item2.getId(), item2);
        Map<String, Item> result = testDao.loadItemsFromFile();
        //ASSERT
        assertNotNull(result, "The map should not be null");
        assertEquals(expResult, result, "Tests to check that the items were properly added to the map");
        assertTrue(result.containsKey(item1.getId()),"The map should contain item1");
        assertTrue(result.containsKey(item2.getId()), "The map should contain item2");
        assertEquals(2, result.size(),"The map should have two items");

    }

    @Test
    public void testGetAllItems() throws Exception{
        System.out.println("getAllItems");
        //ARRANGE
        Item item1 = new Item("1", "Water", BigDecimal.valueOf(1.75), 9 );
        Item item2 = new Item("2", "Cake", BigDecimal.valueOf(4.26), 10);
        testDao.addItem(item1.getId(),item1);
        testDao.addItem(item2.getId(),item2);
        //ACT
        List<Item> result = testDao.getAllItems();
        List<Item> expResult = new ArrayList<>();
        expResult.add(item1);
        expResult.add(item2);
        //ASSERT
        assertEquals(expResult, result, "Test get all items from file and return list");
        assertNotNull(result, "The list of items is not null");
        assertTrue(result.contains(item1), "The list should contain Water");
        assertTrue(result.contains(item2), "The list should contain Cake");
        assertEquals(2, result.size(), "The list should contain 2 items");
    }
    @Test
    public void testGetAllItemsId() throws Exception {
        System.out.println("getAllItemsId");
        //ARRANGE
        Item item1 = new Item("1", "Water", BigDecimal.valueOf(1.75), 9);
        Item item2 = new Item("2", "Cake", BigDecimal.valueOf(4.26), 10);
        testDao.addItem(item1.getId(), item1);
        testDao.addItem(item2.getId(), item2);
        //ACT
        List<String> result = testDao.getAllItemsId();
        List<String> expResult = new ArrayList<>();
        expResult.add(item1.getId());
        expResult.add(item2.getId());
        //ASSERT
        assertNotNull(result, "The list of items should not be null");
        assertEquals(expResult, result,"Test get all item ids from file and return list");
        assertEquals(2, result.size(), "The list should contain two item ids");
        assertTrue(result.contains(item1.getId()),"The list should contain id:1");
        assertTrue(result.contains(item2.getId()), "The list should contian id:2");
    }

    @Test
    public void testGetItem() throws Exception {
        System.out.println("getItem");
        //ARRANGE
        Item item1 = new Item("1", "Water", BigDecimal.valueOf(1.75), 9);
        Item item2 = new Item("2", "Cake", BigDecimal.valueOf(4.26), 10);
        testDao.addItem(item1.getId(),item1);
        testDao.addItem(item2.getId(), item2);
        //ACT
        Item resultItem1 = testDao.getItem(item1.getId());
        Item resultItem2 = testDao.getItem(item2.getId());
        //ASSERT
        assertNotNull(resultItem1, "resultItem1 should not be null");
        assertNotNull(resultItem2, "resultItem2 should not be null");
        assertEquals(item1, resultItem1, "Test that item1 is correctly returned");
        assertEquals(item2, resultItem2, "Test that item2 is correctly returned");

    }

    @Test
    public void testUpdateItem() throws Exception{
        System.out.println("updateItem");
        //ARRANGE
        Map<String, Item> expResult = new HashMap<>();
        Item item1 = new Item("1", "Water", BigDecimal.valueOf(1.75), 0);
        Item item2 = new Item("2", "Cake", BigDecimal.valueOf(4.26), 0);
        expResult.put(item1.getId(), item1);
        expResult.put(item2.getId(), item2);
        //ACT
        Item newItem1 = new Item("1", "Water", BigDecimal.valueOf(1.75), 0);
        Item newItem2 = new Item("2", "Cake", BigDecimal.valueOf(4.26), 0);
        Map<String,Item> resultMap = testDao.loadItemsFromFile();
        testDao.updateItem("1", newItem1);
        testDao.updateItem("2", newItem2);
        //ASSERT
        assertNotNull(resultMap, "resultMap should not be null");
        assertEquals(expResult, resultMap, "Tests that items can be updated");
        assertEquals(2, resultMap.size(), "resultMap should have two items");
    }




}
