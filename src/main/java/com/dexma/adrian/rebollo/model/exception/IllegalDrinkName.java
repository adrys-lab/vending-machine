package com.dexma.adrian.rebollo.model.exception;

public class IllegalDrinkName extends RuntimeException {

    private String message;

    public IllegalDrinkName(String string) {
        this.message = string;
    }

    @Override
    public String getMessage() {
        return message;
    }

}