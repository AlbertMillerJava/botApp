package com.millerBot.models;

import com.millerBot.services.MarketSummaries;
import org.json.JSONArray;
import org.json.JSONObject;

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

        for (Market market :selectedMarketList) {
            pricesMap.put(market, new MovingAverage(limit));
        }

    }

    public void addingPriceToMap() {
        String json = new MarketSummaries().getMarketSummary();
        double price = 0.0;

        JSONObject jsObject = new JSONObject(json);
        JSONArray array = jsObject.getJSONArray("result");

        for (int i = 0; i < selectedMarketList.size(); i++) {

            for (int n = 0; n < array.length(); n++) {

                String marketName = array.getJSONObject(n).getString("MarketName");

                if (selectedMarketList.get(i).getName().equals(marketName)) {

                    price = array.getJSONObject(n).getDouble("Last");

                    pricesMap.get(selectedMarketList.get(i)).addingPrice(price);
                }

            }
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
