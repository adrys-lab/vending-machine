package com.dexma.adrian.rebollo.model.coin;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dexma.adrian.rebollo.model.stock.StockableEnum;

/**
 * Enum for supported coins in the vending machine wrapping its name and value (BigDecimal)
 */
public enum Coin implements StockableEnum {

    FIVE_CENTS(0.05),
    TEN_CENTS(0.10),
    TWENTY_CENTS(0.20),
    FIFTY_CENTS(0.50),
    ONE_EURO(1.00),
    TWO_EURO(2.00);

    private BigDecimal value;

    Coin(double value) {
        this.value = new BigDecimal(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    /**
     * Return the Coin by it's amount value.
     */
    public static Optional<Coin> byAmount(final double value) {
        return Stream.of(values())
                .filter(coin -> coin.getValue().doubleValue() == value)
                .findFirst();
    }

    /**
     * Return The Coins ordered by it's Descendant value.
     * Filter with Maximum allowed value to cut/filter the number of coins to be evaluated.
     */
    public static List<Coin> orderedValues(final double lowerOrEqualsThan) {
        return Collections.unmodifiableList(
                Stream.of(values())
                        .filter(coin -> coin.getValue().doubleValue() <= lowerOrEqualsThan)
                        .sorted(Comparator.comparing(Coin::getValue).reversed())
                        .collect(Collectors.toList())
        );
    }
}

