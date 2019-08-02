package com.millerBot.models;

import java.math.BigDecimal;

public class Market {
    private String name;
    private String currency;
    private double volume;
    private int openBuyOrders;
    private int openSellOrders;
    private double ticker;

    public Market(String name, double volume, int openBuyOrders, int openSellOrders, double ticker) {
        this.name = name;
        this.currency = name.replaceAll("BTC-","");
        this.volume = volume;
        this.openBuyOrders = openBuyOrders;
        this.openSellOrders = openSellOrders;
    }

    public String getName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }

    public int getOpenBuyOrders() {
        return openBuyOrders;
    }

    public int getOpenSellOrders() {
        return openSellOrders;
    }

    public double getTicker() {
        return ticker;
    }

    public String getCurrency() {
        return currency;
    }
}
