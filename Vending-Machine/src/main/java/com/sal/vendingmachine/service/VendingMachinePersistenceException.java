package com.sal.vendingmachine.service;

/**
 *
 * @author Elizabeth Yim
 *
 */
public class VendingMachinePersistenceException extends Exception{
    public VendingMachinePersistenceException(String msg){
        super(msg);
    }
    public VendingMachinePersistenceException(String msg, Throwable cause){
        super(msg,cause);
    }
}