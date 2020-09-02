package com.example.ex01_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStartService, btnFinishService, btnStartIntentService, btnStartForegroundService;
    Intent service_intent;  // 서비스 가동을 위해 사용한 인텐트 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.btnStartService);
        btnFinishService = findViewById(R.id.btnFinishService);
        btnStartIntentService = findViewById(R.id.btnStartIntentService);
        btnStartForegroundService = findViewById(R.id.btnStartForegroundService);

        // 서비스 시작
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service_intent = new Intent(MainActivity.this, MyService1.class);
                startService(service_intent);
            }
        });

        // 서비스 중지
        btnFinishService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(service_intent);
            }
        });

        // 인텐트 서비스 시작
        btnStartIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service_intent = new Intent(MainActivity.this, MyIntentService.class);
               startService(service_intent);
//                startForegroundService(service_intent);
            }
        });

        // ForgroundService 시작
        btnStartForegroundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service_intent = new Intent(MainActivity.this, MyForegroundService.class);
                startForegroundService(service_intent);
            }
        });
    }
}
