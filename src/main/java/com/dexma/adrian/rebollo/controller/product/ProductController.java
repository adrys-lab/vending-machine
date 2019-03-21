package com.dexma.adrian.rebollo.controller.product;

import com.dexma.adrian.rebollo.model.product.AvailableDrinks;
import com.dexma.adrian.rebollo.model.product.Product;
import com.dexma.adrian.rebollo.model.stock.Stock;
import com.dexma.adrian.rebollo.model.stock.StockableProductEnum;

/*
 * Product controller -> responsible for products management.
 * It's generic inherits from product (now only Drinks, but possible future Food addition)
 */
public interface ProductController<T extends Product> {

    /*
     * Initialize with Products and it's quantity -> will be used by Supplier
     */
    void initializeWithProducts(final AvailableDrinks drink, final Integer quantity);

    /*
     * Add product Sell -> updates current product Stock after a sell.
     */
    void addProductSell(final T product);

    /*
     * Return the products stock
     */
    Stock<? extends StockableProductEnum> getAvailableProducts();
}
