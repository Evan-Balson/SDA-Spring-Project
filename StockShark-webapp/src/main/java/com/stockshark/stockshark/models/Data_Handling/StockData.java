package com.stockshark.stockshark.models.Data_Handling;

public class StockData {
    // Existing historical stock data fields
    private String date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;

    // New fields for live/trending stock data (Global Quote)
    private String symbol;
    private String price;
    private String change;
    private String changePercent;
    private Boolean negative;

    /**
     * Constructor for historical stock data.
     */
    public StockData(String name, String date, String open, String high, String low, String close, String volume) {
        this.symbol = name;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    /**
     * Constructor for historical stock data.
     */
    public StockData( String date, String open, String high, String low, String close, String volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    /**
     * Constructor for Global Quote data from Alpha Vantage.
     * This constructor includes only the fields relevant for trending stocks.
     */
    public StockData(String symbol, String price, String change, String changePercent) {
        this.symbol = symbol;
        this.price = price;
        this.change = change;
        this.changePercent = changePercent;
        this.negative = (change != null && change.trim().startsWith("-"));
    }

    /**
     * Overloaded constructor if you receive both historical and global quote data.
     */
    public StockData(String symbol, String date, String open, String high, String low,
                     String close, String price, String volume, String change, String changePercent) {
        this.symbol = symbol;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.price = price;
        this.volume = volume;
        this.change = change;
        this.changePercent = changePercent;
    }

    // Getters for historical stock data
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

    // Setters for historical stock data
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

    // Getters for new global quote/trending stock data
    public String getSymbol() {
        return symbol;
    }

    public String getPrice() {
        return price;
    }

    public String getChange() {
        return change;
    }

    public String getChangePercent() {
        return changePercent;
    }

    // Setters for new global quote/trending stock data
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    // Getter for the new property
    public boolean isNegative() {

        return negative;
    }

    // Optionally, you can also add a setter if needed:
    public void setNegative(boolean negative) {
        this.negative = negative;
    }

    public String getPriceChange() {
        return this.change;
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
                ", price='" + price + '\'' +
                ", volume='" + volume + '\'' +
                ", change='" + change + '\'' +
                ", changePercent='" + changePercent + '\'' +
                '}';
    }


}
