package com.dexma.adrian.rebollo.controller.product.drink;

import com.dexma.adrian.rebollo.controller.product.ProductController;
import com.dexma.adrian.rebollo.model.product.AvailableDrinks;
import com.dexma.adrian.rebollo.model.product.Drink;
import com.dexma.adrian.rebollo.model.stock.DrinkStock;
import com.dexma.adrian.rebollo.model.stock.Stock;

class DrinkControllerImpl implements ProductController<Drink> {

    private Stock<AvailableDrinks> availableProducts = new DrinkStock();

    //Set scope to package-private to avoid instantiation -> only allowed through Factory.
    DrinkControllerImpl() {
    }

    @Override
    public void initializeWithProducts(final AvailableDrinks drink, final Integer quantity) {
        availableProducts.add(drink, quantity);
    }

    @Override
    public void addProductSell(final Drink product) {
        availableProducts.subtract(product.getEnum());
    }

    @Override
    public Stock<AvailableDrinks> getAvailableProducts() {
        return availableProducts;
    }
}
