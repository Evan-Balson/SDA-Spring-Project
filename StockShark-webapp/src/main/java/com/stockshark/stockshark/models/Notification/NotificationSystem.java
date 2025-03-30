package com.stockshark.stockshark.models.Notification;

import java.util.List;

public class NotificationSystem implements iNotification_Port {


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
}
