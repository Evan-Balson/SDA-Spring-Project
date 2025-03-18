package com.sad.stockshark.classes;

// class to handle notification system
public class NotificationSystem implements iNotification{

    // Reference to database
    private iDatabase databaseInstance;

    // Constructor to inject database dependency
    public NotificationSystem(iDatabase databaseInstance){
        // check for null in iDatabase
        if (databaseInstance == null){
            throw new IllegalArgumentException( "Database is null");
        }
        this.databaseInstance = databaseInstance;
    }

    // Declare message
    private String message;

    // Message to set the notification message
    @Override
    public void sendNotification(String message) {
        this.message = message;

        // Log the notification message in the database
        databaseInstance.saveNotification(message);

        System.out.println("Notification: "+ message);
    }

    // Method to retrieve the notification message
    public String getMessage(){
        return message;
    }
    
}
