package com.millerBot.services;

import com.millerBot.models.Market;
import com.millerBot.models.SimulationAccount;
import com.millerBot.models.enums.TypeOfTransaction;
import com.millerBot.models.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CreateTransactions {
    private SimulationAccount simulationAccount;
    private FinalMarket finalMarket;


    public CreateTransactions(SimulationAccount simulationAccount, FinalMarket finalMarket) {
        this.simulationAccount = simulationAccount;
        this.finalMarket = finalMarket;
    }

    public void oneLoop(Market market) {
        String marketTemp = market.getName().replace("BTC-", "");
        if (simulationAccount.getAcountStatus().get("BTC") > 0) {
            Transaction transaction = createBuyTransaction(market);
            double buyPrice = transaction.getRate();
            simulationAccount.getAcountStatus().replace("BTC", 0.0);
            simulationAccount.getAcountStatus().replace(marketTemp, transaction.getQuantity() * 0.9975);
            System.out.println(transaction);
            System.out.println(simulationAccount.getAcountStatus());
            while (simulationAccount.getAcountStatus().get(marketTemp) > 0) {
                double rate = getRate(market.getName(), "Last");
                System.out.println(rate);

                if (rate > 1.005 * buyPrice || rate < 0.98 * buyPrice) {

                    Transaction transaction1 = createSellTransaction(market);
                    System.out.println(transaction1);
                    simulationAccount.getAcountStatus().replace(marketTemp, 0.0);
                    simulationAccount.getAcountStatus().replace("BTC", transaction1.getQuantity() * 0.9975);


                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException c) {
                    c.printStackTrace();
                }

            }
        }
    }

    public Transaction createBuyTransaction(Market market) {


        String type = "Bid";
        double rate = getRate(market.getName(), type);
        double quantity = simulationAccount.getAcountStatus().get("BTC") / rate;
        Transaction transaction = new Transaction(market.getName(), rate, quantity, TypeOfTransaction.BUY);
        System.out.println(simulationAccount.getAcountStatus());
        return transaction;
    }

    public Transaction createSellTransaction(Market market) {
        String type = "Last";
        double rate = getRate(market.getName(), type);
        String marketTemp = market.getCurrency().replaceAll("BTC-", "");
        double quantity = simulationAccount.getAcountStatus().get(marketTemp) * rate;
        Transaction transaction = new Transaction(market.getCurrency(), rate, quantity, TypeOfTransaction.SELL);
        System.out.println(simulationAccount.getAcountStatus());
        return transaction;
    }


    public double getRate(String market, String type) {

        double rate = 0.0;
        String json = "";
        String url1 = "https://api.bittrex.com/api/v1.1/public/getticker?market=" + market;
        try {

            URL url = new URL(url1);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            json = bufferedReader.readLine();
            JSONObject jsonObject = new JSONObject(json);
            System.out.println(json);
            rate = jsonObject.getJSONObject("result").getDouble(type);
            Thread.sleep(5000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            getRate(market, type);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException x) {
                x.printStackTrace();
            }
        }
        return rate;
    }


}
