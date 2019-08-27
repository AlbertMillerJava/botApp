package com.millerBot.services;

import com.millerBot.models.Market;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Ticker {

    Market market;
    private double bid;
    private double ask;
    private double last;

    public Ticker(Market market){
        this.market = market;
    }

    public double getRate(String type) {
        double rate = 0.0;
        String json = "";
        String url1 = "https://api.bittrex.com/api/v1.1/public/getticker?market=" + market.getName();
        try {
            URL url = new URL(url1);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            json = bufferedReader.readLine();
            JSONObject jsonObject = new JSONObject(json);
            rate = jsonObject.getJSONObject("result").getDouble(type);

        } catch (IOException e) {
            System.out.println("get rate failed beacuse internet connection failed");
            try {
                Thread.sleep(1000);
                getRate(type);
            } catch (InterruptedException x) {
                x.printStackTrace();
            }
        }
        return rate;
    }


}
