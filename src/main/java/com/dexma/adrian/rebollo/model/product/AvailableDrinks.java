package com.dexma.adrian.rebollo.model.product;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import com.dexma.adrian.rebollo.model.stock.StockableProductEnum;

/**
 * Drink Inventory
 */
public enum AvailableDrinks implements StockableProductEnum {

    COKE("Coke", 1.50),
    SPRITE("Sprite", 1.45),
    WATER("Water", 0.90);

    private final String name;
    private final BigDecimal price;

    AvailableDrinks(final String name, final double price) {
        this.name = name;
        this.price = new BigDecimal(price);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static Optional<AvailableDrinks> byName(final String name) {
        return Stream.of(values())
                .filter(drink -> drink.getName().equals(name))
                .findFirst();
    }
}

