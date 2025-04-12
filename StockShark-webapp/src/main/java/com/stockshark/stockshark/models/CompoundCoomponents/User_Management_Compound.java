package com.stockshark.stockshark.models.CompoundCoomponents;

import com.stockshark.stockshark.models.Data_Handling.Database;
import com.stockshark.stockshark.models.Stock_Analysis.AiAnalyser;
import com.stockshark.stockshark.models.Stock_Analysis.ChartsAPIService;
import com.stockshark.stockshark.models.User_Management.LoginService;
import com.stockshark.stockshark.models.User_Management.UserService;
import com.stockshark.stockshark.models.User_Management.Portfolio;
import com.stockshark.stockshark.models.Data_Handling.StockData;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class User_Management_Compound {

    private LoginService login;
    private UserService user;
    private Notification_Component notification;
    private Stock_Analysis_Component stockAnalysis;

    public User_Management_Compound() {
        // Initialize login service
        this.login = new LoginService();

        // Initialize stock analysis component with its required dependencies:
        // AiAnalyser and ChartsAPIService, plus Data_Handling_Component is created inside it.
        this.stockAnalysis = new Stock_Analysis_Component(new AiAnalyser(), new ChartsAPIService());

        // Initialize user service (now accepting a Stock_Analysis_Component)
        this.user = new UserService(login, stockAnalysis);

        // Initialize notification component (if needed, you can add more logic later)
        this.notification = new Notification_Component();
    }

    public void getPortfolio(String portfolioID) {
        user.getPortfolio(portfolioID);
    }

    public Boolean updatePortfolio(String email, String symbolA, String symbolB) {
        return user.updatePortfolio(email, symbolA, symbolB);
    }

    public Boolean addToPortfolio(String email, String symbolA, String symbolB) {
        return user.updatePortfolio(email, symbolA, symbolB);
    }

    public Boolean removePortfolio(String email, Portfolio portfolio) {
        return user.removePortfolio(email, portfolio);
    }

    public void startSession(List<String> stockSymbols) {
        user.startSession(stockSymbols);
    }

    public void endSession() {
        user.endSession();
    }

    public void isSessionActive(boolean session) {
        user.isSessionActive(session);
    }

    public Boolean Login(String email, String password) {
        return user.Login(email, password);
    }

    public Boolean logoutUser(String email) {
        return user.logoutUser(email);
    }

    public Data_Handling_Component getexternalDB(){
        return user.getexternalDB();
    }

    public Boolean registerUser(String username, String email, String password) {
        return user.registerUser(username, email, password);
    }

    public JSONObject lookupTrendingMarkets(String symbol) throws IOException, InterruptedException {
        // Now user is not null and its stockAnalysis dependency is set.
        return user.LookupTrendingMarkets(symbol);
    }

    public StockData formatGlobalQuoteData(JSONObject rawData) {
        return user.formatGlobalQuoteData(rawData);
    }

    public List<Portfolio> getPortfolioDetails(String userId){
        return user.getPortfolioDetails(userId);
    }

    public Portfolio getPortfolioById(int portfolioId){
        return user.getPortfolioById(portfolioId);
    }


}
