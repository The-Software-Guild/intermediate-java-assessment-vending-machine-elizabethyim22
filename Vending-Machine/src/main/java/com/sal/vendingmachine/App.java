package com.sal.vendingmachine;

import com.sal.vendingmachine.controller.VendingMachineController;
import com.sal.vendingmachine.dao.*;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.ui.UserIO;
import com.sal.vendingmachine.ui.UserIOImpl;
import com.sal.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author Elizabeth Yim
 *
 * */
public class App {
    public static void main(String[] args) throws VendingMachineException {
        UserIO myIo = new UserIOImpl();
        VendingMachineView myView = new VendingMachineView(myIo);
        VendingMachineDao myDao = new VendingMachineDaoImpl();
        AuditDao myAdao = new AuditDaoImpl();
        VendingMachineService myService = new VendingMachineServiceImpl(myDao, myAdao);
        VendingMachineController controller = new VendingMachineController(myView,myService);
        controller.run();
    }
}