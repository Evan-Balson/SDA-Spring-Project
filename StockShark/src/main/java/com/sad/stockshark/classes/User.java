package com.sad.stockshark.classes;

//provided iUserSession, required iPortfolio and ILogin
public class User implements iUserSession{

    private boolean isSessionOpen = false; //is the user logged in
    //final keyword used to protect the field from being reassigned after it is initialized in the constructor
    private final iPortfolio portfolio; //required interface implementation
    private final iLogin login; //required interface implementation
    private final iStockViewer stockViewer;

    //constructor for user, accepting the implementation of interfaces
    public User(iPortfolio portfolio, iLogin login, iStockViewer stockViewer) {
        this.portfolio = portfolio;
        this.login = login;
        this.stockViewer = stockViewer;
    }

    //iUserSession implementation
    @Override
    public void startSession() {
        isSessionOpen = true;
    }

    @Override
    public void endSession() {
        isSessionOpen = false;
    }

    @Override
    public boolean isSessionActive() {
        return isSessionOpen;
    }

    public void viewStockDetails() {
        if (isSessionActive()) {
            stockViewer.viewStockDetails();
            System.out.println("Displaying stock details."); //could also display the portfolio
        } else {
            System.out.println("Please login first to view stock details.");
        }
    }

    //composition iLogin interface implementation
    public boolean login(String username, String password) {
        if (login.login(username, password)) {
            startSession();
            System.out.println(username + " logged in successfully.");
            return true;
        } else {
            System.out.println(username + " login failed.");
            return false;
        }
    }

    //logout method delegating logout logic to the login class
    public void logout() {
        login.logout();
        endSession();
    }

    //composition Portfolio interface implementation
    public void portfolio(){
        portfolio.displayPortfolio();
    }
    public void addStocks(String stock){
        portfolio.addStock(stock);
    }
    public void removeStocks(String stock){
        portfolio.removeStock(stock);
    }


}
