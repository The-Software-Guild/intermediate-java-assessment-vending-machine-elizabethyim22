package com.sal.vendingmachine.ui;

import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Coins;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Elizabeth Yim
 *
 * */
public class VendingMachineView {
    private UserIO io;
    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void displayBalance(BigDecimal bal) {
        io.print("Current balance:"+bal.setScale(2, RoundingMode.DOWN));
    }

    public void displayErrorMessage(String message) {
        io.print(message + '\n');
        io.readString("Please hit enter to continue.");
    }

    public void displayUserMoneyInput(BigDecimal balance) {
        io.print("You have inputted $"+balance);
    }

    public void displayFinalMessage() {
        io.print("THANK YOU FOR USING THE VENDING MACHINE!");
    }

    public boolean toExit() {
        String answer = io.readString("Do you want to keep using the Vending Machine? (yes/no)").toLowerCase();
        if (answer.startsWith("y")){
            return false;
        }else{
            return true;
        }
    }

    public void displayChangeDue(Change change) {
        io.print("Your change:");
        io.print(Coins.QUARTERS+" : "+change.getQuarters());
        io.print(Coins.DIMES+" : "+change.getDimes());
        io.print(Coins.NICKELS+" : "+change.getNickels());
        io.print(Coins.PENNIES+" : "+change.getPennies());
    }

    public void displayUserChoiceofItem(Item item) {
        io.print("You have chosen "+item.getName());
    }

    public BigDecimal promptUserFundInput() {
        return io.readBigDecimal("Please input money: ");
    }

    public String promptUserItemChoice() {
        return io.readString("Please input item ID of your choice:" );
    }

    public void displayItem(Item item) {
        io.print(item.getId()+"\t"+item.getName()+"\t\t"+item.getCost());
    }

    public void displayItemHeader() {
        io.print("No\tItem\t\tCost");
        io.print("---------------------");
    }

    public void displayVendingMachineWelcome() {
        io.print("WELCOME TO THE VENDING MACHINE\n");
    }

    public void displayUserResponse() {
        io.print("Do you want to make another selection? (yes/no)");
    }
}
