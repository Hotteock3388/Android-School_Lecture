package com.example.ex01_service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d("test", "인텐트서비스 시작");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test", "인텐트서비스 종료");
    }

    // 새로운 스레드가 생성되어 처리되는 메소드
    @Override
    protected void onHandleIntent(Intent intent) {
        for(int i=0; i<10; i++){
            SystemClock.sleep(1000);
            long time = System.currentTimeMillis();
            Log.d("test", "IntentService Running... : " + time);
        }
    }
}
