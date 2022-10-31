package com.sal.vendingmachine.controller;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author Elizabeth Yim
 *
 * */
public class VendingMachineController {
    private VendingMachineView view;
    private VendingMachineService service;
    public VendingMachineController (VendingMachineView view, VendingMachineService service){
        this.view=view;
        this.service=service;
    }

    public void run() throws VendingMachineException {
        BigDecimal balance = new BigDecimal("0");
        Item chosenItem=null;
        String keepGoing= "yes";
        String input;
        Scanner scanner=new Scanner(System.in);
        while(keepGoing.startsWith("y")){
            boolean sufficientFund = false;
            try{
                displayHeader();
                do{
                    itemMenu();
                    balance=addFunds(balance);
                    chosenItem=getChosenItem();
                    sufficientFund=didUserAddSufficientFunds(balance, chosenItem);
                    if (toExitVendingMachine(sufficientFund)){
                        return;
                    }
                    //has enough funds
                } while (!sufficientFund);
                displayUserMoneyInput(balance);
                displayChangeReturnedToUser(balance, chosenItem);
                updateSoldItem(chosenItem);
                //updates balance so shows how much user actually has
                balance=updateBalance(balance, chosenItem.getCost());
                saveItemList();
            }catch (VendingMachinePersistenceException ex){
                displayErrorMessage(ex.getMessage());
            }finally{
                displayFinalMessage();
            }
            view.displayUserResponse();
            keepGoing= scanner.nextLine().toLowerCase();
        }
    }

    void displayHeader(){
        view.displayVendingMachineWelcome();
    }
    void itemMenu() throws VendingMachinePersistenceException, VendingMachineException {
        try{
            view.displayItemHeader();
            for (Item item : service.loadItemsInStock().values()){
                view.displayItem(item);
            }
        }catch (VendingMachineItemInventoryException | VendingMachinePersistenceException ex){
            throw new VendingMachinePersistenceException(ex.getMessage());
        }
    }

    BigDecimal addFunds(BigDecimal balance){
        return balance.add(view.promptUserFundInput());
    }
    //updates the balance after an item is purchased
    BigDecimal updateBalance(BigDecimal balance, BigDecimal itemCost){
        BigDecimal newBalance = balance.subtract(itemCost);
        view.displayBalance(newBalance);
        return newBalance;
    }
    //prompts user for choice
    Item getChosenItem() throws VendingMachineException {
        while(true){
            String itemId = view.promptUserItemChoice();
            try{
                Item item = service.getItem(itemId);
                view.displayUserChoiceofItem(item);
                return item;
            } catch (VendingMachineItemInventoryException ex){
                view.displayErrorMessage(ex.getMessage());
            }
        }
    }
    //checks if user has enough money for item
    boolean didUserAddSufficientFunds(BigDecimal balance, Item item){
        try{
            service.checkSufficientFunds(balance, item);
            return true;
        } catch (VendingMachineInsufficientFundsException ex){
            displayErrorMessage(ex.getMessage());
            displayUserMoneyInput(balance);
            return false;
        }
    }
    void displayUserMoneyInput(BigDecimal balance){
        view.displayUserMoneyInput(balance);
    }
    //gets change and displays
    void displayChangeReturnedToUser(BigDecimal balance, Item item){
        Change change = service.calculateChange(balance, item);
        view.displayChangeDue(change);
    }
    boolean toExitVendingMachine(boolean isEnoughMoney){
        if(isEnoughMoney){
            return false;
        }else{
            return view.toExit();
        }
    }
    void displayErrorMessage(String message){
        view.displayErrorMessage(message);
    }
    //updates item inventory when item is purchased
    void updateSoldItem(Item item) throws VendingMachinePersistenceException, VendingMachineException {
        try{
            service.updateItemInventory(item);
        }catch(VendingMachineItemInventoryException ex){
            throw new VendingMachinePersistenceException(ex.getMessage());
        }
    }
    void saveItemList() throws VendingMachinePersistenceException, VendingMachineException {
        service.saveItemList();
    }
    void displayFinalMessage(){
        view.displayFinalMessage();
    }
}
