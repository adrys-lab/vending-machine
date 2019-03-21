package com.dexma.adrian.rebollo.model.message;

public enum Message {

    COIN_NOT_VALID("coin.notValid"),
    CURRENT_CREDIT("currentCredit"),
    PRODUCT_IS_NOT_IN_STOCK("productIsNotInStock"),
    NO_ENOUGH_BALANCE_FOR_BUY("noEnoughBalanceForBuy"),
    NO_ENOUGH_CHANGE_IN_MACHINE("noEnoughChangeInMachine"),
    PRODUCT_SELL_OK("productSellOk");

    private final String messageKey;

    Message(final String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
