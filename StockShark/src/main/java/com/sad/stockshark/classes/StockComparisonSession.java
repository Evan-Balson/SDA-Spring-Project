package com.sad.stockshark.classes;

public class StockComparisonSession extends Data implements iStockComparison{

    // Properties
    private float currentPrice;
    private String stock1;
    private String stock2;

    // Constructor
    public StockComparisonSession(String name, float currentPrice, String symbol, String stock1, String stock2) {
        super(name, symbol); // Calls the superclass (Data) constructor
        this.currentPrice = currentPrice;
        this.stock1 = stock1;
        this.stock2 = stock2;
    }

    // Getter for symbol
    public String symbol(){
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
    public void compareStocks(String stock1, String stock2){
        return "Comparing result between " + stock1 + " and " + stock2;
    }
}
