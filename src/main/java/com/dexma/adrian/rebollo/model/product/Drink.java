package com.dexma.adrian.rebollo.model.product;

import java.math.BigDecimal;
import java.util.Optional;

import com.dexma.adrian.rebollo.model.exception.IllegalDrinkName;

public class Drink extends Product {

    private Drink(final String name, final BigDecimal price) {
        super(name, price);
    }

    @Override
    public AvailableDrinks getEnum() {
        final Optional<AvailableDrinks> optionalDrink = AvailableDrinks.byName(name);
        if (optionalDrink.isPresent()) {
            return optionalDrink.get();
        } else {
            throw new IllegalDrinkName(String.format("Could not get Drink by name %s", name));
        }
    }

    //Apply Builder pattern to wrapp/restrict Drink creation/visibility.
    public static class DrinkBuilder {

        private AvailableDrinks availableDrink;

        public DrinkBuilder(final AvailableDrinks availableDrink) {
            this.availableDrink = availableDrink;
        }

        public Drink build() {
            return new Drink(availableDrink.getName(), availableDrink.getPrice());
        }
    }
}
