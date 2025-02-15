package com.sad.stockshark.classes;


// class to handle notification system
public class NotificationSystem implements iNotification {

    // Declare message
    private String message;

    // Message to set the notification message
    public void sendNotification(String message) {
        this.message = message;
        System.out.println("Notification: "+ message);
    }

    // Method to retrieve the notification message
    public String getMessage(){
        return message;
    }
    
}
