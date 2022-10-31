package com.mattu.vendingmachine.service;

import com.sal.vendingmachine.dao.*;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
*
* @author Elizabeth Yim
*
* */
public class VendingMachineServiceImplTest {
    private VendingMachineService testService;

    public VendingMachineServiceImplTest(){
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        AuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        testService = new VendingMachineServiceImpl(dao, auditDao);

    }

    @Test
    public void testLoadItemsInStock() throws VendingMachineException, VendingMachinePersistenceException, VendingMachineItemInventoryException {
        try {
            System.out.println("loadItemsInStock");
            BigDecimal cost = new BigDecimal("1.25");
            Item item1 = new Item("1", "Water", cost, 9);
            //expected
            Map<String, Item> expResult = new HashMap<>();
            expResult.put("1", item1);
            //actual
            Map<String,Item> result = testService.loadItemsInStock();
            assertEquals(expResult, result, "Test Products loaded from file the same");
        }catch(VendingMachineItemInventoryException | VendingMachinePersistenceException ex){
            fail("Product was valid. No exception should have been thrown");
        }

    }

    @Test
    public void testGetItem() throws VendingMachineItemInventoryException, VendingMachineException {
        System.out.println("GetItem");
        BigDecimal cost = new BigDecimal("1.25");
        Item item = new Item("1", "Water", cost, 9);
        Item result = testService.getItem("1");
        assertEquals(item, result, "Tests that items are equal");
        assertEquals(item.getName(),result.getName(), "Check that the items are the same");
    }

    @Test
    public void testCheckSufficientFunds() throws VendingMachineInsufficientFundsException, VendingMachineException, VendingMachineItemInventoryException {
        try{
            System.out.println("checkSufficientFunds");
            BigDecimal inputAmount=new BigDecimal("3.50");
            Item result = testService.getItem("1");
            testService.checkSufficientFunds(inputAmount,result);
        }catch (VendingMachineInsufficientFundsException ex){
            fail("Sufficient funds to buy item. No exception should have been thrown");

        }
    }

    @Test
    public void testCalculateChange() throws VendingMachineException, VendingMachineItemInventoryException {
        System.out.println("calculateChange");
        BigDecimal amount = new BigDecimal("5");
        Item item = testService.getItem("1");
        Change change = testService.calculateChange(amount,item);
        //ASSERT
        assertEquals(15, change.getQuarters(), "Change should return 15 quarters");
        assertEquals(0, change.getDimes(), "Change should return 0 dimes");
        assertEquals(0, change.getNickels(), "Change should return 0 nickels");
        assertEquals(0, change.getPennies(), "Change should return 0 pennies");
    }

    @Test
    public void testUpdateItemInventory() throws VendingMachineException, VendingMachineItemInventoryException, VendingMachinePersistenceException {
        System.out.println("updateItemInventory");
        Item item = testService.getItem("1");
        assertEquals(9, item.getNumInventoryItems(), "Check items in stock");
        testService.updateItemInventory(item);
        Item updateItem = testService.getItem("1");
        assertEquals(8,  updateItem.getNumInventoryItems(), "Check items in stock now 9");
    }
}
