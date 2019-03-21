package com.dexma.adrian.rebollo.model.evaluator;

import com.dexma.adrian.rebollo.model.message.Message;
import com.dexma.adrian.rebollo.model.product.Product;
import com.dexma.adrian.rebollo.model.stock.Stock;
import com.dexma.adrian.rebollo.model.stock.StockableProductEnum;
import com.dexma.adrian.rebollo.util.PropertyFactory;

/*
 * Evaluator for evaluate if there are enough stock of given product.
 */
public class ProductIsInStockEvaluator<P extends Product> extends AbstractProductEvaluator<P> {

    private final Stock<? extends StockableProductEnum> availableProducts;

    public ProductIsInStockEvaluator(final Stock<? extends StockableProductEnum> availableProducts) {
        this.availableProducts = availableProducts;
    }

    @Override
    public boolean doEvaluate(final Product product) {
        return availableProducts.hasItem(product.getEnum());
    }

    @Override
    public String getMessage(final Product product) {
        return PropertyFactory
                .formatMessage(Message.PRODUCT_IS_NOT_IN_STOCK.getMessageKey(), product.getName());
    }
}
