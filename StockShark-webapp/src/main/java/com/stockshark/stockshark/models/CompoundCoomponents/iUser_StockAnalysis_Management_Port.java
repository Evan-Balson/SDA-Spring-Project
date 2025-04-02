package com.stockshark.stockshark.models.CompoundCoomponents;

import java.util.List;

public interface iUser_StockAnalysis_Management_Port {

    void DisplayTrendingStocks();
    void compareTrendingStocks(List<String> stockSymbols);
    void viewStocksByDateRange(List<String> stockSymbols,String date1, String date2);
    void analyseData(List<String> stockSymbols);
}
