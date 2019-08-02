package com.millerBot.models;

import java.util.LinkedList;
import java.util.Objects;

public class MovingAverage {
    LinkedList <Double> pricesList;
    int limit;

    public MovingAverage(int limit) {
        this.pricesList= new LinkedList<>();
        this.limit = limit;
    }

    public LinkedList<Double> getPricesList() {
        return pricesList;
    }

    public void addingPrice(Double price){

        if (getPricesList().size()==limit){
            getPricesList().removeFirst();
        }
        getPricesList().add(price);

    }
    public double averaging(){
        double sum = 0;
        double average = 0;
        for (int i = 0; i<getPricesList().size(); i++ ){
            sum += getPricesList().get(i);
        }
        average = sum/getPricesList().size();
        return average;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovingAverage that = (MovingAverage) o;
        return limit == that.limit &&
                Objects.equals(pricesList, that.pricesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pricesList, limit);
    }
}
