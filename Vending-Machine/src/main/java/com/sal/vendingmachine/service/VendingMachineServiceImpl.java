package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.*;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Elizabeth Yim
 *
 * */
public class VendingMachineServiceImpl implements VendingMachineService{
    VendingMachineDao dao;
    private AuditDao auditDao;

    public VendingMachineServiceImpl(VendingMachineDao dao, AuditDao auditDao){
        this.dao=dao;
        this.auditDao=auditDao;
    }

    @Override
    public Map<String, Item> loadItemsInStock() throws VendingMachineException, VendingMachineItemInventoryException, VendingMachinePersistenceException {
        Map<String, Item> itemsInStock = new HashMap<>();
        //lambda function to filter items that are in stock and items that are not
        List<Item> inStock = dao.loadItemsFromFile().values().stream().filter((i)->i.getNumInventoryItems()>0).collect(Collectors.toList());
        List<Item> noStock = dao.loadItemsFromFile().values().stream().filter((i)->i.getNumInventoryItems()==0).collect(Collectors.toList());
        for (Item item : noStock){
            auditDao.writeAuditEntry("Product ID: "+item.getId()+" quantity in stock is zero");
        }
        for (Item item : inStock){
            itemsInStock.put(item.getId(),item);
        }
        return itemsInStock;
    }

    @Override
    public void saveItemList() throws VendingMachinePersistenceException, VendingMachineException {
        dao.writeItemsToFile();
        auditDao.writeAuditEntry("Product list saved to file");
    }

    @Override
    public Item getItem(String itemId) throws VendingMachineException, VendingMachineItemInventoryException {
        validateItemInStock(itemId);
        return dao.getItem(itemId);
    }

    @Override
    public void checkSufficientFunds(BigDecimal inputAmount, Item item) throws VendingMachineInsufficientFundsException {
        if(inputAmount.compareTo(item.getCost())<0){
            throw new VendingMachineInsufficientFundsException("Error: Insufficient funds to purchase: "+ item.getName());
        }
    }

    @Override
    public Change calculateChange(BigDecimal amount, Item item) {
        BigDecimal change = amount.subtract(item.getCost()).multiply(new BigDecimal("100"));
        return new Change(change);
    }

    @Override
    public void updateItemInventory(Item item) throws VendingMachinePersistenceException, VendingMachineItemInventoryException, VendingMachineException {
        if (item.getNumInventoryItems()>0){
            item.setNumInventoryItems(item.getNumInventoryItems()-1);
        }else{
            throw new VendingMachineItemInventoryException("Error: Selected item is not in stock");
        }
        dao.updateItem(item.getId(), item);
        auditDao.writeAuditEntry("Item ID: "+item.getId()+" is updated");
    }

    private void validateItemInStock(String itemId) throws VendingMachineItemInventoryException, VendingMachineException {
        List<String>ids=dao.getAllItemsId();
        Item item = dao.getItem(itemId);
        if(!ids.contains(itemId)||item.getNumInventoryItems()<1){
            throw new VendingMachineItemInventoryException("Error: Selected item is not in stock");
        }
    }
}
