package com.stockshark.stockshark.models.User_Management;

import com.stockshark.stockshark.models.CompoundCoomponents.Data_Handling_Component;

public interface iLogin {
    Boolean registerUser(String username,String email, String password, Data_Handling_Component dh);
    Boolean loginUser(String email, String password, Data_Handling_Component dh);
    Boolean logoutUser(String email, Data_Handling_Component dh);

}
