package com.stockshark.stockshark.models.CompoundCoomponents;

import com.stockshark.stockshark.models.User_Management.LoginService;
import com.stockshark.stockshark.models.User_Management.Portfolio;
import com.stockshark.stockshark.models.User_Management.UserService;

import java.util.List;
import java.util.Map;

public class User_Management_Compound {

    private LoginService login;
    private UserService user;
    private Notification_Component notification;
    private Stock_Analysis_Component stockAnalysis;


    public void getPortfolio(String portfolioID) {
        user.getPortfolio(portfolioID);
    }

    public void updatePortfolio(int userId, Portfolio portfolio) {
        user.updatePortfolio(userId, portfolio);
    }

    public void removePortfolio(int userId, Portfolio portfolio) {
        user.removePortfolio(userId, portfolio);
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

    public void Login(String email, String password) {
        login.loginUser(email, password);
    }

    public void logoutUser() {
        login.logoutUser();
    }

    public void registerUser(String email, String password) {
        login.registerUser(email, password);
    }
}
