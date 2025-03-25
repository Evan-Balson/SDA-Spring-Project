package com.sad.stockshark.classes;

public class Login implements iLogin {

    //for the sake of simplicity
    String username = "username";
    String password = "password";

    @Override
    public boolean login(String username, String password) {
        return username.equals(this.username) && password.equals(this.password);
    }

    @Override
    public void logout() {
        System.out.println("Logged out successfully");
    }
}
