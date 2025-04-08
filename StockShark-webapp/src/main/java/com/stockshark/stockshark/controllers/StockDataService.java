package com.stockshark.stockshark.controllers;

import com.stockshark.stockshark.models.Data_Handling.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class StockDataService {

    private externalAPI api;
    private DataAggregator aggregator;

    public StockDataService() {
        this.api = new externalAPI();
        this.aggregator = new DataAggregator();
    }

    public boolean updateStockData(String symbol) {
        try {
            JSONObject rawData = api.getStockData(symbol);
            //System.out.println(rawData);
            List<StockData> stockDataList = aggregator.formatData_from_AlphaVantage(rawData);
            System.out.println("We got to this point.................");

            //System.out.println(stockDataList);
            Database.insertStockData(stockDataList, symbol);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle or log the error appropriately
        }
        return false;
    }

    public List<StockData> getTrendingStocks() {
        // Let's say you have a function in your Database class to get all stock data
        List<StockData> allStocks = Database.getAllStockData(); // You'll need to implement this in your Database class

        if (allStocks == null || allStocks.isEmpty()) {
            return List.of(); // Return an empty list if there's no data
        }

        // Sort by ChangePercent (descending for top gainers)
        List<StockData> topGainers = new ArrayList<>(allStocks);
        topGainers.sort(Comparator.comparingDouble(StockData::getChangePercent).reversed());
        topGainers = topGainers.subList(0, Math.min(5, topGainers.size())); // Get the top 5

        // Sort by ChangePercent (ascending for top losers)
        List<StockData> topLosers = new ArrayList<>(allStocks);
        topLosers.sort(Comparator.comparingDouble(StockData::getChangePercent));
        topLosers = topLosers.subList(0, Math.min(5, topLosers.size())); // Get the top 5

        // Combine top gainers and top losers into a single list
        List<StockData> trendingStocks = new ArrayList<>();
        trendingStocks.addAll(topGainers);
        // You might want to add a label or some way to distinguish between gainers and losers in your UI
        trendingStocks.addAll(topLosers);

        return trendingStocks;
    }
}