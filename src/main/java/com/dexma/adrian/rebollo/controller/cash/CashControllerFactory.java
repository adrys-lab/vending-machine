package com.dexma.adrian.rebollo.controller.cash;

/**
 * Factory to create a new Instance of Cash Controller implementation.
 */
public class CashControllerFactory {

    public static CashController createCashController() {
        return new CashControllerImpl();
    }
}

