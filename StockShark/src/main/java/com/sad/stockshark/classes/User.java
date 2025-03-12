package com.sad.stockshark.classes;

public class User implements iStockViewer, iLogin {

    private boolean isSessionOpen = false; //is the user logged in

    public void viewStockDetails() {
        if (isSessionOpen) {
            System.out.println("Displaying stock details.."); //could also display the portfolio
        } else {
            System.out.println("Please login first to view stock details.");
        }
    }

    public boolean login(String username, String password) {
        isSessionOpen = true;
        System.out.println(username + " logged in successfully.");
        return true;
    }

    public void logout() {
        isSessionOpen = false;
        System.out.println("Logged out successfully.");
    }
}
