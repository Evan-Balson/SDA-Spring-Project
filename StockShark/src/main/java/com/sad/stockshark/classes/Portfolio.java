package com.sad.stockshark.classes;

import java.util.ArrayList;

public class Portfolio implements iPortfolio{

    private final ArrayList<String> portfolioStocks = new ArrayList<>();

    @Override
    public void displayPortfolio() {
        System.out.println("Portfolio stocks on display");
        portfolioStocks.forEach(System.out::println);
    }

    public void addStock(String stock) {
        portfolioStocks.add(stock);
        System.out.println(stock + " added to portfolio");
    }

    public void removeStock(String stock) {
        if (portfolioStocks.contains(stock)) {
            portfolioStocks.remove(stock);
            System.out.println(stock + " removed from portfolio.");
        } else {
            System.out.println(stock + " not found in portfolio.");
        }
    }
}
