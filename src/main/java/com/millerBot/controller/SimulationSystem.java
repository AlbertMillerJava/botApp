package com.millerBot.controller;

import com.millerBot.models.utils.MapContainer;
import com.millerBot.models.Market;
import com.millerBot.services.CreateTransactions;
import com.millerBot.services.FinalMarket;
import com.millerBot.services.MarketSummaries;

import java.util.List;

public class SimulationSystem {


    MarketSummaries marketSummaries;
    FinalMarket finalMarket;
    CreateTransactions createTransactions;

    public SimulationSystem(MarketSummaries marketSummaries, FinalMarket finalMarket, CreateTransactions createTransactions) {
        this.marketSummaries = marketSummaries;
        this.finalMarket = finalMarket;
        this.createTransactions = createTransactions;
    }

    public void mainLoop(){
        List<Market> selectedMarketList = marketSummaries.createSelectedMarketsList(80,150);

        for(int i = 0 ; i<100 ;i++){

            MapContainer longPricesMap = new MapContainer(21,selectedMarketList);
            MapContainer shortPricesMap = new MapContainer(5,selectedMarketList);
            longPricesMap.fillMap();
            shortPricesMap.fillMap();
            Market selectedMarket = finalMarket.finalSelectedMarket(selectedMarketList,longPricesMap,shortPricesMap);
            createTransactions.oneLoop(selectedMarket);
        }
    }
}
