package com.dexma.adrian.rebollo.controller.vending;

import com.dexma.adrian.rebollo.model.order.ProductSellOrder;
import com.dexma.adrian.rebollo.model.stock.CashStock;
import com.dexma.adrian.rebollo.model.stock.StockableProductEnum;

/**
 * Interface modelled as entryPoint for Vending Machine consumers.
 * It's generics indicates the product that the machine sells.
 * Should be public for it's external use (in case of a Consumer)
 * example:
 *     For machines which only sells Drinks -> VendingMachine<T extends AvailableDrinks>
 *     For machines which only sells Food -> VendingMachine<T extends AvailableFood>
 *     For machines which sells Drinks & Food -> VendingMachine<T extends StockableProductEnum>
 */
public interface VendingMachine<T extends StockableProductEnum> {

    /*
     * Operation to Insert Coin.
     */
    String insertCoin(final double coinValue);

    /*
     * Operation to Refund cash.
     */
    CashStock refund();

    /*
     * Operation to select a product
     */
    ProductSellOrder selectProduct(final T drink);

    /*
     * Reset Vending Machine
     */
    void reset();
}
