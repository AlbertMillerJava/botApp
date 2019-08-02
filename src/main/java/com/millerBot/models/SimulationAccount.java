package com.millerBot.models;

import com.millerBot.services.MarketSummaries;

import java.util.HashMap;
import java.util.Map;

public class SimulationAccount {
    Map<String,Double> acountStatus;

    public SimulationAccount() {
        this.acountStatus = new HashMap<>();
    }

    public Map<String,Double> fillingMap(){
        MarketSummaries marketSummaries = new MarketSummaries();
        for (Market market : marketSummaries.createSelectedMarketsList(40,150)){
            String marketTemp = market.getCurrency();
            acountStatus.put(marketTemp,0.0);

        }
        acountStatus.put("BTC",0.1);
        return acountStatus;
    }



    public Map<String, Double> getAcountStatus() {
        return acountStatus;
    }
}
