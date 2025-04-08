package com.stockshark.stockshark.models.Data_Handling;

public class StockData {
    private String symbol;
    private String date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    private double change;
    private double changePercent;
    private String avgVolume3M;
    private String marketCap;
    private String peRatio;
    private String fiftyTwoWeekChange;

    public StockData(String symbol, String date, String open, String high, String low, String close, String volume) {
        this.symbol = symbol;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public StockData() {
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public String getDate() {
        return date;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getClose() {
        return close;
    }

    public String getVolume() {
        return volume;
    }

    public double getChange() {
        return change;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public String getAvgVolume3M() {
        return avgVolume3M;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public String getPeRatio() {
        return peRatio;
    }

    public String getFiftyTwoWeekChange() {
        return fiftyTwoWeekChange;
    }

    // Setters
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public void setVolume(long volume) {
        this.volume = String.valueOf(volume);
    }

    public void setAvgVolume3M(String avgVolume3M) {
        this.avgVolume3M = avgVolume3M;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public void setPeRatio(String peRatio) {
        this.peRatio = peRatio;
    }

    public void setFiftyTwoWeekChange(String fiftyTwoWeekChange) {
        this.fiftyTwoWeekChange = fiftyTwoWeekChange;
    }


    @Override
    public String toString() {
        return "StockData{" +
                "symbol='" + symbol + '\'' +
                ", date='" + date + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", volume='" + volume + '\'' +
                ", change=" + change +
                ", changePercent=" + changePercent +
                ", avgVolume3M='" + avgVolume3M + '\'' +
                ", marketCap='" + marketCap + '\'' +
                ", peRatio='" + peRatio + '\'' +
                ", fiftyTwoWeekChange='" + fiftyTwoWeekChange + '\'' +
                '}';
    }
}