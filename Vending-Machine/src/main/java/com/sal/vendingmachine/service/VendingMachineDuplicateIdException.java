package com.sal.vendingmachine.service;

/**
 *
 * @author Elizabeth Yim
 *
 * */
public class VendingMachineDuplicateIdException extends Exception{
    public VendingMachineDuplicateIdException(String message){
        super(message);
    }
    public VendingMachineDuplicateIdException(String message, Throwable cause){
        super(message, cause);
    }
}