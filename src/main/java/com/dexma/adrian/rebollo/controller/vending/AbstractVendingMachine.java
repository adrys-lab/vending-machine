package com.dexma.adrian.rebollo.controller.vending;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.dexma.adrian.rebollo.model.coin.Coin;
import com.dexma.adrian.rebollo.model.evaluator.Evaluator;
import com.dexma.adrian.rebollo.model.evaluator.chain.EvaluatorChain;
import com.dexma.adrian.rebollo.model.evaluator.result.EvaluatorResult;
import com.dexma.adrian.rebollo.model.order.ProductSellOrder;
import com.dexma.adrian.rebollo.model.product.AvailableDrinks;
import com.dexma.adrian.rebollo.model.product.Drink;
import com.dexma.adrian.rebollo.model.product.Product;
import com.dexma.adrian.rebollo.model.stock.CashStock;
import com.dexma.adrian.rebollo.model.stock.StockableProductEnum;

/**
 * Abstract Vending Machine with common methods useful for inherited vending machines.
 */
abstract class AbstractVendingMachine<T extends StockableProductEnum> implements VendingMachine<T> {

    Optional<Coin> parseCoin(double value) {
        return Coin.byAmount(value);
    }

    <P extends Product> ProductSellOrder buildProductOrder(final P product, final CashStock change) {
        return new ProductSellOrder.ProductOrderBuilder()
                .addProduct(product)
                .addPrice(product.getPrice())
                .addChange(change)
                .build();
    }

    ProductSellOrder buildProductOrder(final EvaluatorResult evaluatorResult) {
        final ProductSellOrder.ProductOrderBuilder builder = new ProductSellOrder.ProductOrderBuilder();
        evaluatorResult.getEvaluatorMessages().forEach(message -> builder.addMessage(message.getMessage()));
        return builder.build();
    }

    Drink buildDrink(final AvailableDrinks drink) {
        return new Drink.DrinkBuilder(drink).build();
    }

    final <P extends Product> EvaluatorResult evaluateProduct(final P product, final List<Evaluator<P>> evaluators) {
        return new EvaluatorChain<P>()
                .addEvaluators(evaluators)
                .evaluateAll(product)
                .getResult();
    }

    final <P extends Product> EvaluatorResult evaluateProduct(final P product, final Evaluator<P> evaluator) {
        return evaluateProduct(product, Collections.<Evaluator<P>>singletonList(evaluator));
    }
}


