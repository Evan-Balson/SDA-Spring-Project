package com.stockshark.stockshark.models.User_Management;

import java.util.List;

public class StockComparisonSession {

    private UserService user;

    public StockComparisonSession(UserService user) {
        this.user = user;
    }

    public void startSession(List<String> stockSymbols) {
        user.startSession(stockSymbols);
    }

    public void endSession() {
        user.endSession();
    }
    public boolean isSessionActive(boolean session) {
        return user.isSessionActive(session);
    }
}
