package com.dexma.adrian.rebollo.model.evaluator;

import java.math.BigDecimal;

import com.dexma.adrian.rebollo.model.message.Message;
import com.dexma.adrian.rebollo.model.product.Product;
import com.dexma.adrian.rebollo.util.PropertyFactory;

/*
 * Evaluates if the current balance is enough to buy a product price.
 */
public class EnoughBalance<P extends Product> extends AbstractProductEvaluator<P> {

    private final double balance;

    public EnoughBalance(final BigDecimal balance) {
        this.balance = balance.doubleValue();
    }

    @Override
    public boolean doEvaluate(final Product product) {
        return balance >= product.getPrice().doubleValue();
    }

    @Override
    public String getMessage(final Product product) {
        return PropertyFactory.formatMessage(Message.NO_ENOUGH_BALANCE_FOR_BUY.getMessageKey(),
                balance, product.getName(),
                product.getPrice().doubleValue());
    }
}
