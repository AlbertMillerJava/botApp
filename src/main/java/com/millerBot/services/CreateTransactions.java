package com.millerBot.services;

import com.millerBot.models.Market;
import com.millerBot.models.SimulationAccount;
import com.millerBot.models.enums.TypeOfTransaction;
import com.millerBot.models.Transaction;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateTransactions {
    private SimulationAccount simulationAccount;
    private FinalMarket finalMarket;


    public CreateTransactions(SimulationAccount simulationAccount, FinalMarket finalMarket) {
        this.simulationAccount = simulationAccount;
        this.finalMarket = finalMarket;
    }

    public void oneLoop(Market market) {

        if (simulationAccount.getAcountStatus().get("BTC") > 0) {
            Transaction transaction = createBuyTransaction(market);
            double buyPrice = transaction.getRate();

            while (simulationAccount.getAcountStatus().get(market.getCurrency()) > 0) {

                double rate = new Ticker(market).getRate("Ask");

                if (rate > 1.005 * buyPrice || rate < 0.98 * buyPrice) {
                    Transaction transaction1 = createSellTransaction(market);
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
        double rate = new Ticker(market).getRate(type);
        double quantity = simulationAccount.getAcountStatus().get("BTC") / rate;
        Transaction transaction = new Transaction(market.getName(), rate, quantity, TypeOfTransaction.BUY);
        simulationAccount.getAcountStatus().replace("BTC", 0.0);
        simulationAccount.getAcountStatus().replace(market.getCurrency(), transaction.getQuantity() * 0.9975);
        System.out.println(simulationAccount.getAcountStatus() + " " + transaction.toString());
        return transaction;
    }

    public Transaction createSellTransaction(Market market) {

        String type = "Ask";
        double rate = new Ticker(market).getRate(type);
        double quantity = simulationAccount.getAcountStatus().get(market.getCurrency()) * rate;
        Transaction transaction = new Transaction(market.getCurrency(), rate, quantity, TypeOfTransaction.SELL);
        simulationAccount.getAcountStatus().replace(market.getCurrency(), 0.0);
        simulationAccount.getAcountStatus().replace("BTC", transaction.getQuantity() * 0.9975);
        System.out.println(simulationAccount.getAcountStatus() + " " + transaction.toString());
        return transaction;
    }


}
