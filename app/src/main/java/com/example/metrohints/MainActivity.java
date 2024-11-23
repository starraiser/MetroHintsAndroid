package com.example.metrohints;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = new String[]{Manifest.permission.POST_NOTIFICATIONS,Manifest.permission.VIBRATE};
        if(EasyPermissions.hasPermissions(this,permissions)){
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            // 设置触发提醒的时间（这里以10秒后为例）
            long triggerAtMillis = System.currentTimeMillis() + 1000;

            // 创建Intent，指定要触发的BroadcastReceiver
            Intent intent = new Intent(this, MyAlarmReceiver.class);

            // 创建PendingIntent
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            // 设置单次闹钟
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
            }
        }else{
            EasyPermissions.requestPermissions(this, "该权限用于蓝牙, 不授予权限不能使用相关功能", 1, permissions);
        }
    }


    private String createNotificationChannel(String channelID, String channelNAME, int level) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
            manager.createNotificationChannel(channel);
            return channelID;
        } else {
            return null;
        }
    }

}