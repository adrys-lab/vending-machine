package com.dexma.adrian.rebollo.controller.cash;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexma.adrian.rebollo.model.coin.Coin;
import com.dexma.adrian.rebollo.model.stock.CashStock;

public class CashControllerImplTest {

    private static CashController cashController;

    @Before
    public void setUp() {
        cashController = CashControllerFactory.createCashController();
    }

    @Test
    public void testFirstChange() {
        cashController.insertCoin(Coin.TWO_EURO);
        CashStock change = cashController.getChange(1.00);
        Assert.assertEquals(change.getBalance().doubleValue(), 0.0, 0);
    }

    @Test
    public void testSecondChange() {
        cashController.insertCoin(Coin.TWO_EURO);
        cashController.insertCoin(Coin.ONE_EURO);
        cashController.insertCoin(Coin.ONE_EURO);
        cashController.insertCoin(Coin.ONE_EURO);
        cashController.insertCoin(Coin.ONE_EURO);
        cashController.insertCoin(Coin.ONE_EURO);
        CashStock change = cashController.getChange(3.00);
        Assert.assertEquals(change.getQuantity(Coin.TWO_EURO), 1.0, 0);
        Assert.assertEquals(change.getQuantity(Coin.ONE_EURO), 2.0, 0);
        Assert.assertEquals(change.getBalance().doubleValue(), 4.0, 0);
    }

    @Test
    public void testThirdChange() {
        cashController.insertCoin(Coin.ONE_EURO);
        cashController.insertCoin(Coin.ONE_EURO);
        cashController.insertCoin(Coin.FIFTY_CENTS);
        cashController.insertCoin(Coin.FIFTY_CENTS);
        CashStock change = cashController.getChange(0.00);
        Assert.assertEquals(change.getQuantity(Coin.ONE_EURO), 2.0, 0);
        Assert.assertEquals(change.getQuantity(Coin.FIFTY_CENTS), 2.0, 0);
        Assert.assertEquals(change.getBalance().doubleValue(), 3.0, 0);
    }

    @Test
    public void testFourthChange() {
        cashController.insertCoin(Coin.ONE_EURO);
        cashController.insertCoin(Coin.ONE_EURO);
        cashController.insertCoin(Coin.FIFTY_CENTS);
        cashController.insertCoin(Coin.FIFTY_CENTS);
        CashStock change = cashController.getChange(1.50);
        Assert.assertEquals(change.getQuantity(Coin.ONE_EURO), 1.0, 0);
        Assert.assertEquals(change.getQuantity(Coin.FIFTY_CENTS), 1.0, 0);
        Assert.assertEquals(change.getBalance().doubleValue(), 1.5, 0);
    }

    @Test
    public void testNoChange() {
        cashController.insertCoin(Coin.ONE_EURO);
        cashController.insertCoin(Coin.ONE_EURO);
        cashController.insertCoin(Coin.FIFTY_CENTS);
        cashController.insertCoin(Coin.FIFTY_CENTS);
        CashStock change = cashController.getChange(1.25);
        Assert.assertEquals(change.getQuantity(Coin.ONE_EURO), 1.0, 0);
        Assert.assertEquals(change.getQuantity(Coin.FIFTY_CENTS), 1.0, 0);
        Assert.assertTrue(change.getBalance().doubleValue() == 1.50);
        Assert.assertTrue(change.getBalance().doubleValue() - 1.25 == 0.25);
    }
}
