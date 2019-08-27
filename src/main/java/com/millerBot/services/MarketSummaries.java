package com.millerBot.services;

import com.millerBot.models.Market;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MarketSummaries {
    private List<Market> selectedMarketsList;



    public MarketSummaries(){
        this.selectedMarketsList  =new ArrayList<>();
    }
    public String getMarketSummary() {
        String json = "";
        while(json.length()<5){
            try {
                URL url = new URL("https://api.bittrex.com/api/v1.1//public/getmarketsummaries");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                json = bufferedReader.readLine();

            } catch (IOException e) {

                try {

                    Thread.sleep(5000);
                    System.out.println("Check your internet connection");
                    getMarketSummary();
                } catch (InterruptedException x) {
                    x.printStackTrace();
                }
            }
        }
        return json;
    }

    public List<Market> createFullMarketsList(){

        JSONObject jsonObject = new JSONObject(getMarketSummary());
        JSONArray jsonArray = jsonObject.getJSONArray("result");
        List<Market> fullMarketList = new ArrayList<>();

        for (int i = 0 ; i < jsonArray.length() ; i++){

            String name = jsonArray.getJSONObject(i).getString("MarketName");
            double volume = jsonArray.getJSONObject(i).getDouble("BaseVolume");
            int openBuyOrders = jsonArray.getJSONObject(i).getInt("OpenBuyOrders");
            int openSellOrders = jsonArray.getJSONObject(i).getInt("OpenSellOrders");
            double lastPrice = jsonArray.getJSONObject(i).getDouble("Last");

            Market market = new Market(name, volume, openBuyOrders, openSellOrders, lastPrice);

            fullMarketList.add(market);

        }
        return fullMarketList;
    }

    public List<Market> createSelectedMarketsList (double volume, int buyOrders){
        selectedMarketsList = createFullMarketsList().stream()
                .filter(market -> market.getName().startsWith("BTC"))
                .filter(market -> market.getVolume()>volume)
                .filter(market -> market.getOpenBuyOrders()> buyOrders)
                .collect(Collectors.toList());

        return  selectedMarketsList;
    }

    public List<Market> getSelectedMarketsList() {
        return selectedMarketsList;
    }
}
