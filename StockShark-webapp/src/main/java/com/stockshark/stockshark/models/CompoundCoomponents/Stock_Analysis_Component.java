package com.stockshark.stockshark.models.CompoundCoomponents;

import com.stockshark.stockshark.models.Data_Handling.StockData;
import com.stockshark.stockshark.models.Stock_Analysis.AiAnalyser;
import com.stockshark.stockshark.models.Stock_Analysis.ChartsAPIService;
import com.stockshark.stockshark.models.Stock_Analysis.StockComparisonDashboard;
import com.stockshark.stockshark.models.Data_Handling.DataAggregator;
import com.stockshark.stockshark.models.Data_Handling.externalAPI;
import com.stockshark.stockshark.models.CompoundCoomponents.Data_Handling_Component;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class Stock_Analysis_Component implements iUser_StockAnalysis_Management_Port {

    private AiAnalyser aiAnalyser;
    private ChartsAPIService chartsLayout;
    private Data_Handling_Component dataHandling;

    public Stock_Analysis_Component(AiAnalyser aiAnalyser, ChartsAPIService chartsLayout) {
        this.aiAnalyser = aiAnalyser;
        this.chartsLayout = chartsLayout;
        // Initialize Data_Handling_Component with its required dependencies
        this.dataHandling = new Data_Handling_Component(new DataAggregator(), new externalAPI());
    }
    public Stock_Analysis_Component() {}

    @Override
    public JSONObject DisplayTrendingStocks(String stockSymbol) throws IOException, InterruptedException {
        // Use dataHandling to get trending market data (properly using the GLOBAL_QUOTE API)
        return dataHandling.getTrendingStockData(stockSymbol);
    }

    public StockData formatGlobalQuoteData(JSONObject rawData){
        return dataHandling.formatGlobalQuoteData(rawData);
    }

    @Override
    public void compareTrendingStocks(List<String> stockSymbols) {
        // Insert logic to compare stocks if needed
    }

    @Override
    public void viewStocksByDateRange(List<String> stockSymbols, String date1, String date2) {
        chartsLayout.GenerateAreaChartByDateRange(stockSymbols, date1, date2);
    }

    @Override
    public void analyseData(List<String> stockSymbols) {
        aiAnalyser.analyseData(stockSymbols);
    }
}
