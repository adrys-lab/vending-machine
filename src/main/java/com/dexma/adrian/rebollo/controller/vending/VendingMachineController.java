package com.dexma.adrian.rebollo.controller.vending;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.dexma.adrian.rebollo.controller.cash.CashController;
import com.dexma.adrian.rebollo.controller.cash.CashControllerFactory;
import com.dexma.adrian.rebollo.controller.product.ProductController;
import com.dexma.adrian.rebollo.controller.product.drink.DrinkControllerFactory;
import com.dexma.adrian.rebollo.model.coin.Coin;
import com.dexma.adrian.rebollo.model.evaluator.EnoughBalance;
import com.dexma.adrian.rebollo.model.evaluator.EnoughChange;
import com.dexma.adrian.rebollo.model.evaluator.Evaluator;
import com.dexma.adrian.rebollo.model.evaluator.ProductIsInStockEvaluator;
import com.dexma.adrian.rebollo.model.evaluator.result.EvaluatorResult;
import com.dexma.adrian.rebollo.model.message.Message;
import com.dexma.adrian.rebollo.model.order.ProductSellOrder;
import com.dexma.adrian.rebollo.model.product.AvailableDrinks;
import com.dexma.adrian.rebollo.model.product.Drink;
import com.dexma.adrian.rebollo.model.stock.CashStock;
import com.dexma.adrian.rebollo.util.PropertyFactory;

/**
 * Sample implementation of Vending Machine
 */
class VendingMachineController extends AbstractVendingMachine<AvailableDrinks> {

    private CashController cashController;
    private ProductController<Drink> productController;

    //Set scope to package-private to avoid instantiation -> only allowed through Factory.
    VendingMachineController() {
        this.cashController = CashControllerFactory.createCashController();
        this.productController = DrinkControllerFactory.createDrinkController();
        initialize();
    }

    @Override
    public String insertCoin(final double coinValue) {
        final Optional<Coin> optionalCoin = parseCoin(coinValue);
        if (optionalCoin.isPresent()) {
            cashController.insertCoin(optionalCoin.get());
            return PropertyFactory.formatMessage(Message.CURRENT_CREDIT.getMessageKey(), cashController.getBalance().doubleValue());
        }
        return PropertyFactory.formatMessage(Message.COIN_NOT_VALID.getMessageKey(), coinValue);
    }

    @Override
    public CashStock refund() {
        final CashStock refund = cashController.getChange(0.0);
        cashController.refund(refund);
        return refund;
    }

    @Override
    public ProductSellOrder selectProduct(final AvailableDrinks selectedDrink) {
        final Drink drink = buildDrink(selectedDrink);
        EvaluatorResult evaluatorResult = evaluateProduct(drink, getSellEvaluatorsList());
        if (!evaluatorResult.hasMessages()) {
            final CashStock change = cashController.getChange(drink.getPrice().doubleValue());
            evaluatorResult = evaluateProduct(drink, new EnoughChange<>(change.getBalance(), cashController.getBalance()));
            if (!evaluatorResult.hasMessages()) {
                productController.addProductSell(drink);
                cashController.updateCashStock(change);
                cashController.resetCurrentBalance();
                return buildProductOrder(drink, change);
            }
        }
        cashController.resetCurrentBalance();
        return buildProductOrder(evaluatorResult);
    }

    @Override
    public void reset() {
        this.cashController = CashControllerFactory.createCashController();
        this.productController = DrinkControllerFactory.createDrinkController();
        initialize();
    }

    /*
     * By default Initialize the vending machine with 5u of each Coin and 2u of each Product in stock.
     */
    private void initialize() {
        Stream.of(Coin.values()).forEach(coin -> cashController.initializeWithCoins(coin, 5));
        Stream.of(AvailableDrinks.values()).forEach(drink -> productController.initializeWithProducts(drink, 10));
    }

    private List<Evaluator<Drink>> getSellEvaluatorsList() {
        return Arrays.asList(
                new ProductIsInStockEvaluator<Drink>(productController.getAvailableProducts()),
                new EnoughBalance<Drink>(cashController.getBalance())
        );
    }
}
