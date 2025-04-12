package com.stockshark.stockshark.models.Data_Handling;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class DataAggregator implements iStockData {

    @Override
    public List<StockData> formatData_from_AlphaVantage(JSONObject jsonData) {
        List<StockData> stockDataList = new ArrayList<>();

        System.out.println(jsonData);

        System.out.println("Data from AlphaVantage Now being PARSED");

        // Assuming 'jsonData' is a JSONObject containing the response from the API
        JSONObject monthlySeries = jsonData.getJSONObject("Monthly Adjusted Time Series");

        // Iterate over each date key in the monthly series
        monthlySeries.keys().forEachRemaining(date -> {
            // Check if the specific date's data exists
            if (monthlySeries.has(date)) {
                JSONObject dailyData = monthlySeries.getJSONObject(date);
                // Safely retrieve each field, ensuring they exist using optString to avoid JSONException
                String open = dailyData.optString("1. open", "Not Available");
                String high = dailyData.optString("2. high", "Not Available");
                String low = dailyData.optString("3. low", "Not Available");
                String close = dailyData.optString("4. close", "Not Available");
                String volume = dailyData.optString("6. volume", "Not Available");

                // Create a new StockData object and add it to the list
                StockData stockData = new StockData(date, open, high, low, close, volume);
                stockDataList.add(stockData);


            } else {
                // Handle the missing date (optional, based on requirements)
                System.out.println("Data for " + date + " is not available.");
            }
        });

        // Return the list of StockData objects
        return stockDataList;
    }

    public StockData formatGlobalQuoteData(JSONObject rawData){

        JSONObject globalQuote = rawData.getJSONObject("Global Quote");
        String symbol = globalQuote.getString("01. symbol");
        String price = globalQuote.getString("05. price");
        String change = globalQuote.getString("09. change");
        String changePercent = globalQuote.getString("10. change percent");

        // Create and return a new StockData object using the global quote constructor.
        return new StockData(symbol, price, change, changePercent);
    }

    public StockData formatGlobalQuoteData(String rawData) {
        // Parse the raw JSON string into a JSONObject
        JSONObject jsonObject = new JSONObject(rawData);
        JSONObject globalQuote = jsonObject.getJSONObject("Global Quote");

        String symbol = globalQuote.getString("01. symbol");
        String price = globalQuote.getString("05. price");
        String change = globalQuote.getString("09. change");
        String changePercent = globalQuote.getString("10. change percent");

        // Create and return a new StockData object using the global quote constructor.
        return new StockData(symbol, price, change, changePercent);
    }

}
