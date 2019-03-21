package com.dexma.adrian.rebollo.controller.cash;

import java.math.BigDecimal;

import com.dexma.adrian.rebollo.model.coin.Coin;
import com.dexma.adrian.rebollo.model.stock.CashStock;

/*
 * Controller responsible to manage the Cash of vending machine (current balance and coin stock inside machine)
 */
public interface CashController {

    /*
     * Add coin -> when a user inserts a coin into the vending machine
     */
    void insertCoin(final Coin coin);

    /*
     * Used to initialize the machine with coins and it's quantity -> will be used by Supplier
     */
    void initializeWithCoins(final Coin coin, final Integer quantity);

    /*
     * Return the current balance inside the machine
     */
    BigDecimal getBalance();

    /*
     * Refund current balance and update cash stock
     */
    void refund(final CashStock refund);

    /*
     * Update cash stock inside vending machine
     */
    void updateCashStock(final CashStock change);

    /*
     * Calculate change to be returned to the user
     */
    CashStock getChange(double price);

    /*
     * Reset current balance to 0.
     */
    void resetCurrentBalance();
}
