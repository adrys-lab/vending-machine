package com.dexma.adrian.rebollo.model.order;

import java.math.BigDecimal;

import com.dexma.adrian.rebollo.model.stock.CashStock;

/**
 * An order object represents an item Order.
 * Wraps whole Order information. -> item, price and change.
 * It's generic is the sold item.
 */
class SellOrder<T> {

    private final T item;
    private final BigDecimal price;
    private final CashStock change;
    private final String message;

    SellOrder(final T first, final BigDecimal price, final CashStock change, final String message) {
        this.item = first;
        this.price = price;
        this.change = change;
        this.message = message;
    }

    public T getItem() {
        return item;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public final CashStock getChange() {
        return change;
    }

    public String getMessage() {
        return message;
    }
}

