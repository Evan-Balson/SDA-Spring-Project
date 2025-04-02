package com.stockshark.stockshark.models.User_Management;

public class LoginService implements iLogin {

    @Override
    public void registerUser(String username, String password) {
        // access the database
        // write an insert statement to add the new user to the database

    }

    @Override
    public String loginUser(String email, String password) {
        // access the database
        // check if the user is existing
        // if yes
            // get the user ID from the database using the email and password match
            // return the user ID

        // if no return null
        return null;
    }

    //this provides the logout method for the user
    @Override
    public String logoutUser() {
        return null;

    }
}
