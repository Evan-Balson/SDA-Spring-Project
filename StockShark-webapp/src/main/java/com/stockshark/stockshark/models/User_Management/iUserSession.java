package com.stockshark.stockshark.models.User_Management;

import java.util.List;

public interface iUserSession {
    void startSession(List<String> stockSymbols);
    void endSession();
    boolean isSessionActive(boolean session);
}
