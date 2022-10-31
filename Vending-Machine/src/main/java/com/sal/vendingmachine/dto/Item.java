package com.sal.vendingmachine.dto;

import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author Elizabeth Yim
 *
 */
public class Item {
    private String itemId;
    private String name;
    private BigDecimal cost;
    private int numInventoryItems;
    private final String DELIMITER="::";

    public Item(String itemId, String name, BigDecimal cost, int numInventoryItems) {
        this.itemId=itemId;
        this.name=name;
        this.cost=cost;
        this.numInventoryItems=numInventoryItems;
    }

    public Item(String itemAsText) throws VendingMachinePersistenceException {
        try{
            String[] fields = itemAsText.split(DELIMITER);
            this.itemId=fields[0];
            this.name=fields[1];
            this.cost=new BigDecimal(fields[2]);
            this.numInventoryItems = Integer.parseInt(fields[3]);
        }catch(PatternSyntaxException ex){
            throw new VendingMachinePersistenceException(ex.getMessage());
        }catch(NullPointerException | NumberFormatException ex){
            throw new VendingMachinePersistenceException(ex.getMessage());
        }
    }

    public String getId(){
        return itemId;
    }

    public void setId(){
        this.itemId=itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getNumInventoryItems() {
        return numInventoryItems;
    }

    public void setNumInventoryItems(int numInventoryItems) {
        this.numInventoryItems = numInventoryItems;
    }
    public String marshalItemAsText() {
        return this.getId() + DELIMITER + this.getName() + DELIMITER + this.getCost() + DELIMITER + this.getNumInventoryItems();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getNumInventoryItems() == item.getNumInventoryItems() && itemId.equals(item.itemId) && getName().equals(item.getName()) && getCost().equals(item.getCost()) && DELIMITER.equals(item.DELIMITER);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, getName(), getCost(), getNumInventoryItems(), DELIMITER);
    }
}