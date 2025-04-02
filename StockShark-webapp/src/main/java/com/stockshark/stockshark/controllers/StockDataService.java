package com.stockshark.stockshark.controllers;

import com.stockshark.stockshark.models.Data_Handling.*;
import org.json.JSONObject;

import java.util.List;



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
}