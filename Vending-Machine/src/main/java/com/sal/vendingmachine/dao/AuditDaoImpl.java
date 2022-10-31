package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.service.VendingMachinePersistenceException;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author Elizabeth Yim
 *
 */
public class AuditDaoImpl implements AuditDao{
    public static final String AUDIT_FILE = "audit.txt";
    @Override
    public void writeAuditEntry(String entry) throws VendingMachineException, VendingMachinePersistenceException {
        PrintWriter out;
        try{
            out=new PrintWriter(new FileWriter(AUDIT_FILE,true));
        } catch (IOException e){
            throw new VendingMachinePersistenceException("Error: Could not persist audit information");
        }
        LocalDateTime timeStamp = LocalDateTime.now();
        out.println(timeStamp.toString()+" : "+ entry);
        out.flush();
    }

}