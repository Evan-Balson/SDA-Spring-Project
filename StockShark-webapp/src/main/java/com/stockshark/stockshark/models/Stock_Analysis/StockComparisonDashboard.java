package com.stockshark.stockshark.models.Stock_Analysis;

import com.stockshark.stockshark.models.CompoundCoomponents.Data_Handling_Component;
import com.stockshark.stockshark.models.CompoundCoomponents.iUser_StockAnalysis_Management_Port;
import com.stockshark.stockshark.models.Data_Handling.Database;

import java.util.List;

public class StockComparisonDashboard implements iUser_StockAnalysis_Management_Port {

    private AiAnalyser AiAnalyser;
    private ChartsAPIService chartsLayout;
    private Data_Handling_Component dataHandling;

    public StockComparisonDashboard(AiAnalyser AiAnalyser, ChartsAPIService chartsLayout) {
        this.AiAnalyser = AiAnalyser;
        this.chartsLayout = chartsLayout;
    }

    // Methods must be declared in the iUserStockAnalysis if the functionality must be provided to the user
    // see the examples below.

    @Override
    public void DisplayTrendingStocks() {
        // String result = dataHandling.getTrendingStockData();
        //chartsLayout.GenerateAreaChart(result);
    }

    @Override
    public void compareTrendingStocks(List<String> stockSymbols) {
        // result = dataHandling.getTrendingStockData();
        // chartsLayout.displayAreaChart(result);
    }

    @Override
    public void viewStocksByDateRange(List<String> stockSymbols,String date1, String date2) {
        chartsLayout.GenerateAreaChartByDateRange(stockSymbols,date1, date2);
    }

    public void analyseData(List<String> stockSymbols) {
        AiAnalyser.analyseData(stockSymbols);
    }
}
