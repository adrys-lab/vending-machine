package com.dexma.adrian.rebollo.controller.vending;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dexma.adrian.rebollo.model.coin.Coin;
import com.dexma.adrian.rebollo.model.message.Message;
import com.dexma.adrian.rebollo.model.order.ProductSellOrder;
import com.dexma.adrian.rebollo.model.product.AvailableDrinks;
import com.dexma.adrian.rebollo.model.stock.CashStock;
import com.dexma.adrian.rebollo.util.PropertyFactory;

public class VendingMachineTest {

    private static VendingMachine<AvailableDrinks> vm;

    @BeforeClass
    public static void setUp() {
        vm = VendingMachineFactory.getInstance();
    }

    @Before
    public void beforeTest() {
        vm.reset();
    }

    @Test
    public void testVendingMachineIsSingleton() {
        VendingMachine vendingMachine = VendingMachineFactory.getInstance();
        Assert.assertTrue(vm.hashCode() == vendingMachine.hashCode());
    }

    @Test
    public void testMessageForNonExistingCoin() {
        final double nonExistingCoin = 0.02;
        final String insertCoinMessage = vm.insertCoin(nonExistingCoin);
        Assert.assertEquals(insertCoinMessage, PropertyFactory.formatMessage(Message.COIN_NOT_VALID.getMessageKey(), nonExistingCoin));
    }

    @Test
    public void testMessageExistingCoin() {
        final double existingCoin = 1.0;
        final String insertCoinMessage = vm.insertCoin(existingCoin);
        Assert.assertEquals(insertCoinMessage, PropertyFactory.formatMessage(Message.CURRENT_CREDIT.getMessageKey(), existingCoin));
    }

    @Test
    public void testSeveralAddCoin() {
        final double existingCoin = 1.0;
        final double anotherExistingCoin = 0.20;
        final String firstInsertCoinMessage = vm.insertCoin(existingCoin);
        final String secondInsertCoinMessage = vm.insertCoin(anotherExistingCoin);
        Assert.assertEquals(firstInsertCoinMessage, PropertyFactory.formatMessage(Message.CURRENT_CREDIT.getMessageKey(), existingCoin));
        Assert.assertEquals(secondInsertCoinMessage, PropertyFactory.formatMessage(Message.CURRENT_CREDIT.getMessageKey(), existingCoin + anotherExistingCoin));
    }

    @Test
    public void testInsufficientCredit() {
        final double existingCoin = 0.20;
        final AvailableDrinks coke = AvailableDrinks.COKE;
        vm.insertCoin(existingCoin);
        final ProductSellOrder productSellOrder = vm.selectProduct(coke);
        Assert.assertTrue(productSellOrder.getMessage().contains(PropertyFactory.formatMessage(Message.NO_ENOUGH_BALANCE_FOR_BUY.getMessageKey(),
                existingCoin,
                coke.getName(),
                coke.getPrice().doubleValue())));
    }

    @Test
    public void testGoodChange() {
        final double existingCoin = 2.00;
        final AvailableDrinks coke = AvailableDrinks.COKE;
        vm.insertCoin(existingCoin);
        final ProductSellOrder productSellOrder = vm.selectProduct(coke);
        Assert.assertTrue(productSellOrder.getChange().getBalance().doubleValue() == existingCoin - coke.getPrice().doubleValue());
        Assert.assertTrue(productSellOrder.getChange().getStock().get(Coin.FIFTY_CENTS) == 1);
    }

    @Test
    public void testGoodChange2() {
        final double twoEuros = 2.0;
        final AvailableDrinks coke = AvailableDrinks.SPRITE;
        vm.insertCoin(twoEuros);
        final ProductSellOrder productSellOrder = vm.selectProduct(coke);
        Assert.assertTrue(productSellOrder.getChange().getBalance().doubleValue() == twoEuros - coke.getPrice().doubleValue());
        Assert.assertTrue(productSellOrder.getChange().getStock().get(Coin.FIFTY_CENTS) == 1);
        Assert.assertTrue(productSellOrder.getChange().getStock().get(Coin.FIVE_CENTS) == 1);
        Assert.assertEquals(productSellOrder.getPrice(), coke.getPrice() );
        Assert.assertEquals(productSellOrder.getItem().getEnum(), coke);
    }

    @Test
    public void testOutOfStock() {
        final double oneWuro = 1.0;
        final double fifTyCents = 0.50;
        final AvailableDrinks coke = AvailableDrinks.COKE;
        ProductSellOrder lastProductSellOrder = null;
        for(int i = 0; i<=10; i++) {
            vm.insertCoin(oneWuro);
            vm.insertCoin(fifTyCents);
            lastProductSellOrder = vm.selectProduct(coke);
        }
        Assert.assertTrue(lastProductSellOrder.getMessage().contains(PropertyFactory.formatMessage(Message.PRODUCT_IS_NOT_IN_STOCK.getMessageKey(),
                coke.getName())));
    }

    @Test
    public void testRefund() {
        final double twoEuros = 2.0;
        vm.insertCoin(twoEuros);
        final CashStock cashStock = vm.refund();
        Assert.assertTrue(cashStock.getBalance().doubleValue() == twoEuros);
        Assert.assertTrue(cashStock.getStock().get(Coin.TWO_EURO) == 1);
    }

    @Test
    public void testNoRefundAfterBuy() {
        final double twoEuros = 2.0;
        final AvailableDrinks coke = AvailableDrinks.COKE;
        vm.insertCoin(twoEuros);
        vm.selectProduct(coke);
        final CashStock cashStock = vm.refund();
        Assert.assertTrue(cashStock.getBalance().doubleValue() == 0.0);
    }

    @Test
    public void testNoEnoughCashForChange() {
        final double oneEuro = 1.0;
        final AvailableDrinks water = AvailableDrinks.WATER;
        ProductSellOrder productSellOrder = null;
        for(int i = 0; i<=7; i++) {
            vm.insertCoin(oneEuro);
            productSellOrder = vm.selectProduct(water);
        }
        Assert.assertTrue(productSellOrder.getItem() == null);
        Assert.assertTrue(productSellOrder.getPrice() == null);
        Assert.assertTrue(productSellOrder.getChange() == null);
        Assert.assertTrue(productSellOrder.getMessage().contains(PropertyFactory.formatMessage(Message.NO_ENOUGH_CHANGE_IN_MACHINE.getMessageKey())));
    }
}


