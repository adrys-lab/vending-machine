package com.dexma.adrian.rebollo.controller.vending;

import com.dexma.adrian.rebollo.model.product.AvailableDrinks;

/**
 * Factory to get Singleton Vending Machine implementation instance.
 * Should be public for it's external use (in case of a Consumer)
 */
public class VendingMachineFactory {

    private static AbstractVendingMachine<AvailableDrinks> instance;

    /*
     * Apply Double Checked Locking principle.
     * Vending machine should be under Singleton pattern -> only one vending machine instance exists.
     */
    public static VendingMachine<AvailableDrinks> getInstance() {
        if(instance == null){
            synchronized (VendingMachineController.class) {
                if(instance == null){
                    instance = new VendingMachineController();
                }
            }
        }
        return instance;
    }

}

