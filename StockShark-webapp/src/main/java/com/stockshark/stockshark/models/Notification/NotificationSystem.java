package com.stockshark.stockshark.models.Notification;

import com.stockshark.stockshark.models.CompoundCoomponents.Data_Handling_Component;
import com.stockshark.stockshark.models.CompoundCoomponents.Notification_Component;
import com.stockshark.stockshark.models.CompoundCoomponents.User_Management_Compound;

import java.util.List;

public class NotificationSystem implements iNotification_Port {

    User_Management_Compound user_management;

    public NotificationSystem(User_Management_Compound user_management){
        this.user_management = user_management;
    }


    @Override
    public void sendNotification(String message) {
        // access database to insert new notification information

    }

    @Override
    public List<String> viewNotifications(String userID) {
        // access the database to collect the notifications for a user ID
        // return the list if found

        // if none or query failed return null
        return null;

    }

    public Data_Handling_Component getDataHandlingComponentPort(){
        return new Data_Handling_Component();
    }

    public Notification_Component Notification_ComponentPort(){
        return new Notification_Component();
    }

}
