package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.service.VendingMachinePersistenceException;

/**
 *
 * @author Elizabeth Yim
 *
 * */
public interface AuditDao {
    /**
     *
     * writes new audit entry to the audit.txt file
     *
     * @param entry-string entry of the item info to be added
     *
     */
    void writeAuditEntry(String entry) throws VendingMachineException, VendingMachinePersistenceException;
}
