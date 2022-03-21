package com.example.zzzleep.ui.settings;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.example.zzzleep.R;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "io.github.bonigarcia.android.notification.notify_001";
    private static final String CHANNEL_NAME = "My notification channel";

    private NotificationManager notificationManager;
    private int notificationId;

    @Override
    public void onReceive(Context context, Intent intent) {
            notificationManager = (NotificationManager) context
                    .getSystemService(context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, CHANNEL_ID);
            builder.setContentTitle("Bedtime");
            builder.setContentText("It is time to go to bed.");
            builder.setSmallIcon(R.drawable.ic_android_black_24dp);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
                builder.setChannelId(CHANNEL_ID);
            }

            notificationId = 0;
            notificationManager.notify(notificationId, builder.build());
                    ;

    }
}