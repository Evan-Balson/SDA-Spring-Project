package com.stockshark.stockshark.models.CompoundCoomponents;

import com.stockshark.stockshark.models.Stock_Analysis.StockComparisonDashboard;

import java.util.List;

public class Stock_Analysis_Component implements iUser_StockAnalysis_Management_Port {

    private StockComparisonDashboard stockComparisonDashboard;
    private Data_Handling_Component data_handling_component;

    @Override
    public void DisplayTrendingStocks() {
        stockComparisonDashboard.DisplayTrendingStocks();
    }

    @Override
    public void compareTrendingStocks(List<String> stockSymbols) {
       stockComparisonDashboard.compareTrendingStocks(stockSymbols);
    }

    @Override
    public void viewStocksByDateRange(List<String> stockSymbols,String date1, String date2) {
        stockComparisonDashboard.viewStocksByDateRange(stockSymbols,date1,date2);
    }

    @Override
    public void analyseData(List<String> stockSymbols) {
        stockComparisonDashboard.analyseData(stockSymbols);
    }

}
