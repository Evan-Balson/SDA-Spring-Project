package com.stockshark.stockshark.models.User_Management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements iUserSession, iPortfolio{

    private String userID;

    //required login interface
    private Login Login;
    private Map<String, List<String>> stockPortfolio;
    private boolean isSessionOpen = false;


    // constructor to initialize the required interface
    public User(Login login) {
        this.Login = login;
        stockPortfolio = new HashMap<String, List<String>>();
        this.userID = "Guest";
    }


    @Override
    public Map<String, List<String>> getPortfolio(String portfilioID) {
        // access the database, get the portfolio items that have the user id
        // store it to the Map
        // then return the StockPortfolio Map
        return this.stockPortfolio;
    }

    @Override
    public void updatePortfolio(int userId, Portfolio portfolio) {
        // access the database,
        // Write an insert SQL statement to add the new values to the table


    }

    @Override
    public void removePortfolio(int userId, Portfolio portfolio) {
        // access the database,
        // Write an remove SQL statement to add the new values to the table
    }

    @Override
    public void startSession(List<String> stockSymbols) {
        this.isSessionOpen = true;
        //This should use the required COMPOUND COMPONENT stock analysis

    }

    @Override
    public void endSession() {
        this.isSessionOpen = false;

    }

    @Override
    public boolean isSessionActive(boolean session) {
        if (session) {
            return true;
        }
        else{
            return false;
        }
    }

    public String Login(String userName, String password) {
       String user = Login.loginUser(userName, password);
        return this.userID = user;
    }

    public String logoutUser() {
        String userID = Login.logoutUser();
        return this.userID = userID;
    }
}
