package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author Elizabeth Yim
 *
 */
public interface VendingMachineService {

    /**
     *
     * loads the items that are in stock from items.txt into map
     *
     * @return map of all the items in stock
     *
     * */
    public Map<String, Item>loadItemsInStock() throws VendingMachineException, VendingMachineItemInventoryException, VendingMachinePersistenceException;

    /**
     *
     * saves item to items.txt an adds audit entry to audit.txt
     *
     * */
    public void saveItemList() throws VendingMachinePersistenceException, VendingMachineException;

    /**
     *
     * gets an item from the map using the ID
     *
     * @param itemId-id of the item to be returned
     * @return returns Item
     *
     * */
    public Item getItem(String itemId) throws VendingMachineException,VendingMachineItemInventoryException;

    /**
     *
     * checks to make sure that user has enough money to purchase item
     *
     * @param inputAmount-amount user has
     * @param item-item to be purchased
     *
     * */
    public void checkSufficientFunds(BigDecimal inputAmount, Item item) throws VendingMachineInsufficientFundsException;

    /**
     *
     * calculates the change to be returned to user after purchase
     *
     * @param amount-total amount of money user has
     * @param item-item to be purchased
     * @return change after purchase of item returned in coins
     *
     * */
    public Change calculateChange(BigDecimal amount, Item item);

    /**
     *
     * updates the item inventory after item purchased
     *
     * @param item-item being purchased
     *
     * */
    public void updateItemInventory(Item item) throws VendingMachinePersistenceException, VendingMachineItemInventoryException, VendingMachineException;

}