package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import java.io.*;
import java.util.*;

/**
 *
 * @author Elizabeth Yim
 *
 * */
public class VendingMachineDaoImpl implements VendingMachineDao{
    public final String ITEM_FILE;
    private Map<String, Item> items=new HashMap<>();

    public VendingMachineDaoImpl(){
        ITEM_FILE="items.txt";
    }
    public VendingMachineDaoImpl(String itemTextFile){
        ITEM_FILE=itemTextFile;
    }
    //adds items to items hashmap
    @Override
    public Item addItem(String itemId, Item item) throws VendingMachineException {
        Item prevItem = items.put(itemId, item);
        return prevItem;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachineException {
        return new ArrayList<Item>(items.values());
    }

    @Override
    public List<String> getAllItemsId() throws VendingMachineException {
        return new ArrayList<>(items.keySet());
    }

    @Override
    public Item getItem(String itemId) throws VendingMachineException {
        return items.get(itemId);
    }

    @Override
    public Item updateItem(String itemId, Item item) throws VendingMachineException {
        return items.replace(itemId, item);
    }

    @Override
    public Item removeItem(String itemId) throws VendingMachineException {
        Item removedItem = items.remove(itemId);
        return removedItem;
    }

    @Override
    public Map<String, Item> loadItemsFromFile() throws VendingMachinePersistenceException {
        Scanner scanner;
        try{
            scanner=new Scanner(
                    new BufferedReader(
                            new FileReader(ITEM_FILE)));
        }catch(FileNotFoundException ex){
            throw new VendingMachinePersistenceException("Error: could not load data into memory", ex);
        }
        String currentLine;
        Item currentItem;
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem =new Item(currentLine);
            items.put(currentItem.getId(), currentItem);
        }
        scanner.close();
        return items;
    }

    @Override
    public void writeItemsToFile() throws VendingMachinePersistenceException, VendingMachineException {
        PrintWriter out;
        try{
            out=new PrintWriter(new FileWriter(ITEM_FILE));
        }catch (IOException ex){
            throw new VendingMachinePersistenceException("Error: could not save data", ex);
        }
        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList){
            itemAsText = currentItem.marshalItemAsText();
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }

}