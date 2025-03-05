package com.sad.stockshark.classes;

import java.rmi.server.UID;

public class User implements iStockViewer,iLogin{
    // User attributes
    String userID;
    String userName;
    String userPassword;


    // Constructor
    public User(String UID, String name, String password){

        this.userID = UID;
        this.userName = name;
        this.userPassword = password;

    }

    // Methods
    public void viewStockDetails() {
        // Implementation code
    }

    //
    public boolean verifyLogin(String UID, String password) {
        if (UID.equals(this.userID) && password.equals(this.userPassword)){
            return true;
        }
        else{
            return false;
        }
    }

}
