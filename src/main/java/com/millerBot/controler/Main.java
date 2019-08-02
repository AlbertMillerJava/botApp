package com.millerBot.controler;
import com.millerBot.services.CreateTransactions;
import com.millerBot.services.FinalMarket;

import com.millerBot.services.MarketSummaries;
import com.millerBot.models.SimulationAccount;

public class Main {

    public static void main(String[] args) {

        MarketSummaries marketSummaries = new MarketSummaries();

        FinalMarket finalMarket = new FinalMarket(marketSummaries);

        SimulationAccount simulationAccount = new SimulationAccount();

        simulationAccount.fillingMap();

        CreateTransactions createTransactions = new CreateTransactions(simulationAccount, finalMarket);

        SimulationSystem simulationSystem = new SimulationSystem(marketSummaries,finalMarket, createTransactions);

        simulationSystem.mainLoop();

    }
}
