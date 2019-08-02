package com.millerBot.models;

import com.millerBot.models.enums.TypeOfTransaction;

public class Transaction {

    String market;
    double rate;
    double quantity;
    TypeOfTransaction typeOfTransaction;

    public Transaction(String market, double rate, double quantity, TypeOfTransaction typeOfTransaction) {
        this.market = market;
        this.rate = rate;
        this.quantity = quantity;
        this.typeOfTransaction = typeOfTransaction;
    }

    public String getMarket() {
        return market;
    }

    public double getRate() {
        return rate;
    }

    public double getQuantity() {
        return quantity;
    }

    public TypeOfTransaction getTypeOfTransaction() {
        return typeOfTransaction;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "market='" + market + '\'' +
                ", rate=" + rate +
                ", quantity=" + quantity +
                ", typeOfTransaction=" + typeOfTransaction +
                '}';
    }
}



