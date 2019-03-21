package com.dexma.adrian.rebollo.model.stock;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stock class to wrapp a generic object -> stock of X type.
 * Stock is used to encapusule the products sold in the vending machine or the coins inside the machine.
 * Stock is mapped by Type -> Quantity.
 */
public abstract class Stock<T extends StockableEnum> {

    /*
    * set it to concurrent hash map for concurrency access.
    * Thread safe map without locking/synchronizing the whole map.
    * No need to lock the objects, only the acces to the map (that's why there's no need ofr SynchronizedHashMap).
    * Key = Enum name.
    * Value = Number of StockableEnum in stock.
    */
    final Map<T, Integer> stock = new ConcurrentHashMap<>();

    public void add(final T item) {
        int count = getQuantity(item);
        stock.put(item, count + 1);
    }

    public void add(final T item, final Integer quantity) {
        int count = getQuantity(item);
        stock.put(item, count + quantity);
    }

    public void subtract(final T item) {
        if (hasItem(item)) {
            int count = getQuantity(item);
            stock.put(item, count - 1);
        }
    }

    public void subtract(final T item, int quantity) {
        if (hasItem(item)) {
            int count = getQuantity(item);
            stock.put(item, count - quantity);
        }
    }

    public void subtract(final Stock<T> stock) {
        for(final Map.Entry<T, Integer> entry : stock.stock.entrySet()) {
            if (hasItem(entry.getKey())) {
                int count = getQuantity(entry.getKey());
                this.stock.put(entry.getKey(), count - entry.getValue());
            }
        }
    }

    public boolean hasItem(final T item) {
        return getQuantity(item) > 0;
    }

    public int getQuantity(final T item) {
        Integer value = Optional.ofNullable(stock.get(item)).orElse(0);
        return value == null ? 0 : value;
    }

    public Map<T, Integer> getStock() {
        return stock;
    }
}



