package com.sad.stockshark.classes;

public class StockComparisonSession implements iStockComparison{

    private iNotification notificationInstance;
    private iUserSession usersessionInstance;

    // Properties
    private String stock1;
    private String stock2;


    // Constructor required interfaces iData and iNotification
    public StockComparisonSession( iNotification iN, iUserSession iU, String stock1, String stock2) {
        this.notificationInstance = iN;
        this.usersessionInstance = iU;
        this.stock1 = stock1;
        this.stock2 = stock2;
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
    @Override
    public void compareStocks(String stock1, String stock2) {
        System.out.println("Comparing " + stock1 + " and " + stock2);
    }
}
