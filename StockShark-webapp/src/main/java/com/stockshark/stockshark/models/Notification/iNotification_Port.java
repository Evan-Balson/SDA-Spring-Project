package com.stockshark.stockshark.models.Notification;
import java.util.List;

import com.stockshark.stockshark.models.CompoundCoomponents.Notification_Component;

public interface iNotification_Port {

    void sendNotification(String message);
    List<String> viewNotifications(String userID);


}
