package com.stockshark.stockshark.models.User_Management;

import java.util.List;

public interface iUserManagement_Port {
    void startSession(List<String> stockSymbols);
    void endSession();
    boolean isSessionActive(boolean session);
}
