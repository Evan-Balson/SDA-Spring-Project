package com.stockshark.stockshark.models.User_Management;

import com.stockshark.stockshark.models.CompoundCoomponents.Data_Handling_Component;
import com.stockshark.stockshark.models.Data_Handling.DataAggregator;
import com.stockshark.stockshark.models.Data_Handling.Database;

public class LoginService implements iLogin {

    @Override
    public Boolean registerUser(String username, String email, String password, Data_Handling_Component dh) {

        return dh.beginRegistration(username, email,password);
    }

    @Override
    public Boolean loginUser(String email, String password, Data_Handling_Component dh) {

        return dh.authenticateUser(email,password);

    }

    //this provides the logout method for the user
    @Override
    public Boolean logoutUser(String email, Data_Handling_Component dh) {

        dh.signOutUser(email);

        return null;

    }

}
