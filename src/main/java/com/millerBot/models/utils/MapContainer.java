package com.millerBot.models.utils;

import com.millerBot.models.Market;
import com.millerBot.services.MarketSummaries;
import com.millerBot.services.Ticker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CORBA.MARSHAL;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MapContainer {

    private Map<Market, MovingAverage> pricesMap;
    private int limit;
    private List<Market> selectedMarketList;

    public MapContainer(int limit, List<Market> selectedMarketList) {
        this.limit = limit;
        this.selectedMarketList = selectedMarketList;
        this.pricesMap = new HashMap<>();
    }

    public void fillMap() {
        for (Market market : selectedMarketList) {
            pricesMap.put(market, new MovingAverage(limit));
        }
    }


    public void addingPriceToMap() {
        double price = 0.0;

        for (Market market : selectedMarketList) {
            Ticker ticker = new Ticker(market);
            price = ticker.getRate("Last");
            pricesMap.get(market).addingPrice(price);
        }
    }

    public Map<Market, MovingAverage> getPricesMap() {
        return pricesMap;
    }

    @Override
    public String toString() {
        return "MapContainer{" +
                "pricesMap=" + pricesMap.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapContainer that = (MapContainer) o;
        return limit == that.limit &&
                Objects.equals(pricesMap, that.pricesMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pricesMap, limit);
    }
}
