package com.eteration.simplebanking.enums;

public enum TransactionTypeEnum {
    WITHDRAWAL("Withdrawal"),
    DEPOSIT("Deposit");

    public final String type;

    private TransactionTypeEnum(String type) {
        this.type = type;
    }
}
