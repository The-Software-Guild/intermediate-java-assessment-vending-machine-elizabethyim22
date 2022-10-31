package com.mattu.vendingmachine.service;

import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elizabeth Yim
 *
 * */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
    public Item onlyItem;
    public VendingMachineDaoStubImpl(){
        onlyItem = new Item("1","Water", BigDecimal.valueOf(1.25), 9);
    }
    public VendingMachineDaoStubImpl(Item testItem){
        this.onlyItem=testItem;
    }
    @Override
    public Item addItem(String itemId, Item item) throws VendingMachineException{
        if (itemId.equals(onlyItem.getId())){
            return onlyItem;
        }else{
            return null;
        }
    }
    @Override
    public List<Item> getAllItems() throws VendingMachineException {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }
    @Override
    public List<String> getAllItemsId() throws VendingMachineException{
        List<String> itemIdsList = new ArrayList<>();
        itemIdsList.add(onlyItem.getId());
        return itemIdsList;
    }
    @Override
    public Item getItem(String itemId) throws VendingMachineException{
        if (itemId.equals(onlyItem.getId())){
            return onlyItem;
        }else{
            return null;
        }
    }

    @Override
    public Item updateItem(String itemId, Item item) throws VendingMachineException {
        if (itemId.equals(onlyItem.getId())){
            return onlyItem;
        }else{
            return null;
        }
    }

    @Override
    public Item removeItem(String itemId) throws VendingMachineException {
        if (itemId.equals(onlyItem.getId())){
            return onlyItem;
        }else{
            return null;
        }
    }

    @Override
    public Map<String, Item> loadItemsFromFile() throws VendingMachinePersistenceException {
        Map<String,Item> itemMap = new HashMap<>();
        itemMap.put("1", onlyItem);
        return itemMap;
    }

    @Override
    public void writeItemsToFile() throws VendingMachinePersistenceException, VendingMachineException {

    }

}


