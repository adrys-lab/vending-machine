package com.dexma.adrian.rebollo.model.evaluator;

import java.math.BigDecimal;

import com.dexma.adrian.rebollo.model.message.Message;
import com.dexma.adrian.rebollo.model.product.Product;
import com.dexma.adrian.rebollo.util.PropertyFactory;

/*
 * Evaluates if there's enough change in the cash stock to be returned to the user.
 */
public class EnoughChange<P extends Product> extends AbstractProductEvaluator<P> {

    private final double change;
    private final double balance;

    public EnoughChange(final BigDecimal change, final BigDecimal balance) {
        this.change = change.doubleValue();
        this.balance = balance.doubleValue();
    }

    @Override
    public boolean doEvaluate(final Product product) {
        return balance - change == product.getPrice().doubleValue();
    }

    @Override
    public String getMessage(final Product product) {
        return PropertyFactory.formatMessage(Message.NO_ENOUGH_CHANGE_IN_MACHINE.getMessageKey());
    }
}
