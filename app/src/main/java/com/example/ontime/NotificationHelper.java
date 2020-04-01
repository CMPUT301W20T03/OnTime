package com.example.ontime;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationHelper extends ContextWrapper {
    private static final String CHANNEL_ID = "com.example.ontime";
    private static final String CHANNEL_NAME = "On Time";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel otChannels = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        otChannels.enableLights(true);
        otChannels.enableVibration(true);
        otChannels.setLightColor(Color.GREEN);
        otChannels.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(otChannels);
    }

    public NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getotNotification (String title, String content, PendingIntent contentIntent) {
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentText(content)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher_foreground);
    }
}
