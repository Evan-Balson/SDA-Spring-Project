package com.sad.stockshark.classes;

public class StockComparisonSession implements iStockComparison{

    private iData idataInstance;
    private iUserSession iusersessionInstance;

    // Properties
    private float currentPrice;
    private String stock1;
    private String stock2;
    private String name;
    private String symbol;

    // Constructor required interfaces iData and iNotification
    public StockComparisonSession( iData iD, iUserSession iU, String name, float currentPrice, String symbol, String stock1, String stock2) {
        this.idataInstance = iD;
        this.iusersessionInstance = iU;
        this.name = name;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.stock1 = stock1;
        this.stock2 = stock2;
    }
    // Getter for name
    public String getName() {
        return name;
    }


    // Getter for symbol
    public String getSymbol(){
        return symbol;
    }

    // Getter for stock1
    public String getStock1(){
        return stock1;
    }

    // Getters for stock2
    public String getStock2(){
        return stock2;
    }


    // Compare stocks
    public String compareStocks(){
        return "Comparing result between " + this.stock1 + " and " + this.stock2;
    }
}
