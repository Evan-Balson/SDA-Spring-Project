package com.stockshark.stockshark.models.User_Management;

public interface iLogin {
    void registerUser(String username, String password);
    String loginUser(String email, String password);
    String logoutUser();

}
