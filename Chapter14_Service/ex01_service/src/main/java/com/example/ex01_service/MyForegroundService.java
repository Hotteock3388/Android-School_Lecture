package com.example.ex01_service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyForegroundService extends Service {
    public MyForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test", "Foreground 서비스 시작");

        // Notification 띄우기
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("test", "Service", NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "test");
        builder.setSmallIcon(android.R.drawable.ic_menu_search);
        builder.setContentTitle("서비스 가동");
        builder.setContentText("서비스 가동 중입니다.");
        builder.setAutoCancel(true);
        Notification notification = builder.build();

        // 현재 notificaiton 메시지를 포그라운드 서비스의 메시지로 등록한다.
        startForeground(10, notification);

        // 스레드 시작
        ThreadClass thread = new ThreadClass();
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test", "Foreground 서비스 종료");
    }

    class ThreadClass extends Thread{
        @Override
        public void run() {
            for(int i=0; i<10; i++){
                SystemClock.sleep(1000);
                long time = System.currentTimeMillis();
                Log.d("test", "ForegroundService Running... : " + time);
            }

            // 스레드 작업이 완료되면 Notification 메시지를 사라지도록 한다.
            stopForeground(STOP_FOREGROUND_REMOVE);
            NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(10);
        }
    }
}
