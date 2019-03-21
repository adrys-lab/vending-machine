package com.dexma.adrian.rebollo.model.order;

import java.math.BigDecimal;

import com.dexma.adrian.rebollo.model.message.Message;
import com.dexma.adrian.rebollo.model.product.Product;
import com.dexma.adrian.rebollo.model.stock.CashStock;
import com.dexma.adrian.rebollo.util.PropertyFactory;

/**
 * An order object represents a Product Sell.
 */
public class ProductSellOrder extends SellOrder<Product> {

    private ProductSellOrder(final Product product, final BigDecimal price, final CashStock coins, final String message) {
        super(product, price, coins, message);
    }

    public static class ProductOrderBuilder {

        private String message = "";
        private Product product;
        private BigDecimal price;
        private CashStock change;
        private double totalChange;

        public ProductOrderBuilder addProduct(final Product product) {
            this.product = product;
            return this;
        }

        public ProductOrderBuilder addPrice(final BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductOrderBuilder addChange(final CashStock change) {
            this.change = change;
            totalChange += change.getBalance().doubleValue();
            return this;
        }

        public ProductOrderBuilder addMessage(final String message) {
            if (!message.isEmpty()) {
                this.message = this.message.concat(message).concat("\n");
            }
            return this;
        }

        public ProductSellOrder build() {
            if (message.isEmpty()) {
               message = PropertyFactory.formatMessage(Message.PRODUCT_SELL_OK.getMessageKey(),
                       product.getName(),
                       product.getPrice().doubleValue(),
                       totalChange);
            }
            return new ProductSellOrder(product, price, change, message);
        }
    }
}

