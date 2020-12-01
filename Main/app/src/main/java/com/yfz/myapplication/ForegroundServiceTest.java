package com.yfz.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ForegroundServiceTest extends Service {
    private NotificationManager notificationManager;
    private Notification notification;
    private NotificationChannel notificationChannel;
    private final static String NOTIFICATION_CHANNEL_ID = "CHANNEL_ID";
    private final static String NOTIFICATION_CHANNEL_NAME = "CHANNEL_NAME";
    private final static int FOREGROUND_ID=1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
         new UnsupportedOperationException("not yet implemented");
         return null;
    }

    @Override
    public void onCreate() {
        notification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        intent = new Intent(getApplicationContext(), MainActivity.class);  //点击通知栏后想要被打开的页面MainActivity.class
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);  //点击通知栏触发跳转
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("标题：测试文案")
                    .setContentText("内容：你好,点击打开app主页")
                    .setContentIntent(pendingIntent)
                    .build();
        }
        notification.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(FOREGROUND_ID, notification);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true); //销毁时停止服务
        super.onDestroy();
    }


    private void notification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
            );
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            notification = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).build();
        }
        startForeground(FOREGROUND_ID, notification);
    }
}
