package com.example.zzzleep.ui.settings;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import com.example.zzzleep.R;

import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "zzzleep.notifications";
    private static final String CHANNEL_NAME = "My notification channel";

    private NotificationManager notificationManager;
    private int notificationId;

    @Override
    public void onReceive(Context context, Intent intent) {
            notificationManager = (NotificationManager) context
                    .getSystemService(context.NOTIFICATION_SERVICE);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            String name = prefs.getString("name", "");
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, CHANNEL_ID);
            builder.setContentTitle("Get ready for bed, " + name + "!");
            builder.setContentText("It's bedtime in 30 minutes");
            builder.setSmallIcon(R.drawable.ic_launcher_z_big_foreground);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
                builder.setChannelId(CHANNEL_ID);
            }


            notificationId = 0;
            notificationManager.notify(notificationId, builder.build());

    }
    //use tutorial to fix the repeat of the alarm
    // https://code.luasoftware.com/tutorials/android/android-daily-repeating-alarm-at-specific-time-with-alarmmanager/#:~:text=Use%20setExact%20%2F%20setWindow%20%2F%20set%20to,onReceive%20for%20the%20next%20day.
}