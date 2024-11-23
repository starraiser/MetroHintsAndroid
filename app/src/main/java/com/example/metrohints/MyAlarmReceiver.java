package com.example.metrohints;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MyAlarmReceiver extends BroadcastReceiver {

    private String createNotificationChannel(Context context,String channelID, String channelNAME, int level) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
            manager.createNotificationChannel(channel);
            return channelID;
        } else {
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        String channelId = createNotificationChannel(context,"my_channel_ID", "my_channel_NAME", NotificationManager.IMPORTANCE_MAX);
        // 创建Notification并显示
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("提醒")
                .setContentText("这是一个闹钟提醒")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());

        // 获取Vibrator服务
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(VibrationEffect.createOneShot(1000,255));
            vibrator.vibrate(new long[]{0,2000,2000,4000} , -1); // 震动500毫秒
        }

//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // 创建通知内容
//        Notification notification = new NotificationCompat.Builder(context, "important")
//                .setContentTitle("My App")
//                .setContentText("This is a test notification.")
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .build();
//
//        // 发送通知
//        manager.notify(0, notification);
    }
}