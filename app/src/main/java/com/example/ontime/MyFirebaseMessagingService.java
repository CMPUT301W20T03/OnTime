package com.example.ontime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.logging.Handler;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived (final RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification().getTitle().equals("Accepted")) {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showAcceptedNotification26(remoteMessage.getNotification().getBody());
            }
            else {
                showAcceptedNotification(remoteMessage.getNotification().getBody());
            }
        }
    }

    private void showAcceptedNotification26(String body) {
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, new Intent(), PendingIntent.FLAG_ONE_SHOT);
        NotificationHelper notificationHelper = new NotificationHelper((getBaseContext()));
        Notification.Builder builder = notificationHelper.getotNotification("Accepted", body, contentIntent);

        notificationHelper.getManager().notify(1, builder.build());
    }

    private void showAcceptedNotification(String body) {
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, new Intent(), PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle("Accepted")
                .setContentText(body)
                .setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }




}
