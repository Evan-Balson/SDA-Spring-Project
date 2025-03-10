package com.sad.stockshark.classes;

// class to handle notification system
public class NotificationSystem implements iNotification {

    // Reference to database
    private iData idataInstance;

    // Constructor to inject database dependency
    public NotificationSystem(iData idataInstance){
        // check for null in idataInstance
        if (idataInstance == null){
            throw new IllegalArgumentException( "Data is null");
        }
        this.idataInstance = idataInstance;
    }

    // Declare message
    private String message;

    // Message to set the notification message
    @Override
    public void sendNotification(String message) {
        this.message = message;

        // Log the notification message in the database
        idataInstance.saveNotification(message);

        System.out.println("Notification: "+ message);
    }

    // Method to retrieve the notification message
    public String getMessage(){
        return message;
    }
    
}
