package com.dexma.adrian.rebollo.model.stock;

import java.math.BigDecimal;

import com.dexma.adrian.rebollo.model.coin.Coin;

/**
 * Cash is an Stock enumeration of coins -> translated into the number of coins available inside the machine
 */
public class CashStock extends Stock<Coin> {

    public BigDecimal getBalance() {
        return new BigDecimal(stock.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getValue().multiply(new BigDecimal(entry.getValue())).doubleValue())
                .sum());
    }
}



