package com.stockshark.stockshark.models.User_Management;

import com.oracle.wls.shaded.java_cup.runtime.Symbol;
import com.stockshark.stockshark.models.CompoundCoomponents.Data_Handling_Component;
import com.stockshark.stockshark.models.CompoundCoomponents.Notification_Component;
import com.stockshark.stockshark.models.CompoundCoomponents.Stock_Analysis_Component;
import com.stockshark.stockshark.models.CompoundCoomponents.User_Management_Compound;
import com.stockshark.stockshark.models.Data_Handling.Database;
import com.stockshark.stockshark.models.Data_Handling.StockData;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService implements iUserSession, iPortfolio {

    private String userID;
    private LoginService login;
    private Map<String, List<String>> stockPortfolio;
    private Stock_Analysis_Component stockAnalysis;
    private boolean isSessionOpen = false;

    public UserService() {}

    public UserService(String userid) {
        this.userID = userid;
    }

    // Constructor to accept Stock_Analysis_Component
    public UserService(LoginService login, Stock_Analysis_Component stockAnalysis) {
        this.login = login;
        this.stockAnalysis = stockAnalysis;
        stockPortfolio = new HashMap<>();
        this.userID = "Guest";
    }

    @Override
    public Map<String, List<String>> getPortfolio(String portfolioID) {
        return this.stockPortfolio;
    }

    @Override
    public Boolean updatePortfolio(String email, String symbolA, String symbolB) {
        UserService userService = new UserService(email);
        Portfolio portfolio = new Portfolio(userService);
        return portfolio.updatePortfolio(symbolA,symbolB,email,getDataHandlingComponentPort());

        // in progress
    }

    @Override
    public Boolean removePortfolio(String user, Portfolio portfolio) {
        return true;
        // In progress
    }

    @Override
    public void startSession(List<String> stockSymbols) {
        this.isSessionOpen = true;
    }

    @Override
    public void endSession() {
        this.isSessionOpen = false;
    }

    @Override
    public boolean isSessionActive(boolean session) {
        return session;
    }

    public Boolean Login(String email, String password) {

        Data_Handling_Component data_handling_component = getDataHandlingComponentPort();

        return login.loginUser(email, password, data_handling_component);

    }

    public Boolean logoutUser(String email) {

        Data_Handling_Component data_handling_component = getDataHandlingComponentPort();
        return login.logoutUser(email, data_handling_component);
    }

    public JSONObject LookupTrendingMarkets(String symbol) throws IOException, InterruptedException {
        // Delegate to stockAnalysis to look up trending data
        return stockAnalysis.DisplayTrendingStocks(symbol);
    }

    public StockData formatGlobalQuoteData(JSONObject rawData){
        return stockAnalysis.formatGlobalQuoteData(rawData);
    }

    public List<Portfolio> getPortfolioDetails(String userId){
        UserService userService = new UserService(userId);
        Portfolio portfolio = new Portfolio(userService);

        return portfolio.getPortfolioDetails(userId, getDataHandlingComponentPort());
    }

    public boolean registerUser(String username, String email, String password) {
        return login.registerUser(username, email, password, getDataHandlingComponentPort());
    }

    public Portfolio getPortfolioById(int portfolioId){
        UserService userService = new UserService(null);
        Portfolio portfolio = new Portfolio(userService);

        return portfolio.getPortfolioById(portfolioId, getDataHandlingComponentPort());
    }

    public Data_Handling_Component getexternalDB(){
        return getDataHandlingComponentPort();
    }


    public Data_Handling_Component getDataHandlingComponentPort(){
        return new Data_Handling_Component();
    }


    public Notification_Component Notification_ComponentPort(){
        return new Notification_Component();
    }

    public User_Management_Compound user_management_compoundPort(){
        return new User_Management_Compound();
    }


    public String getEmail() {
        return userID;
    }
}
