package com.example.beginnercookingapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1_ID = "timerChannel";
    public static final String SERVICE_CHANNEL_ID = "serviceChannel";

    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        createServiceChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {           //check for API version
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "Timer Alarm", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Timer is done!");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
        }
    }

    private void createServiceChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {           //check for API version
            NotificationChannel serviceChannel = new NotificationChannel(SERVICE_CHANNEL_ID, "Timer Alarm", NotificationManager.IMPORTANCE_DEFAULT);
            serviceChannel.setDescription("Timer is running!");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(serviceChannel);
        }
    }
}
