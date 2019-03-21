package com.dexma.adrian.rebollo.controller.product.drink;

/**
 * Factory to create a new Instance of Vending Machine implementation.
 */
public class DrinkControllerFactory {

    public static DrinkControllerImpl createDrinkController() {
        return new DrinkControllerImpl();
    }
}

