package com.dexma.adrian.rebollo.controller.cash;

import java.math.BigDecimal;
import java.math.MathContext;

import com.dexma.adrian.rebollo.model.coin.Coin;
import com.dexma.adrian.rebollo.model.stock.CashStock;

/*
 * Cash controller is the responsible for the global cash available in the whole Vending machine, and the current balance.
 */
class CashControllerImpl implements CashController {

    private CashStock cashStock = new CashStock();
    private CashStock balance = new CashStock();

    CashControllerImpl(){}

    @Override
    public void insertCoin(final Coin coin) {
        cashStock.add(coin);
        balance.add(coin);
    }

    @Override
    public void initializeWithCoins(final Coin coin, final Integer quantity) {
        cashStock.add(coin, quantity);
        balance = new CashStock();
    }

    @Override
    public BigDecimal getBalance() {
        return balance.getBalance();
    }

    @Override
    public void refund(final CashStock refund) {
        cashStock.subtract(refund);
        balance.subtract(refund);
    }

    /*
     * Method that substracts some CashStock to the existing one.
     * It's called when a Sell is being produced, so the change should be subtracted from current cash stock.
     */
    @Override
    public void updateCashStock(final CashStock change) {
        change.getStock().entrySet().forEach(entry -> cashStock.subtract(entry.getKey(), entry.getValue()));
    }

    /*
     * getChange method tries the change ordering coins Descendant.
     * Then for each gets the quantity of coins, and substract them from a Temporary Cash Stock.
     * Coi Candidates are added with it's quantity into a CashStock object.
     * Example: For return 85 Cents:
     *          CashStock --> FIFTY_CENTS --> 1 Unit
     *                    --> TWENTY_CENTS> 1 Units
     *                    --> TEN_CENTS > 1 Units
     *                    --> FIVE_CENTS > 1 Units
     */
    @Override
    public CashStock getChange(double productPrice) {
        final CashStock change = new CashStock();
        final CashStock tmpCashStock = new CashStock();
        cashStock.getStock().entrySet().forEach(t -> tmpCashStock.add(t.getKey(), t.getValue()));
        double  cashToReturn = balance.getBalance().subtract(new BigDecimal(productPrice).round(MathContext.DECIMAL64)).doubleValue();
        for(final Coin coinCandidate : Coin.orderedValues(cashToReturn)) {
            final Double coinValue = coinCandidate.getValue().doubleValue();
            final Double quantity = Math.floor(cashToReturn / coinValue);
            if (quantity > 0) {
                final int coinStock = tmpCashStock.getQuantity(coinCandidate) >= quantity ? quantity.intValue() : tmpCashStock.getQuantity(coinCandidate);
                tmpCashStock.subtract(coinCandidate, coinStock);
                cashToReturn -= coinValue * coinStock;
                change.add(coinCandidate, coinStock);
            }
        }
        return change;
    }

    @Override
    public void resetCurrentBalance() {
        this.balance  = new CashStock();
    }
}