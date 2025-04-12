package com.stockshark.stockshark.models.CompoundCoomponents;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public interface iUser_StockAnalysis_Management_Port {

    JSONObject DisplayTrendingStocks(String symbol) throws IOException, InterruptedException;
    void compareTrendingStocks(List<String> stockSymbols);
    void viewStocksByDateRange(List<String> stockSymbols,String date1, String date2);
    void analyseData(List<String> stockSymbols);
}
