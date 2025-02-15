package com.sad.stockshark.classes;

public interface iUserSession {
    void startSession(String username);
    void endSession();
    boolean isSessionActive();
} 

