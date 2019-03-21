package com.dexma.adrian.rebollo.model.product;

import java.math.BigDecimal;

import com.dexma.adrian.rebollo.model.stock.StockableProductEnum;

public abstract class Product {

    final String name;
    private final BigDecimal price;

    Product(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public abstract <P extends StockableProductEnum> P getEnum();
}
