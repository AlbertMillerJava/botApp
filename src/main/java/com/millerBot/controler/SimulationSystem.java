package com.millerBot.controler;

import com.millerBot.models.MapContainer;
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

        List<Market> selectedMarketList = marketSummaries.createSelectedMarketsList(40,150);
        MapContainer longPricesMap = new MapContainer(21,selectedMarketList);
        MapContainer shortPricesMap = new MapContainer(5,selectedMarketList);
        Market selectedMarket = finalMarket.getFinalMarket(selectedMarketList,longPricesMap,shortPricesMap);

        createTransactions.runSimulation(selectedMarket);

    }
}
