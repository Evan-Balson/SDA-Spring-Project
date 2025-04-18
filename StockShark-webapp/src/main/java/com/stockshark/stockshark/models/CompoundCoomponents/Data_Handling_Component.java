package com.stockshark.stockshark.models.CompoundCoomponents;

import com.stockshark.stockshark.models.Data_Handling.DataAggregator;
import com.stockshark.stockshark.models.Data_Handling.Database;
import com.stockshark.stockshark.models.Data_Handling.StockData;
import com.stockshark.stockshark.models.Data_Handling.externalAPI;
import com.stockshark.stockshark.models.User_Management.Portfolio;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;

public class Data_Handling_Component {


    private Database databaseClient;
    private DataAggregator aggregator;
    private externalAPI externalAPI;



    public Data_Handling_Component(DataAggregator aggregator, externalAPI api) {
        this.databaseClient = new Database(aggregator, api);
    }

    public Data_Handling_Component(){
        aggregator = new DataAggregator();
        externalAPI = new externalAPI();

        this.databaseClient = new Database(aggregator, externalAPI);

    }

    public JSONObject getTrendingStockData(String stockSymbol) throws IOException, InterruptedException {
        return databaseClient.getTrendingStockDataFromAlphaVantage(stockSymbol);
    }

    public StockData formatGlobalQuoteData(JSONObject rawData){
        return databaseClient.formatGlobalQuoteData(rawData);
    }

    public Boolean authenticateUser(String email, String password) {
        return databaseClient.authenticateUser(email, password);
    }

    public Boolean signOutUser(String email) {
        return databaseClient.signOut(email);
    }

    public Boolean pushPortfolioToDatabase(String email, String SymbolA, String SymbolB){
        return databaseClient.putPortfolioToDatabase(email, SymbolA,SymbolB);
    }

    public JSONObject getStockDataByDateRange(String missingSymbol, String startDate, String endDate) throws IOException, InterruptedException {
        return databaseClient.getStockDataByDateRange(missingSymbol,startDate,endDate);
    }

    public List<StockData> Format_AlphaVantageData_ForDatabase(JSONObject apiResponse){
        return databaseClient.Format_AlphaVantageData_ForDatabase(apiResponse);
    }

    public void insertStockData(List<StockData> stockDataList, String symbol){
        databaseClient.insertStockData(stockDataList, symbol);
    }

    public List<Portfolio> getPortfolioDetails(String userId) {

        return databaseClient.getPortfolioDetails(userId);
    }

    public Boolean beginRegistration(String username, String email, String password){
        return databaseClient.beginRegistration(username, email, password);
    }

    public Portfolio getPortfolioById(int portfolioId) {


        return databaseClient.getPortfolioById(portfolioId);
    }

    public StockData getStockDataObject(){
        return new StockData();
    }
}

