package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import java.util.*;

/**
 *
 * @author Elizabeth Yim
 *
 */
public interface VendingMachineDao {
    /**
     *
     * adds item to map of items
     *
     * @param itemId-id of the item to be added
     * @param item-Item to be added
     * @return the item being added
     *
    * */
    Item addItem(String itemId, Item item) throws VendingMachineException;

    /**
     *
     * retrieves all the items from the map
     *
     * @return a list of the items
     *
     * */
    List<Item> getAllItems() throws VendingMachineException;

    /**
     *
     * retrieves all the item IDs from the map
     *
     * @return a list of the item IDs
     *
     * */
    List<String> getAllItemsId() throws VendingMachineException;

    /**
     *
     * retrieves a specific item from map using item ID
     *
     * @param itemId-id of the Item to be returned
     * @return Item corresponding with the ID inputted
     *
     * */
    Item getItem(String itemId) throws VendingMachineException;

    /**
     *
     * updates the information of an item in the map
     *
     * @param itemId-id of the item to be updated
     * @param item-new Item that will replace item at itemId
     * @return new Item
     *
     * */
    Item updateItem(String itemId, Item item) throws VendingMachineException;

    /**
     *
     * removes item from the map
     *
     * @param itemId-id of the item to be removed
     * @return Item to be removed
     *
     * */
    Item removeItem(String itemId) throws VendingMachineException;
    /**
     *
     * loads the items from items.txt and puts them into a map
     *
     * @return map of all the items from items.txt
     *
     * */
    Map<String, Item> loadItemsFromFile() throws VendingMachinePersistenceException;

    /**
     *
     * writes items from list to items.txt
     *
     * */
    void writeItemsToFile() throws VendingMachinePersistenceException, VendingMachineException;
}