package com.example.ex02_intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");

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
