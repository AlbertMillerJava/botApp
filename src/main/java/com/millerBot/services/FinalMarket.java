package com.millerBot.services;
import com.millerBot.models.MapContainer;
import com.millerBot.models.Market;
import java.util.*;


public class FinalMarket {

    MarketSummaries marketSummaries;

    public FinalMarket(MarketSummaries marketSummaries) {
        this.marketSummaries = marketSummaries;

    }


    public Market getFinalMarket(List<Market> marketList,MapContainer longPricesMap, MapContainer shortPricesMap) {
        List<Market> oneMarket = new ArrayList<>();
        String chosenPair = "";
        try {

            shortPricesMap.fillMap();
            longPricesMap.fillMap();
            boolean trend;


            List<Boolean> trendList = new ArrayList<>();

            Map<Market, List> trendMap = new HashMap<>();

            int step = 0;
            while (chosenPair.length() < 2 || longPricesMap.getPricesMap().get(marketList.get(0)).getPricesList().size()<20) {
                step++;
                Thread.sleep(5000);

                longPricesMap.addingPriceToMap();
                shortPricesMap.addingPriceToMap();


                for (Market market : marketList) {

                    trendMap.put(market, trendList);
                    System.out.println(market.getName() + longPricesMap.getPricesMap().get(market).averaging() + " <--To dluga srednia "+ shortPricesMap.getPricesMap().get(market).averaging());
                    if (shortPricesMap.getPricesMap().get(market).averaging() < longPricesMap.getPricesMap().get(market).averaging()) {

                        trend = false;
                        trendMap.get(market).add(trend);

                    } else if (shortPricesMap.getPricesMap().get(market).averaging() == longPricesMap.getPricesMap().get(market).averaging()) {

                        trend = true;
                        trendMap.get(market).add(trend);

                    } else if (shortPricesMap.getPricesMap().get(market).averaging() > (longPricesMap.getPricesMap().get(market).averaging())*1.00015 && trendMap.get(market).get(step - 1).equals(false) &&
                            longPricesMap.getPricesMap().get(market).getPricesList().size() >= 20) {

                        trend = true;
                        trendMap.get(market).add(trend);
                        chosenPair = market.getName();

                        oneMarket.add(market);

                    } else {
                        trend = false;
                        trendMap.get(market).add(trend);
                    }
                }
            }

        } catch (InterruptedException | NullPointerException x) {
            x.printStackTrace();
        }
        Market marketSelected = oneMarket.get(0);
        if (!oneMarket.isEmpty()){
            return marketSelected;
        }
        return marketSelected;
    }
}