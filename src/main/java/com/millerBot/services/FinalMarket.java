package com.millerBot.services;

import com.millerBot.models.utils.MapContainer;
import com.millerBot.models.Market;

import java.util.*;


public class FinalMarket {

    MarketSummaries marketSummaries;
    boolean trend = false;
    String chosenPair ="";
    Market selectedMarket = null;
    List<Boolean> trendList = new ArrayList<>();
    Map<Market, List> trendMap = new HashMap<>();

    public FinalMarket(MarketSummaries marketSummaries) {
        this.marketSummaries = marketSummaries;
    }

    public Market finalSelectedMarket(List<Market> marketList, MapContainer longPricesMap, MapContainer shortPricesMap) {

        int step = 0;

        try {
            Thread.sleep(5000);
            while (selectedMarket == null) {
                step++;
                addPrices(longPricesMap, shortPricesMap);
                selectedMarket = selectMarket(marketList,step, longPricesMap, shortPricesMap);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return selectedMarket;
    }


    public void addPrices(MapContainer longPricesMap, MapContainer shortPricesMap) {
        longPricesMap.addingPriceToMap();
        shortPricesMap.addingPriceToMap();
    }


    public Market selectMarket(List<Market> marketList, int step, MapContainer longPricesMap, MapContainer shortPricesMap) {

        for (Market market : marketList) {

            int length = longPricesMap.getPricesMap().get(market).getPricesList().size();
            double shortAverage = shortPricesMap.getPricesMap().get(market).averaging();
            double longAverage = longPricesMap.getPricesMap().get(market).averaging();

            trendMap.put(market, trendList);

            if (shortAverage < longAverage) {
                trend = false;
                trendMap.get(market).add(trend);

            } else if (shortAverage == longAverage) {
                trend = true;
                trendMap.get(market).add(trend);

            } else if (shortAverage > longAverage && trendMap.get(market).get(step - 1).equals(false) && length >= 20) {
                trend = true;
                trendMap.get(market).add(trend);
                chosenPair = market.getName();
                selectedMarket = market;

            } else {
                trend = false;
                trendMap.get(market).add(trend);
            }
        }

        return selectedMarket;
    }
}